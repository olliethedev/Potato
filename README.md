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
    compile 'com.beastpotato.potato:potato_api:1.8'
    apt 'com.beastpotato.potato:potato_compiler:1.8'
    compile 'org.glassfish.main:javax.annotation:4.0-b33'
    ...
}
...
```
* **com.beastpotato.potato:potato_api** is the library containing annotation deffinitions and other various utility classes used by genereated classes.
* **com.beastpotato.potato:potato_compiler** is the annotation processor that converts annotated data into generated classes for you to use.
* **org.glassfish.main:javax.annotation** is needed for the generated classes.

## 3.Define endpoint
We will define an endpoint for getting movie and tv show data in json format from the server.
  1. Create class and add annotation.
  
  ```java
@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/discover/{discover_type}", jsonExample = "paste sample json response here")
public abstract class DiscoverMovieEndpoint {
...
}
  ```
  2. In the endpoint definition class add fields that will be converted into Url Params, Url Path Params, and Header Params.
  
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
