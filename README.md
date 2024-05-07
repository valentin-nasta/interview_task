# Interview task
With this task we would like to test your frontend skills. 
Therefore, we have created a simple backend. We ask you to create a suitable frontend.
The provided backend contains very common functionality like a REST api and Websocket, both is described in the `openapi` directory.

## Task
What we want you to do:
* create a graphical representation of provided metrics (e.g. chart)
* apply a filter to display different day or different components
* use websocket data to update the graph in realtime

You can use the framework you are the most comfortable with. 

## Setup
To get the backend running you can use either docker or the java environment. 

The main advantage of
docker is, that you are not required to install any additional tools, just build and the image and execute it. ('Useful commands' option 1)

Different for the java environment. In order to get the backend running you need to have jdk21 installed.
Maven is optional here, since we provide a wrapper script in the repository (mvnw and mvnw.cmd)
('Useful commands' Options 2 and 3 )


## Useful commands
### Build docker
```bash
docker build -f src/main/docker/Dockerfile.jvm . -t interview-backend
```
### Run the backend
1) Docker 
    ```bash
    docker run -p 8080:8080 interview-backend:latest
    ```
2) maven (jdk21 is required)
   ```bash
   ./mvnw quarkus:dev #if maven is installed locally, no wrapper needed (mvn quarkus:dev)
    ```
3) quarkus (jdk21 is required)
   ```bash
   quarkus dev #only possible, if quarkus is installed locally
   ```   