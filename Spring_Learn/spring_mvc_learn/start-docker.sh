#!/bin/bash
set -e
rm -rf /usr/local/tomcat/webapps/spring_mvc_learn
# tar -xvf /usr/local/tomcat/webapps/spring_mvc_learn.war -C /usr/local/tomcat/webapps/spring_mvc_learn
exec catalina.sh run