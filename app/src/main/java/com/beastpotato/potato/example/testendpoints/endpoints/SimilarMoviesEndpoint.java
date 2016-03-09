package com.beastpotato.potato.example.testendpoints.endpoints;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;
import com.beastpotato.potato.api.UrlPathParam;

/**
 * Created by Oleksiy on 2/21/2016.
 */
@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/movie/{id}/similar", jsonExample = "{\n" +
        "  \"page\": 1,\n" +
        "  \"results\": [\n" +
        "    {\n" +
        "      \"adult\": false,\n" +
        "      \"backdrop_path\": \"/vmpyNlphrzFlsiG8XcnaGeQ93WW.jpg\",\n" +
        "      \"id\": 159,\n" +
        "      \"original_title\": \"Der Bewegte Mann\",\n" +
        "      \"release_date\": \"1994-10-05\",\n" +
        "      \"poster_path\": \"/7UEzKYus31qTtyGDftRhao7kxbb.jpg\",\n" +
        "      \"popularity\": 0.64515,\n" +
        "      \"title\": \"Maybe... Maybe Not\",\n" +
        "      \"vote_average\": 6,\n" +
        "      \"vote_count\": 1\n" +
        "    }\n" +
        "  ],\n" +
        "  \"total_pages\": 2271,\n" +
        "  \"total_results\": 45401\n" +
        "}")
public class SimilarMoviesEndpoint {
    @HeaderParam("Content-Type")
    String contentType;

    @UrlParam("api_key")
    String apiKey;

    @UrlParam("page")
    Integer page;

    @UrlPathParam("id")
    String movieId;
}
