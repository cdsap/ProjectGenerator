#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RESOURCES_DIR="$ROOT_DIR/project-generator/src/main/resources"
VERSIONS_FILE="$RESOURCES_DIR/supported-gradle-versions.txt"
README_FILE="$ROOT_DIR/README.md"
TARGET_MAJORS="${TARGET_MAJORS:-9 8}"
MINORS_PER_MAJOR="${MINORS_PER_MAJOR:-3}"
VERSIONS_ENDPOINT="https://services.gradle.org/versions/all"
TEMP_DIR="$(mktemp -d)"
GRADLE_USER_HOME="$TEMP_DIR/gradle-user-home"

cleanup() {
  rm -rf "$TEMP_DIR"
}
trap cleanup EXIT

fetch_versions() {
  curl -fsSL "$VERSIONS_ENDPOINT" | python3 -c '
import json
import os
import sys

items = json.load(sys.stdin)
target_majors = os.environ["TARGET_MAJORS"].split()
minors_per_major = int(os.environ["MINORS_PER_MAJOR"])
selected_by_minor = {major: [] for major in target_majors}
seen_minor = {major: set() for major in target_majors}

for item in items:
    version = item.get("version", "").strip()
    if not version:
        continue
    if item.get("snapshot") or item.get("nightly") or item.get("releaseNightly") or item.get("broken"):
        continue
    if any(ch.isalpha() for ch in version):
        continue
    parts = version.split(".")
    if len(parts) < 2:
        continue
    major = parts[0]
    if major not in selected_by_minor:
        continue
    minor_key = ".".join(parts[:2])
    if minor_key in seen_minor[major]:
        continue
    selected_by_minor[major].append(version)
    seen_minor[major].add(minor_key)

    if all(len(selected_by_minor[major]) >= minors_per_major for major in target_majors):
        break

for major in target_majors:
    print("\n".join(selected_by_minor[major][:minors_per_major]))
' 
}

generate_wrapper_bundle() {
  local version="$1"
  local bundle_name="gradle_${version//./_}.zip"
  local work_dir="$TEMP_DIR/$version"

  mkdir -p "$work_dir/gradle/wrapper"
  cp "$ROOT_DIR/gradlew" "$ROOT_DIR/gradlew.bat" "$work_dir/"
  cp "$ROOT_DIR/gradle/wrapper/gradle-wrapper.jar" "$ROOT_DIR/gradle/wrapper/gradle-wrapper.properties" "$work_dir/gradle/wrapper/"
  printf 'rootProject.name = "wrapper-bundle"\n' > "$work_dir/settings.gradle.kts"
  printf '\n' > "$work_dir/build.gradle.kts"

  GRADLE_USER_HOME="$GRADLE_USER_HOME" "$work_dir/gradlew" -p "$work_dir" wrapper --gradle-version "$version" --distribution-type bin
  (
    cd "$work_dir"
    zip -qr "$RESOURCES_DIR/$bundle_name" gradle gradlew gradlew.bat
  )
}

prune_removed_bundles() {
  local versions=("$@")
  local keep_list=()

  for version in "${versions[@]}"; do
    keep_list+=("gradle_${version//./_}.zip")
  done

  while IFS= read -r existing; do
    local base
    base="$(basename "$existing")"
    if [[ ! " ${keep_list[*]} " == *" ${base} "* ]]; then
      rm -f "$existing"
    fi
  done < <(find "$RESOURCES_DIR" -maxdepth 1 -name 'gradle_*.zip' -type f | sort)
}

update_readme() {
  python3 - "$README_FILE" <<'PY'
from pathlib import Path
import re

readme = Path(__import__("sys").argv[1])
versions = Path(__import__("os").environ["VERSIONS_FILE"]).read_text().strip().splitlines()
majors = {}
for version in versions:
    major = version.split(".", 1)[0]
    majors.setdefault(major, []).append(version)

lines = []
for major, items in majors.items():
    lines.append(f"* Gradle {major}.x")
    lines.extend(f"  * {version}" for version in items)

replacement = "Gradle used, versions supported:\n" + "\n".join(lines) + "\n* The newest bundled version is the default."
content = readme.read_text()
pattern = re.compile(
    r"Gradle used, versions supported:\n(?:.*\n)*?\* The newest bundled version is the default\.",
    re.MULTILINE
)
updated = pattern.sub(replacement, content)
readme.write_text(updated)
PY
}

export TARGET_MAJORS MINORS_PER_MAJOR VERSIONS_FILE
mapfile -t versions < <(fetch_versions)

if [[ "${#versions[@]}" -eq 0 ]]; then
  echo "No stable Gradle versions found from $VERSIONS_ENDPOINT" >&2
  exit 1
fi

printf '%s\n' "${versions[@]}" > "$VERSIONS_FILE"

for version in "${versions[@]}"; do
  echo "Generating wrapper bundle for Gradle $version"
  generate_wrapper_bundle "$version"
done

prune_removed_bundles "${versions[@]}"
update_readme

echo "Updated supported Gradle versions:"
cat "$VERSIONS_FILE"
