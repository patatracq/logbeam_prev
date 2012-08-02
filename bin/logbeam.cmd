
#set APP_HOME=C:\Program Files\logbeam
#set JAVA_HOME=C:\Program Files\Java\jre1.6.0_22

@cd "C:\Program Files\logbeam"
@java -Dhttp.proxyHost=proxy.sfa.se -Dhttp.proxyPort=8080 -classpath bin\logbeam-client.jar;config;lib\org.springframework.context-3.1.0.RELEASE.jar;lib\org.springframework.core-3.1.0.RELEASE.jar;lib\org.springframework.beans-3.1.0.RELEASE.jar;lib\commons-logging-1.1.1.jar;lib\org.springframework.asm-3.1.0.RELEASE.jar;lib\org.springframework.expression-3.1.0.RELEASE.jar;lib\spring-integration-core-2.1.0.RELEASE.jar;lib\spring-integration-ip-2.1.0.RELEASE.jar;lib\log4j-1.2.16.jar;lib\slf4j-api-1.6.1.jar;lib\slf4j-log4j12-1.6.1.jar;lib\org.springframework.aop-3.1.0.RELEASE.jar;lib\aopalliance-1.0.jar springclient.SpringClient client-config.xml
