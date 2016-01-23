# Anton-Savitsky
CAR-PRODUCER
Version 1.0

This is a short description of how you can run this application.
1. Software you'll need: Java version 8 or higher, Tomcat server, browser.
2. Startup of the application:
-Download the app
-Go to the root directory Anton-Savitsky
-Run the command: mvn clean install
If you want to run JavaScript version of the UI:
-go to the Anton-Savitsky/app-rest/target/ directory
-copy WAR file app-rest-1.0-SNAPSHOT.war to the webapps directory of your tomcat server
-restart tomcat
-go to app-js-client and open index.html in a browser
The default URI for the REST service (PREFIX_URL) is http://localhost:8080/app-rest-1.0-SNAPSHOT/
which is configured in the app-js-client/js/properties.js,
this one can be changed upon moving of the REST service to another server.
You can also run REST service on the Jetty server
-go to Anton-Savitsky/app-rest/
-run the following commands:
mvn clean install
mvn jetty:run
-then you need to change the URI for REST service (PREFIX_URL) to http://localhost:8081/rest/
-open app-js-client/index.html in browser