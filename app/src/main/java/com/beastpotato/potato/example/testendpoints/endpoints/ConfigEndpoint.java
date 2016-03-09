package com.beastpotato.potato.example.testendpoints.endpoints;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;

/**
 * Created by Oleksiy on 2/21/2016.
 */
@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/configuration", jsonExample = "{\n" +
        "  \"images\": {\n" +
        "    \"base_url\": \"http://image.tmdb.org/t/p/\",\n" +
        "    \"secure_base_url\": \"https://image.tmdb.org/t/p/\",\n" +
        "    \"backdrop_sizes\": [\n" +
        "      \"w300\",\n" +
        "      \"w780\",\n" +
        "      \"w1280\",\n" +
        "      \"original\"\n" +
        "    ],\n" +
        "    \"logo_sizes\": [\n" +
        "      \"w45\",\n" +
        "      \"w92\",\n" +
        "      \"w154\",\n" +
        "      \"w185\",\n" +
        "      \"w300\",\n" +
        "      \"w500\",\n" +
        "      \"original\"\n" +
        "    ],\n" +
        "    \"poster_sizes\": [\n" +
        "      \"w92\",\n" +
        "      \"w154\",\n" +
        "      \"w185\",\n" +
        "      \"w342\",\n" +
        "      \"w500\",\n" +
        "      \"w780\",\n" +
        "      \"original\"\n" +
        "    ],\n" +
        "    \"profile_sizes\": [\n" +
        "      \"w45\",\n" +
        "      \"w185\",\n" +
        "      \"h632\",\n" +
        "      \"original\"\n" +
        "    ],\n" +
        "    \"still_sizes\": [\n" +
        "      \"w92\",\n" +
        "      \"w185\",\n" +
        "      \"w300\",\n" +
        "      \"original\"\n" +
        "    ]\n" +
        "  },\n" +
        "  \"change_keys\": [\n" +
        "    \"adult\",\n" +
        "    \"also_known_as\",\n" +
        "    \"alternative_titles\",\n" +
        "    \"biography\",\n" +
        "    \"birthday\",\n" +
        "    \"budget\",\n" +
        "    \"cast\",\n" +
        "    \"character_names\",\n" +
        "    \"crew\",\n" +
        "    \"deathday\",\n" +
        "    \"general\",\n" +
        "    \"genres\",\n" +
        "    \"homepage\",\n" +
        "    \"images\",\n" +
        "    \"imdb_id\",\n" +
        "    \"name\",\n" +
        "    \"original_title\",\n" +
        "    \"overview\",\n" +
        "    \"plot_keywords\",\n" +
        "    \"production_companies\",\n" +
        "    \"production_countries\",\n" +
        "    \"releases\",\n" +
        "    \"revenue\",\n" +
        "    \"runtime\",\n" +
        "    \"spoken_languages\",\n" +
        "    \"status\",\n" +
        "    \"tagline\",\n" +
        "    \"title\",\n" +
        "    \"trailers\",\n" +
        "    \"translations\"\n" +
        "  ]\n" +
        "}")
public class ConfigEndpoint {
    @HeaderParam("Content-Type")
    String contentType;

    @UrlParam("api_key")
    String apiKey;
}
