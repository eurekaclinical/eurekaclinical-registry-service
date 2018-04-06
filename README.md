# Eureka! Clinical Registry Service
[Atlanta Clinical and Translational Science Institute (ACTSI)](http://www.actsi.org), [Emory University](http://www.emory.edu), Atlanta, GA

The registry service provides a list of deployed Eureka! Clinical components. 
Its initial purpose is for resource discovery such as populating menus and other lists of
available components.

## Version history
Latest release: [![Latest release](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/eurekaclinical-registry-service/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/eurekaclinical-registry-service)

### Version 1.1
The properties in the application.properties were named in a non-standard way in version 1.0,
starting with eurekaclinical.registry.service rather than eurekaclinical.registryservice.
Version 1.1 added standard property names. It responds to either, and it prioritizes the original
non-standard names if both are present. The non-standard property names are deprecated and will be 
removed in a future release.

We also updated the README.md.

### Version 1.0
Initial release.

* [Oracle Java JDK 8](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [Maven 3.2.5 or greater](https://maven.apache.org)

* [Oracle Java JRE 8](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [Tomcat 7](https://tomcat.apache.org)
* One of the following relational databases:
  * [Oracle](https://www.oracle.com/database/index.html) 11g or greater
  * [PostgreSQL](https://www.postgresql.org) 9.1 or greater
  * [H2](http://h2database.com) 1.4.193 or greater (for testing)


Manages registering a user with this service for authorization purposes.

Call-dependent

Yes

Properties:
* `id`: unique number identifying the user (set by the server on object creation, and required thereafter).
* `username`: required username string.
* `roles`: array of numerical ids of roles.

All calls use standard names, return values and status codes as specified in the [Eureka! Clinical microservice specification](https://github.com/eurekaclinical/dev-wiki/wiki/Eureka%21-Clinical-microservice-specification)

Returns an array of all User objects. Requires the `admin` role.

Returns a specified User object by the value of its id property, which is unique. Requires the `admin` role to return any user record. Otherwise, it will only return the user's own record.

Returns a specified User object by its username, which is unique. Requires the `admin` role to return any user record. Otherwise, it will only return the user's own record.

Returns the User object for the currently authenticated user.

Creates a new user. The User object is passed in as the body of the request. Returns the URI of the created User object. Requires the `admin` role.

Updates the user object with the specified id. The User object is passed in as the body of the request. Requires the `admin` role.

Manages roles for this service. It is read-only.

No.

Yes

Properties:
* `id`: unique number identifying the role.
* `name`: the role's name string.

All calls use standard names, return values and status codes as specified in the [Eureka! Clinical microservice specification](https://github.com/eurekaclinical/dev-wiki/wiki/Eureka%21-Clinical-microservice-specification)

Returns an array of all User objects.

Returns a specified Role object by the value of its id property, which is unique.

Returns a specified Role object by its name, which is unique.

The project uses the maven build tool. Typically, you build it by invoking `mvn clean install` at the command line. For simple file changes, not additions or deletions, you can usually use `mvn install`. See https://github.com/eurekaclinical/dev-wiki/wiki/Building-Eureka!-Clinical-projects for more details.

You can run this project in an embedded tomcat by executing `mvn process-resources cargo:run -Ptomcat` after you have built it. It will be accessible in your web browser at https://localhost:8443/eurekaclinical-registry-service/. Your username will be `superuser`.

A [Liquibase](http://www.liquibase.org) changelog is provided in `src/main/resources/dbmigration/` for creating the schema and objects. [Liquibase 3.3 or greater](http://www.liquibase.org/download/index.html) is required.

Perform the following steps:
1) Create a schema in your database and a user account for accessing that schema.
2) Get a JDBC driver for your database and put it the liquibase lib directory.
3) Run the following:
```
./liquibase \
      --driver=JDBC_DRIVER_CLASS_NAME \
      --classpath=/path/to/jdbcdriver.jar:/path/to/eurekaclinical-registry-service.war \
      --changeLogFile=dbmigration/changelog-master.xml \
      --url="JDBC_CONNECTION_URL" \
      --username=DB_USER \
      --password=DB_PASS \
      update
```
4) Add the following Resource tag to Tomcat's `context.xml` file:
```
<Context>
...
    <Resource name="jdbc/EurekaClinicalServiceRegistryService" auth="Container"
            type="javax.sql.DataSource"
            driverClassName="JDBC_DRIVER_CLASS_NAME"
            factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
            url="JDBC_CONNECTION_URL"
            username="DB_USER" password="DB_PASS"
            initialSize="3" maxActive="20" maxIdle="3" minIdle="1"
            maxWait="-1" validationQuery="SELECT 1" testOnBorrow="true"/>
...
</Context>
```

The validation query above is suitable for PostgreSQL. For Oracle and H2, use
`SELECT 1 FROM DUAL`.

This service is configured using a properties file located at `/etc/ec-registry/application.properties`. It supports the following properties:
* `eurekaclinical.domain.url`: https://hostname:port
* `eurekaclinical.registryservice.callbackserver`: https://hostname:port
* `eurekaclinical.registryservice.url`: https://hostname:port/eurekaclinical-registry-service
* `cas.url`: https://hostname.of.casserver:port/cas-server

It also supports the following deprecated properties:
* `eurekaclinical.registry.service.callbackserver`: replaced by `eurekaclinical.registryservice.callbackserver`
* `eurekaclinical.registry.service.url`: replaced by `eurekaclinical.registryservice.url`

These deprecated properties will be removed in a future release.

A Tomcat restart is required to detect any changes to the configuration file.

1) Stop Tomcat.
2) Remove any old copies of the unpacked war from Tomcat's webapps directory.
3) Copy the warfile into the tomcat webapps directory, renaming it to remove the version. For example, rename `eurekaclinical-registry-service-1.0-SNAPSHOT.war` to `eurekaclinical-registry-service.war`.
4) Start Tomcat.

```
<dependency>
    <groupId>org.eurekaclinical</groupId>
    <artifactId>eurekaclinical-registry-service</artifactId>
    <version>version</version>
</dependency>
```

* [Javadoc for latest development release](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-registry-service) [![Javadocs](http://javadoc.io/badge/org.eurekaclinical/eurekaclinical-registry-service.svg)](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-registry-service)

Feel free to contact us at help@eurekaclinical.org.

