# ApiDiff
This application is able to determine differences in API specifications with focus on potentially privacy related changes.
ApiDiff uses the [openapi-diff](https://github.com/OpenAPITools/openapi-diff) java library to compare OpenAPI specifications and implements further logic to filter potentially privacy related changes.
It is written in Java and uses the Spring Boot framework.

# Build
`` docker build . -t apidiff ``

# Run
`` docker run -p 8080:8080 apidiff ``

The application will run under http://localhost:8080/ .

#OpenAPI Spec
The application generates a Swagger-UI page on startup exposed at:
`` /swagger-ui.html ``

The OpenAPI spec can be found on 
``/v3/api-docs`` as well as in the ``resources`` folder

# Endpoints


## ApiDiff Endpoints

### Relevant Changes Endpoint
This endpoint is exposing the functionality to compare to OpenAPI specifications, analyze and filter the differences and only return the differences which are potentially related to privacy. Moreover, the returned differences are represented in a compacter way to eliminate unnecessary large responses. You can define the OpenApi specification which should be compared in the RequestBody of the request.

- ``/apidiff/relevantChanges``

  *RequestBody*: you can define the old and new Api Specification which should be compared here in this format with JSON:
  
  ```
  {
    "oldApiSpec": {...},
    "newApiSpec": {...}
  }
  ```
  Note: You also can define the specifications in the RequestBody with yaml. For this the HTTP request header `Content-Type` must be set to `application/x-yaml` 
  ```
  oldApiSpec:
    ...
  newApiSpec:
    ...
  ```

- ``/apidiff/relevantChangesFromSamples?oldApi=<oldApi.yaml>&newApi=<newApi.yaml>``

  Same functionality as ``/relevantChanges``, but you can specify the OpenApi specification which should be compared in the request parameters (see Render Endpoints below for details).
  This endpoint serves only for easier and faster development.

A response will have this format:

```
{
    "generalDifferenceGiven": false,
    "potentiallyPrivacyRelatedDifferencesGiven": false,
    "newEndpoints": [ ... ],
    "missingEndpoints": [ ... ],
    "changedOperations": [ ... ]
}
```

### Tira Endpoints
This endpoint will analyze the supplied OpenAPI specifications for TIRA annotations and analyze the difference.

- `` /apidiff/tira ``

  *RequestBody*: you can define the old and new Api Specification which should be compared here in this format with JSON:

  ```
  {
    "oldApiSpec": {...},
    "newApiSpec": {...}
  }
  ```

  Note: You also can define the specifications in the RequestBody with yaml. For this the HTTP request header `Content-Type` must be set to `application/x-yaml`
    ```
    oldApiSpec:
      ...
    newApiSpec:
      ...
  ```

- `` /apidiff/tiraFromSamples?oldApi=<oldApi.yaml>&newApi=<newApi.yaml>``
  
  Same functionality as ``/tira``, but you can specify the OpenApi specification which should be compared in the request parameters (see Render Endpoints below for details).
  This endpoint serves only for easier and faster development. 

A response will have this format:

```
{
    "newGlobalTiraAnnotation": [ ... ],
    "missingGlobalTiraAnnotation": [ ... ],
    "changedGlobalTiraAnnotation": [ ... ],
    "newSchemaTiraAnnotations": [ ... ],
    "missingSchemaTiraAnnotations": [ ... ],
    "changedSchemaTiraAnnotations": [ ... ]
}
```

## Render Endpoints
The render endpoints are only exposing the basic comparison functionality of the openapi-diff library. It renders the compared APIs using the Render classes of openapi-diff.
No filtering is done here.


To choose the render type you have to set the `type` request parameter to your desired value. Possible values for  ``type`` are either html, json, markdown or console
- `/render/fromSamples?type=<type>&oldApi=<oldApi.yaml>&newApi=<newApi.yaml>`:
  
  You can choose from the provided example API specification samples and compare them by changing the ``<oldApi.yaml>`` and ``<newApi.yaml>`` parameter values in the request. A list of all provided example API specification can be found in the ``yamls`` folder in the project.

  
- `/render/fromRequestBody?type=<type>`:
  This endpoint has the same functionality as `/render/fromSamples`. However you can define the OpenApi specification which should be compared in the RequestBody of the request.

  *RequestBody*: you can define the old and new Api Specification which should be compared here in this format with JSON:

  ```
  {
    "oldApiSpec": {...},
    "newApiSpec": {...}
  }
  ```

  Note: You also can define the specifications in the RequestBody with yaml. For this the HTTP request header `Content-Type` must be set to `application/x-yaml`
  ```
  oldApiSpec:
    ...
  newApiSpec:
    ...
  ```

## Responses
Some example responses from calls to the ApiDiff service with specifications from the samples in the `yamls` folder.

- ### `/relevantChanges`

  **Request**:
  http://localhost:8080/apidiff/relevantChangesFromSamples?oldApi=path_1.yaml&newApi=path_4.yaml
  
  **Response**:
  
  ```
  {
      "generalDifferenceGiven": true,
      "potentiallyPrivacyRelatedDifferencesGiven": true,
      "newEndpoints": [
          {
              "pathUrl": "/pet/{petId2}",
              "method": "POST",
              "summary": "deletes a pet",
              "pathIsNew": true
          }
      ],
      "missingEndpoints": [
          {
              "pathUrl": "/calc/{petId}",
              "method": "GET",
              "summary": "gets a pet by id",
              "pathIsStillPresent": false
          },
          {
              "pathUrl": "/pet/{petId}",
              "method": "DELETE",
              "summary": "gets a pet by id",
              "pathIsStillPresent": true
          }
      ],
      "changedOperations": [
          {
              "pathUrl": "/pet/{petId}",
              "method": "GET",
              "changedFields": [
                  "summary",
                  "parameters"
              ]
          }
      ]
  }
  ```

- ### `/tira`

  **Request**:
  http://localhost:8080/apidiff/tiraFromSamples?oldApi=tira_1.yaml&newApi=tira_3.yaml
  
  **Response**:
  
  ```
  {
      "newGlobalTiraAnnotation": {
          "utilizer_category": [
              {
                  "name": "Health Insurance Company"
              }
          ]
      },
      "missingGlobalTiraAnnotation": {
          "profiling": {
              "reason": "Health profile based on series of health related behaviour."
          }
      },
      "changedGlobalTiraAnnotation": {
          "oldGlobalTiraAnnotation": {
              "utilizer": [
                  {
                      "name": "AWS",
                      "non_eu_country": true,
                      "country": "UK"
                  }
              ]
          },
          "newGlobalTiraAnnotation": [
              {
                  "name": "AWS2",
                  "non_eu_country": true,
                  "country": "UK"
              }
          ]
      },
      "newSchemaTiraAnnotations": [
          {
              "schemaName": "JumpEvent",
              "schemaTiraAnnotation": {
                  "retention-time": {
                      "volatile": true
                  },
                  "special_category": {
                      "category": "Health Data"
                  },
                  "profiling": {
                      "reason": "Health profile based on series of health related behaviour."
                  }
              }
          }
      ],
      "missingSchemaTiraAnnotations": [
          {
              "schemaName": "DanceEvent",
              "schemaTiraAnnotation": {
                  "retention-time": {
                      "volatile": true
                  },
                  "special_category": {
                      "category": "Health Data"
                  },
                  "purposes": {
                      "yappl": "{ \"id\":123, \"preference\":[ { \"rule\":{ \"purpose\":{ \"permitted\": [ \"FitnessData Sharing\", \"Health Insurance Bonus Program\" ], \"excluded\": [ ... ] }, \"utilizer\":{ \"permitted\": [ ... ], \"excluded\": [ ... ] }, \"transformation\": [ ... ], \"valid_from\":\"2021-06-09T00:00:00.000Z\", \"exp_date\":\"0000-01-01T00:00:00.000Z\" } } ] }"
                  },
                  "profiling": {
                      "reason": "Health profile based on series of health related behaviour."
                  },
                  "utilizer": [
                      {
                          "name": "MyFitnessPal",
                          "non_eu_country": false
                      },
                      {
                          "name": "Strava",
                          "non_eu_country": true,
                          "country": "USA"
                      }
                  ],
                  "utilizer_category": [
                      {
                          "name": "Health Insurance Company",
                          "country": "Germany",
                          "non_eu_country": false,
                          "type": "Insurance Company",
                          "sector": "Insurance",
                          "sub_sector": [
                              "Health Insurance",
                              "Health Tax"
                          ]
                      }
                  ]
              }
          }
      ],
      "changedSchemaTiraAnnotations": [
          {
              "schemaName": "SameEvent",
              "oldSchemaTiraAnnotation": {
                  "retention-time": {
                      "volatile": true
                  },
                  "special_category": {
                      "category": "Health Data"
                  }
              },
              "newSchemaTiraAnnotation": {
                  "retention-time": {
                      "volatile": true
                  }
              }
          }
      ]
  }
  ```