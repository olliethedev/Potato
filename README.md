# Potato
Android library for generating request object and response object from server endpoint definition using annotation processing. 
* Eliminates the need to write boilerplate code for network requests and responses.
* Request object is generated based on endpoint definition.
* Response object is generated from json example in the endpoint definition.
![alt tag](http://i66.tinypic.com/316u5ur.png)

#Get started
## 1.Setup project's build.gradle:
```gradle
...
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        ...
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.6'
    }
}
...
```
This adds a dependency for **com.neenbedankt.gradle.plugins:android-apt** plug-in that is required to run Potato library's annotation processor.

## 2.Setup app module's build.gradle:
```gradle
apply plugin: 'com.neenbedankt.android-apt'
...
dependencies {
    compile 'com.beastpotato.potato:potato_api:2.8'
    apt 'com.beastpotato.potato:potato_compiler:2.8'
    compile 'org.glassfish.main:javax.annotation:4.0-b33'
    compile 'com.j256.ormlite:ormlite-core:4.48'
    ...
}
...
```
* **com.beastpotato.potato:potato_api** is the library containing annotation deffinitions and other various utility classes used by genereated classes.
* **com.beastpotato.potato:potato_compiler** is the annotation processor that converts annotated data into generated classes for you to use.
* **org.glassfish.main:javax.annotation** is needed for the generated classes.
* **com.j256.ormlite:ormlite-core:4.48** is needed ONLY if you are using the @JsonToModel with modelType = Constants.ModelType.GSON_AND_ORM_LITE_COMPAT.

## 3.Define endpoint
We will define an endpoint for getting movie and tv show data in json format from the server.
  1. Create class and add annotation.
  
  ```java
@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/discover/{discover_type}", jsonExample = "paste sample json response here")
public abstract class DiscoverMovieEndpoint {
...
}
  ```
  2. In the endpoint definition class add fields that will be converted into Url Params, Url Path Params, Header Params and body.
  
  ```java
    ...
    @UrlPathParam("discover_type")
    String discoverType;

    @UrlParam("api_key")
    String apiKey;

    @UrlParam("page")
    Integer page;

    @UrlParam("sort_by")
    String sortBy;

    @HeaderParam("Content-Type")
    String contentType;
    
    @Body
    String body;
    ...
    ```
    
  3. This step is optional but you can add validation logic. 
    ```java
    ...
    public class Validator {

      @Validation(fieldName = "api_key")
      public static boolean isApiKeyValid(String key){
          return key != null;
      }
  
      @Validation(fieldName = "page")
      public static boolean isPageNumberValid(Integer page){
          return page >= 1 && page <= 1000;
      }
      
      @Validation(fieldName = "discover_type")
      public static boolean isDiscoveryTypeValid(String discoveryStr){
          return discoveryStr!=null && (discoveryStr.equals("tv") || discoveryStr.equals("movie"));
      }
    }
    ```

## 4. Build! 
This will generate **DiscoverMovieEndpointApiRequest** in app>build>generated>source>apt>debug>packagename> aswell as **DiscoverMovieEndpointApiResponse**  in app>build>generated>source>apt>debug>packagename>response. 
* **DiscoverMovieEndpointApiRequest** contains getters and setters for the fields we defined in step **3.2** aswell as logic for validation and making the network call and parsing the response to POJO model. 
* **DiscoverMovieEndpointApiResponse** contains POJO model representing json string set in **jsonExample** parameter in step **3.1**.

## 5. Use the generated classes.
  1. Add internet permission to AndroidManifest.xml
  
  ```xml
  <uses-permission android:name="android.permission.INTERNET" />
  ```
  2. Set values in the Request object.
  
  ```java
  ...
  DiscoverMovieEndpointApiRequest discoverRequest = new DiscoverMovieEndpointApiRequest("http://api.themoviedb.org/3",getApplicationContext());
  discoverRequest.setApiKey("2e2ddf0d141ab64938cf49b95e458392");
  discoverRequest.setContentType("application/json");
  discoverRequest.setDiscoverType("movie");
  discoverRequest.setPage(10);
  ...
  ```
  
  3. This step is optional. You can handle field validation failures.
  
  ```java
  ...
  List<DiscoverMovieEndpointApiRequest.Fields> invalidFields = discoverRequest.validateFields();
  for(DiscoverMovieEndpointApiRequest.Fields field : invalidFields){
      Toast.makeText(getApplicationContext(),"Field "+field.name()+" failed validation!",Toast.LENGTH_LONG).show();
      switch (field) {
          case apiKey:
              //handle case where apiKey is null
              break;
          case page:
              //handle case where page number is less than 1 or greater than 100
              break;
      }
  }
  ...
  ```
  
  4. Send request and handle response.
  
  ```java
  discoverRequest.send(new ApiRequest.RequestCompletion<DiscoverMovieEndpointApiResponse>() {
      @Override
      public void onResponse(DiscoverMovieEndpointApiResponse data) {
          textView.setText("First movie title in response:" + data.getResults().get(0).getOriginalTitle());
      }

      @Override
      public void onError(VolleyError error) {
          error.printStackTrace();
          Toast.makeText(getApplicationContext(),"request failed!",Toast.LENGTH_LONG).show();
      }
  });
  ```
  
## 6. Profit.
* Simple example project available in this repository. Go to the **app** module or click [HERE](https://github.com/beast-potato/Potato/tree/master/app).
* Advanced example project available [HERE](https://github.com/beast-potato/Movie-Info-App). This project demonstrates use of Potato Library with Android Data Binding Library.

#Other Annotations
## 1. JsonToModel 
- fields annotated with this annotation will be turned into POJO representation of jsonString value
    ```java
    ...
    public class TestJsonStrings {
    @JsonToModel(jsonString = "" +
            "{\"employees\":[\n" +
            "    {\"firstName\":\"John\", \"lastName\":\"Doe\"},\n" +
            "    {\"firstName\":\"Anna\", \"lastName\":\"Smith\"},\n" +
            "    {\"firstName\":\"Peter\", \"lastName\":\"Jones\"}\n" +
            "]}")
    public String EmployeesModel;

    @JsonToModel(jsonString = "{\n" +
            "  \"firstName\": \"John\",\n" +
            "  \"lastName\": \"Smith\",\n" +
            "  \"isAlive\": true,\n" +
            "  \"age\": 25,\n" +
            "  \"address\": {\n" +
            "    \"streetAddress\": \"21 2nd Street\",\n" +
            "    \"city\": \"New York\",\n" +
            "    \"state\": \"NY\",\n" +
            "    \"postalCode\": \"10021-3100\"\n" +
            "  },\n" +
            "  \"phoneNumbers\": [\n" +
            "    {\n" +
            "      \"type\": \"home\",\n" +
            "      \"number\": \"212 555-1234\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"office\",\n" +
            "      \"number\": \"646 555-4567\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"mobile\",\n" +
            "      \"number\": \"123 456-7890\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"children\": [],\n" +
            "  \"spouse\": null\n" +
            "}")
    public String PersonModel;
    ```
    will be tuned into 
    ![alt tag](http://i64.tinypic.com/2hf1hk4.jpg)

    * use modelType = Constants.ModelType.GSON_AND_ORM_LITE_COMPAT if you want the generated model to be compatible with OrmLite database. Remember to include compile 'com.j256.ormlite:ormlite-core:4.48' in app module's build.gradle
