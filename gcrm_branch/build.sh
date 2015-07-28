#!/bin/bash

echo $MAVEN_OPTS
echo $JAVA_OPTS
export JAVA_HOME=$JAVA_HOME_1_6
export PATH=$JAVA_HOME/bin:$PATH

echo $MAVEN_OPTS
echo $JAVA_OPTS
echo SCMPF_MODULE_VERSION: $SCMPF_MODULE_VERSION

pwd
echo "gcrm mvn install..."

echo "check files..."

mvn -s settings.xml -U clean install -Pnightly -DskipTests=true
if [ $? -ne 0 ]; then
  exit 1
fi

pwd
mkdir output
cp -r target/*.war output