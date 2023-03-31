FROM tomcat:8.5.85-jre8-temurin-jammy
COPY target/SSM_learn.war /usr/local/tomcat/webapps/SSM_learn.war
COPY start-docker.sh /usr/local/tomcat/start.sh
RUN chmod +x /usr/local/tomcat/start.sh
EXPOSE 8080
CMD ["/usr/local/tomcat/start.sh"]