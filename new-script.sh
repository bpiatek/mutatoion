#!/bin/bash

# Ensure the script exits if any command fails
set -e

# Fetch the latest changes from the main branch
#git fetch origin main

# Fetch the list of changed Java files compared to the main branch, excluding deleted files
changed_files=$(git diff --name-status main | grep '.java' | grep -v '^D' | awk '{print $2}')

# Check if any Java files have changed
if [ -z "$changed_files" ]; then
  echo "No Java files have changed compared to main."
  exit 0
fi

# Construct the targetClasses parameter for PIT
target_classes=""
for file in $changed_files; do
  # Check if the file is in the src/main/java directory
  if [[ $file == src/main/java/* ]]; then
    # Remove the src/main/java/ prefix and the .java suffix, then replace slashes with dots
    class_name=$(echo $file | sed 's|src/main/java/||; s|/|.|g; s|.java$||')
    if [ -z "$target_classes" ]; then
      target_classes="$class_name"
    else
      target_classes="$target_classes,$class_name"
    fi
  fi
done

# Check if there are any valid target classes
if [ -z "$target_classes" ]; then
  echo "No valid Java files have changed compared to main."
  exit 0
fi

echo "Running PIT for the following classes: $target_classes"

# Run PIT with the dynamically generated targetClasses
mvn -X test-compile org.pitest:pitest-maven:mutationCoverage -DtargetClasses=$target_classes
