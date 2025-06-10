#!/bin/bash

# Script to generate and test various project configurations
# This script generates different project layouts and tests them with Gradle

# Navigate to the project root directory
cd "$(dirname "$0")/.."

# Build the CLI fat binary
echo "Building project generator CLI..."
./gradlew :cli:fatbinary

# Navigate to the CLI directory
cd cli || exit 1

# Create a log file
LOG_FILE="project_test_results.log"
echo "Starting project generation and testing at $(date)" > "$LOG_FILE"

# Function to generate and test a project
generate_and_test() {
    local config_name="$1"
    local shape="$2"
    local modules="$3"
    shift 3
    local extra_args=("$@")

    echo "======================================================="
    echo "Generating project: $config_name"
    echo "Shape: $shape, Modules: $modules"
    echo "Extra args: ${extra_args[*]}"

    # Generate the project
    ./projectGenerator --shape "$shape" --modules "$modules" "${extra_args[@]}"

    if [ $? -ne 0 ]; then
        echo "FAILED: Project generation failed for $config_name" | tee -a "$LOG_FILE"
        return 1
    fi

    # Navigate to the generated project
    local project_dir="./projects_generated/${shape}_${modules}/project_kts"
    cd "$project_dir" || {
        echo "FAILED: Could not find generated project at $project_dir" | tee -a ../../"$LOG_FILE"
        return 1
    }

    # Run Gradle build
    echo "Building project with gradlew assembleDebug..."
    ./gradlew assembleDebug --no-daemon

    if [ $? -eq 0 ]; then
        echo "SUCCESS: Project $config_name built successfully" | tee -a ../../"$LOG_FILE"
        result="SUCCESS"
    else
        echo "FAILED: Project $config_name failed to build" | tee -a ../../"$LOG_FILE"
        result="FAILED"
    fi

    # Return to CLI directory
    cd ../../

    echo "$config_name: $result" >> "$LOG_FILE"
    echo "======================================================="
    echo ""
}

# Test a variety of configurations
echo "Starting tests for different project configurations"

# Small projects
generate_and_test "Small Triangle" "triangle" "30" "--layers" "3"
generate_and_test "Small Rhombus" "rhombus" "40" "--layers" "3"

# Medium projects with different types
generate_and_test "Medium Android Flat" "flat" "100" "--type" "android"
generate_and_test "Medium JVM Rectangle" "rectangle" "120" "--type" "jvm" "--classesModule" "8"

# Complex projects with varying configurations
generate_and_test "Large Bottleneck" "middle_bottleneck" "200" "--layers" "5" "--classesModuleType" "random" "--classesModule" "10"
generate_and_test "Large Inverse Triangle" "inverse_triangle" "180" "--layers" "6" "--generateUnitTest"

# Different Gradle versions
generate_and_test "Gradle 8.2 Project" "triangle" "50" "--gradle" "gradle_8_2" "--agpVersion" "8.2.0"
generate_and_test "Gradle 8.13 Project" "triangle" "50" "--gradle" "gradle_8_13" "--agpVersion" "8.9.0"

# Different language settings
generate_and_test "Both Languages Project" "rhombus" "60" "--language" "both" "--layers" "4"

# Generate some extreme cases
generate_and_test "Minimal Project" "flat" "10" "--layers" "1" "--classesModule" "2"
generate_and_test "Large Project" "rectangle" "300" "--layers" "5" "--typeOfStringResources" "large"

echo "All tests completed. Check $LOG_FILE for results."
