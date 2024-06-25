#!/bin/bash

# Ensure the script exits if any command fails
set -e

# Fetch the latest changes from the main branch
git fetch origin main

# Fetch the list of changed Java files compared to the main branch, excluding deleted files
changed_files=$(git diff --name-status main | grep '.java' | grep -v '^D' | awk '{print $2}')

# Check if any Java files have changed
if [ -z "$changed_files" ]; then
  echo "No Java files have changed compared to main."
  exit 0
fi

# Construct the targetClasses and targetTests parameters for PIT
target_classes=""
target_tests=""
for file in $changed_files; do
  # Check if the file is in the src/main/java directory
  if [[ $file == src/main/java/* ]]; then
    # Remove the src/main/java/ prefix and the .java suffix, then replace slashes with dots
    class_name=$(echo $file | sed 's|src/main/java/||; s|/|.|g; s|.java$||')

    # Get the package name by removing the class name from the end
    package_name=${class_name%.*}

    # Add the changed class to the targetClasses
    target_classes="$target_classes,$class_name"

    # Find all Test classes in the exact same package
    test_classes=$(find src/test/java -type f -name "*Test.java" | grep "^src/test/java/$(echo $package_name | tr '.' '/')" || true)

    # Check if any test classes were found
    if [ -z "$test_classes" ]; then
      continue
    fi

    for test_file in $test_classes; do
      # Remove the src/test/java/ prefix and the .java suffix, then replace slashes with dots
      test_class_name=$(echo $test_file | sed 's|src/test/java/||; s|/|.|g; s|.java$||')

      # Add the test class to the targetTests
      target_tests="$target_tests,$test_class_name"
    done
  fi
done

# Convert the comma-separated strings to arrays, sort them, and remove duplicates
IFS=',' read -r -a target_classes_array <<< "$target_classes"
IFS=',' read -r -a target_tests_array <<< "$target_tests"
target_classes=$(printf '%s\n' "${target_classes_array[@]}" | sort -u | tr '\n' ',')
target_tests=$(printf '%s\n' "${target_tests_array[@]}" | sort -u | tr '\n' ',')

# Trim the leading and trailing commas
target_classes=${target_classes#,}
target_classes=${target_classes%,}
target_tests=${target_tests#,}
target_tests=${target_tests%,}

# Check if there are any valid target classes and tests
if [ -z "$target_classes" ]; then
  echo "No valid Java files have changed compared to main."
  exit 0
fi
if [ -z "$target_tests" ]; then
  echo "No valid Test classes have changed compared to main."
  exit 0
fi

# Print a new line
echo ""

# Print each target class on a new line
echo "Running PIT for the following classes:"
IFS=',' read -r -a target_classes_array <<< "$target_classes"
for class in "${target_classes_array[@]}"; do
  echo "$class"
done

# Print the count of target classes
echo "Total number of target classes: ${#target_classes_array[@]}"

# Print a new line
echo ""

# Print each target test on a new line
echo "Running PIT for the following test classes:"
IFS=',' read -r -a target_tests_array <<< "$target_tests"
for test in "${target_tests_array[@]}"; do
  echo "$test"
done

# Print the count of target tests
echo "Total number of target tests: ${#target_tests_array[@]}"

# Run PIT with the dynamically generated targetClasses and targetTests
mvn clean
mvn test-compile org.pitest:pitest-maven:mutationCoverage -DtargetClasses="$target_classes" -DtargetTests="$target_tests"
