cd D:\Documents\Projects\eclipse\PlantillaSpring
call mvn clean install
copy "D:\Documents\Projects\eclipse\PlantillaSpring\target\PlantillaSpring.war" "D:\Servers\Apaches\apache-tomcat-10.1.11\webapps" /Y
SET CATALINA_HOME=D:\Servers\Apaches\apache-tomcat-10.1.11
call D:\Servers\Apaches\apache-tomcat-10.1.11\bin\startup.bat