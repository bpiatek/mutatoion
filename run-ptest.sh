#!/bin/bash

# Fetch the list of changed Java files compared to the main branch
changed_files=$(git diff --name-only main | grep '.java' | sed 's|/|.|g' | sed 's/.java//g')

# Check if any Java files have changed
if [ -z "$changed_files" ]; then
  echo "No Java files have changed compared to main."
  exit 0
fi

# Construct the targetClasses parameter for PIT
target_classes=""
for file in $changed_files; do
  target_classes="${target_classes}<param>${file}</param>"
done

# Create a temporary Maven settings file to include the targetClasses
temp_maven_settings=$(mktemp)
cat > $temp_maven_settings <<EOL
<settings>
  <profiles>
    <profile>
      <id>pitest-changed</id>
      <properties>
        <targetClasses>$target_classes</targetClasses>
      </properties>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>pitest-changed</activeProfile>
  </activeProfiles>
</settings>
EOL

# Run PIT with the dynamically generated targetClasses
mvn org.pitest:pitest-maven:mutationCoverage --settings $temp_maven_settings

# Clean up the temporary Maven settings file
rm $temp_maven_settings
