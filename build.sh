#!/bin/sh

cd src/java

javac GenCharFunkData.java
java GenCharFunkData

javac GenDoco.java
java GenDoco

rm -f *.class

cd ../..
npx grunt

echo "Launch tests with:\n"
echo "npm run test"
