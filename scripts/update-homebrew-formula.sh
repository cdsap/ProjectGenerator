#!/usr/bin/env bash
# Updates Formula/project-generator.rb for a JVM fat-binary release.
# Usage:
#   update-homebrew-formula.sh <version> <tap-dir> <projectGenerator-binary>
set -euo pipefail

usage() {
  echo "Usage: $0 <version> <tap-checkout-dir> <projectGenerator-binary>" >&2
  exit 2
}

if [[ $# -ne 3 ]]; then
  usage
fi

version=$1
tap_dir=$2
binary=$3
formula="$tap_dir/Formula/project-generator.rb"

if [[ ! -f "$formula" ]]; then
  echo "Formula not found: $formula" >&2
  exit 1
fi

if [[ ! "$version" =~ ^[0-9]+\.[0-9]+\.[0-9]+([.-].+)?$ ]]; then
  echo "Unexpected version: $version" >&2
  exit 1
fi

if [[ ! -s "$binary" ]]; then
  echo "Missing or empty binary: $binary" >&2
  exit 1
fi

sha=$(sha256sum "$binary" | awk '{print $1}')
url="https://github.com/cdsap/ProjectGenerator/releases/download/v${version}/projectGenerator"

tmp=$(mktemp)
trap 'rm -f "$tmp"' EXIT

python3 - "$tmp" "$url" "$sha" "$version" <<'PY'
import pathlib
import sys

dst, url, sha, version = sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4]

text = f'''class ProjectGenerator < Formula
  desc "CLI generating modularized Gradle projects"
  homepage "https://github.com/cdsap/ProjectGenerator"
  url "{url}",
      using: :nounzip
  version "{version}"
  sha256 "{sha}"
  license "MIT"

  depends_on "openjdk"

  def install
    libexec.install "projectGenerator"
    chmod 0755, libexec/"projectGenerator"
    env = Language::Java.overridable_java_home_env
    env["PATH"] = "#{{formula_opt_bin("openjdk")}}:$PATH"
    (bin/"projectGenerator").write_env_script libexec/"projectGenerator", env
  end

  test do
    assert_match "Usage", shell_output("#{{bin}}/projectGenerator --help")
  end
end
'''
text = text.replace('#{{formula_opt_bin("openjdk")}}', '#{formula_opt_bin("openjdk")}')
text = text.replace("#{{bin}}", "#{bin}")
pathlib.Path(dst).write_text(text)
PY

mv "$tmp" "$formula"
chmod a+r "$formula"
trap - EXIT

echo "Updated $formula"
echo "  url: $url"
echo "  sha256: $sha"
