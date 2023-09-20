# REST ASSURED - API automation framework

Java library (API) for testing RESTful web services.
Used to test JSON based web services.
Supports GET, POST, PATCH, PUT, DELETE.  Integrated with testing frameworks TestNG.

### Quick Start
- Java
- IntelliJ IDE
- Maven
- TestNG

Create a local REST API with JSON Server for testing
JSON Server: Used to create our own fake Rest API.

Installing JSON Server
npm install -g json-server
Start JSON Server
json-server --watch db.json
REFERENCE LINK: https://medium.com/codingthesmartway-com-blog/create-a-rest-api-with-json-server-36da8680136d

Created API will be available in the local server
http://localhost:3000/
To start Json Server on the machine IP address

json-server --host <<ipaddress>> --watch db.json
Demo Uri's
https://reqres.in/
http://ergast.com/mrd/
Validate JSON Schema
Create or Get JSON Schema
Add JSON Schema file in src/test/resources path
Add maven dependency for JSON Schema validator

## Test Application

Swagger Doc

https://jobportalkarate.herokuapp.com/swagger-ui.html
To access the application locally - Open terminal, navigate to the location of 'jobportal-0.0.1-SNAPSHOT' (\src\test\resources\microserviceapplication\) JAR file and execute the following command

java -jar -Dserver.port=5050 jobportal-0.0.1-SNAPSHOT.jar
This will start the server and the following url can be used to access the Swagger doc

http://localhost:5050/swagger-ui.html