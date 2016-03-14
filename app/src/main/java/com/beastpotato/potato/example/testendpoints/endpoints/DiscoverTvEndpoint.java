package com.beastpotato.potato.example.testendpoints.endpoints;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;
import com.beastpotato.potato.api.UrlPathParam;

/**
 * Created by Admin on 2/26/2016.
 */

@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/{firstPathPart}/{secondPathPart}", jsonExample = "{\n" +
        "  \"page\": 1,\n" +
        "  \"results\": [\n" +
        "    {\n" +
        "      \"backdrop_path\": \"/aKz3lXU71wqdslC1IYRC3yHD6yw.jpg\",\n" +
        "      \"first_air_date\": \"2011-04-17\",\n" +
        "      \"genre_ids\": [\n" +
        "        10765,\n" +
        "        18\n" +
        "      ],\n" +
        "      \"id\": 1399,\n" +
        "      \"original_language\": \"en\",\n" +
        "      \"original_name\": \"Game of Thrones\",\n" +
        "      \"overview\": \"Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.\\n\\n\",\n" +
        "      \"origin_country\": [\n" +
        "        \"US\"\n" +
        "      ],\n" +
        "      \"poster_path\": \"/jIhL6mlT7AblhbHJgEoiBIOUVl1.jpg\",\n" +
        "      \"popularity\": 36.072708,\n" +
        "      \"name\": \"Game of Thrones\",\n" +
        "      \"vote_average\": 9.1,\n" +
        "      \"vote_count\": 273\n" +
        "    }" +
        "  ],\n" +
        "  \"total_pages\": 3089,\n" +
        "  \"total_results\": 61761\n" +
        "}")
public class DiscoverTvEndpoint {
    @UrlParam("api_key")
    String apiKey;

    @UrlParam("page")
    Integer page;

    @HeaderParam("Content-Type")
    String contentType;

    @UrlPathParam("firstPathPart")
    String firstPathPart;

    @UrlPathParam("secondPathPart")
    String secondPathPart;
}
