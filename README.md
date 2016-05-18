# Anton-Savitsky

New!!! Added angular-js client with websockets support

AngularJS version of UI is now available online on http://carproducer-e5am.rhcloud.com/angular-js/html/cars.main.html

CAR-PRODUCER

Version 1.0

This is a short description of how you can run this application.

1. Software you'll need: JDK 8 or higher, Tomcat server, browser.

2. Startup of the application:

- Download the app

- Go to the root directory Anton-Savitsky

- Run the command: mvn clean install -PnoITest
(-PnoITest needed to not run integration tests in the consumer-rest-impl module)

1) If you want to run JavaScript version of the UI:

- go to the Anton-Savitsky/rest-service-provider/target/ directory

- copy WAR file rest-service-provider-1.0-SNAPSHOT.war to the webapps directory of your tomcat server

- restart tomcat

- go to app-js-client and open index.html in a browser (alternatively you may use angular-js version of client)

The default URI for the REST service (PREFIX_URL) is http://localhost:8080/app-rest-1.0-SNAPSHOT/

which is to be configured in the app-js-client/js/properties.js,
this one can be changed upon moving of the REST service to another server.

You can also run REST service on the Jetty server

- go to Anton-Savitsky/rest-service-provider/

- run the following command: mvn jetty:run

- then you need to change the URI for REST service (PREFIX_URL) to http://localhost:8081/rest/

- open js-client/index.html in browser

2) If you want to run the application with JSP UI:

- go to the Anton-Savitsky

- you may need to check the path variable restServicesPrefix (URL of the rest-service-provider) 
which is configured in prefix.properties file in web-app/src/main/resources

- run mvn clean install
 
- copy rest-service-provider/target/rest-service-provider-1.0-SNAPSHOT.war and 
web-app/target/web-app-1.0-SNAPSHOT.war to the webapps folder of the Tomcat server

- restart the server

- now the application is available on the path:
http://localhost:8080/web-app-1.0-SNAPSHOT/car which is initial page
