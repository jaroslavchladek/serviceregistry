# ServiceRegistry

## Configuration

Make sure there is a running Docker daemon instance on your machine.

```bash
dockerd
```

Then you may successfully generate a runnable *JAR* file of the application.

```bash
mvn clean && install
```

The application will automatically create a Docker container running the application with a RabbitMQ subcontainer.

```bash
java <application-name>.jar
```

Now you are free to make requests as per the OpenAPI.

```
Hint: make sure the Docker and Maven executables (dockerd/dockerd.exe and mvn/mvn.exe) are present
in the Path variable of your system.
```