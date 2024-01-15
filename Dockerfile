FROM tomcat:9.0-jdk17-openjdk

COPY modules/flowable-ui/flowable-ui-app/src/main/resources/modus.cer $JAVA_HOME/lib/security

RUN\
   cd $JAVA_HOME/lib/security\
   && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias moduscert -file modus.cer

COPY modules/flowable-ui/flowable-ui-app/target/flowable-ui.war /usr/local/tomcat/webapps/

EXPOSE 8080