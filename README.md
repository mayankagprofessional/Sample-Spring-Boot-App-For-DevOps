##### Table of Contents
* [Description](#description)  
* [Assumptions](#assumptions)  
* [Dependencies](#dependencies)  
* [Instructions to run](#instructions-to-run)  
* [Documentation](#documentation)  
* [Testing](#testing)  
* [Database](#database)  
* [Logging/Monitoring](#loggingmonitoring)  
* [Hosting](#hosting)  
* [Deployment](#deployment)  
* [Possible improvements](#possible-improvements)  

<a name="description"></a>
## Description
A Sample backend service based on spring boot for all Job Application. In this application the user needs to register an account and then login in the account created. Once logged in the user will receive the JWT token which will be used to perform all the other transactions. 

<a name="assumptions"></a>
## Assumptions
* NA

<a name="dependencies"></a>
## Dependencies
* [Maven](https://maven.apache.org/)

<a name="instructions-to-run"></a>
## Instructions to run
1. Open project in any IDE as a maven project. And run the main file:
```bash
src/main/java/info/mayankag/springscale/SpringScaleApplication.java
``` 

<a name="documentation"></a>
## Documentation
OpenAPI Specification Swagger documentation is at the default page.  
[http://localhost:8080/](http://localhost:8080/)


<a name="testing"></a>
## Testing
Till now only completed Acceptance testing for all the endpoints. To run the aceptance test please run the below file
```bash
src/test/java/info/mayankag/springscale/user/controller/UserAcceptanceTest.java
``` 

To test the application in command line navigate to the root directory and run the below command
```bash
./mvnw clean test
```


<a name="database"></a>
## Database
[H2 Database](https://www.h2database.com/)
Used H2 Local Database for the complete application but can be easily modified for MySQL or any other SQL database


<a name="loggingmonitoring"></a>
## Logging/Monitoring
[Lombok](https://projectlombok.org/) Slf4j logging is used for the ease of implementation.

<a name="hosting"></a>
## Hosting
This solution will be dockerized in the future to provide easy hosting service.

<a name="deployment"></a>
## Deployment
Since, the service is a containerized solutions we can easily create a CI/CD pipeline using [Jenkins](https://www.jenkins.io/)
or [GitLab](https://docs.gitlab.com/ee/ci/) and create a dockerized image which then can further be deployed.

Note: For now deployment is done using the jar file, which can be generated using the command in the IDE:
```bash
mvn clean package
```

To generate the jar file using command line navigate to the root directory and run the below command
```bash
./mvnw clean package
```

After successful completion of this command, the jar file can be found in the target folder.

* Host the jar file with command:
```bash
nohup java -jar SpringScale-1.0.jar &
```

<a name="possible-improvements"></a>
## Possible improvements
* NA
