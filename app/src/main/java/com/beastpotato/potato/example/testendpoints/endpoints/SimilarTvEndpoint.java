package com.beastpotato.potato.example.testendpoints.endpoints;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;
import com.beastpotato.potato.api.UrlPathParam;

/**
 * Created by Oleksiy on 3/1/2016.
 */
@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/tv/{id}/similar", jsonExample = "{\n" +
        "  \"page\": 1,\n" +
        "  \"results\": [\n" +
        "    {\n" +
        "      \"backdrop_path\": null,\n" +
        "      \"id\": 15985,\n" +
        "      \"original_name\": \"Tom Brown's Schooldays\",\n" +
        "      \"first_air_date\": \"1971-11-14\",\n" +
        "      \"poster_path\": \"/7IDcPPyw0DYSH7xS583ufVZBhnd.jpg\",\n" +
        "      \"popularity\": 0.3,\n" +
        "      \"name\": \"Tom Brown's Schooldays\",\n" +
        "      \"vote_average\": 0,\n" +
        "      \"vote_count\": 0\n" +
        "    },\n" +
        "    {\n" +
        "      \"backdrop_path\": null,\n" +
        "      \"id\": 15008,\n" +
        "      \"original_name\": \"Something in the Air\",\n" +
        "      \"first_air_date\": \"2000-01-17\",\n" +
        "      \"poster_path\": null,\n" +
        "      \"popularity\": 0.500518,\n" +
        "      \"name\": \"Something in the Air\",\n" +
        "      \"vote_average\": 0,\n" +
        "      \"vote_count\": 0\n" +
        "    }\n" +
        "  ],\n" +
        "  \"total_pages\": 339,\n" +
        "  \"total_results\": 6763\n" +
        "}")
public class SimilarTvEndpoint {
    @HeaderParam("Content-Type")
    String contentType;

    @UrlParam("api_key")
    String apiKey;

    @UrlParam("page")
    Integer page;

    @UrlPathParam("id")
    String tvId;
}
