# ApiDiff
This application is able to determine differences in API specifications with focus on potentially privacy related changes.
ApiDiff uses the [openapi-diff](https://github.com/OpenAPITools/openapi-diff) java library to compare OpenAPI specifications and implements further logic to filter potentially privacy related changes.
It is written in Java and uses the Spring Boot framework.

# Maven
Build the java project using [maven](https://maven.apache.org/).

`` mvn clean package ``
# Build
`` docker build . -t api-diff ``

# Run
`` docker run -p 8080:8080 apidiff ``

The application will run under http://localhost:8080/ .

# Endpoints

## Mock Render Endpoints
The mock render endpoint are only exposing the basic comparison functionality of the openapi-diff library. It renders the compared APIs using the Render classes of openapi-diff.
No filtering is done here.

You can choose from the provided example API specification and compare them by changing the ``<oldApi.yaml>`` and ``<newApi.yaml>`` parameter values in the request. A list of all provided example API specification can be found in the ``yamls`` folder in the project.

- ``/mock/console?oldApi=<oldApi.yaml>&newApi=<newApi.yaml>`` : Console Output of differences (preferably use something like PostMan for this request for better visualization)
- ``/mock/html?oldApi=<oldApi.yaml>&newApi=<newApi.yaml>`` : Html representation of differences
- ``/mock/json?oldApi=<oldApi.yaml>&newApi=<newApi.yaml>`` : Json representation of differences (Note: Json file is usually very big)
- ``/mock/markdown?oldApi=<oldApi.yaml>&newApi=<newApi.yaml>`` :  Markdown representation of differences

## Relevant Changes Endpoint
This endpoint is exposing the functionality to compare to OpenAPI specifications, analyze and filter the differences and only return the differences which are potentially related to privacy. Moreover, the returned differences are represented in a compacter way to eliminate unnecessary large responses.
