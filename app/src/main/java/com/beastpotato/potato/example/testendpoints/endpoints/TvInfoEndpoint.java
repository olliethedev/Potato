package com.beastpotato.potato.example.testendpoints.endpoints;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;
import com.beastpotato.potato.api.UrlPathParam;

/**
 * Created by Oleksiy on 3/1/2016.
 */
@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/tv/{id}", jsonExample = "{\n" +
        "  \"backdrop_path\": \"/bzoZjhbpriBT2N5kwgK0weUfVOX.jpg\",\n" +
        "  \"created_by\": [\n" +
        "    {\n" +
        "      \"id\": 66633,\n" +
        "      \"name\": \"Vince Gilligan\",\n" +
        "      \"profile_path\": \"/rLSUjr725ez1cK7SKVxC9udO03Y.jpg\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"episode_run_time\": [\n" +
        "    45,\n" +
        "    47\n" +
        "  ],\n" +
        "  \"first_air_date\": \"2008-01-19\",\n" +
        "  \"genres\": [\n" +
        "    {\n" +
        "      \"id\": 18,\n" +
        "      \"name\": \"Drama\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"homepage\": \"http://www.amctv.com/shows/breaking-bad\",\n" +
        "  \"id\": 1396,\n" +
        "  \"in_production\": false,\n" +
        "  \"languages\": [\n" +
        "    \"en\",\n" +
        "    \"de\",\n" +
        "    \"ro\",\n" +
        "    \"es\",\n" +
        "    \"fa\"\n" +
        "  ],\n" +
        "  \"last_air_date\": \"2013-09-29\",\n" +
        "  \"name\": \"Breaking Bad\",\n" +
        "  \"networks\": [\n" +
        "    {\n" +
        "      \"id\": 174,\n" +
        "      \"name\": \"AMC\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"number_of_episodes\": 62,\n" +
        "  \"number_of_seasons\": 5,\n" +
        "  \"origin_country\": [\n" +
        "    \"US\"\n" +
        "  ],\n" +
        "  \"original_language\": \"en\",\n" +
        "  \"original_name\": \"Breaking Bad\",\n" +
        "  \"overview\": \"Breaking Bad is an American crime drama television series created and produced by Vince Gilligan. Set and produced in Albuquerque, New Mexico, Breaking Bad is the story of Walter White, a struggling high school chemistry teacher who is diagnosed with inoperable lung cancer at the beginning of the series. He turns to a life of crime, producing and selling methamphetamine, in order to secure his family's financial future before he dies, teaming with his former student, Jesse Pinkman. Heavily serialized, the series is known for positioning its characters in seemingly inextricable corners and has been labeled a contemporary western by its creator.\",\n" +
        "  \"popularity\": 7.39820387093879,\n" +
        "  \"poster_path\": \"/4yMXf3DW6oCL0lVPZaZM2GypgwE.jpg\",\n" +
        "  \"production_companies\": [\n" +
        "    {\n" +
        "      \"name\": \"Gran Via Productions\",\n" +
        "      \"id\": 2605\n" +
        "    },\n" +
        "    {\n" +
        "      \"name\": \"Sony Pictures Television\",\n" +
        "      \"id\": 11073\n" +
        "    },\n" +
        "    {\n" +
        "      \"name\": \"High Bridge Entertainment\",\n" +
        "      \"id\": 33742\n" +
        "    }\n" +
        "  ],\n" +
        "  \"seasons\": [\n" +
        "    {\n" +
        "      \"air_date\": \"2009-02-17\",\n" +
        "      \"episode_count\": 6,\n" +
        "      \"id\": 3577,\n" +
        "      \"poster_path\": \"/spPmYZAq2xLKQOEIdBPkhiRxrb9.jpg\",\n" +
        "      \"season_number\": 0\n" +
        "    },\n" +
        "    {\n" +
        "      \"air_date\": \"2008-01-19\",\n" +
        "      \"episode_count\": 7,\n" +
        "      \"id\": 3572,\n" +
        "      \"poster_path\": \"/dHCYpEoHEjAV6Xt3eyNthkdLRl3.jpg\",\n" +
        "      \"season_number\": 1\n" +
        "    },\n" +
        "    {\n" +
        "      \"air_date\": \"2009-03-08\",\n" +
        "      \"episode_count\": 13,\n" +
        "      \"id\": 3573,\n" +
        "      \"poster_path\": \"/ww6cDy0dhrVEdMqielNEsYz96mg.jpg\",\n" +
        "      \"season_number\": 2\n" +
        "    },\n" +
        "    {\n" +
        "      \"air_date\": \"2010-03-21\",\n" +
        "      \"episode_count\": 13,\n" +
        "      \"id\": 3575,\n" +
        "      \"poster_path\": \"/rINvcsYHUprsx9L8zNr5JltALda.jpg\",\n" +
        "      \"season_number\": 3\n" +
        "    },\n" +
        "    {\n" +
        "      \"air_date\": \"2011-07-17\",\n" +
        "      \"episode_count\": 13,\n" +
        "      \"id\": 3576,\n" +
        "      \"poster_path\": \"/ngnE7FFQqrrLgK3yVsv3kjwtQMZ.jpg\",\n" +
        "      \"season_number\": 4\n" +
        "    },\n" +
        "    {\n" +
        "      \"air_date\": \"2012-07-15\",\n" +
        "      \"episode_count\": 16,\n" +
        "      \"id\": 3578,\n" +
        "      \"poster_path\": \"/ih1JKNxEzW56azeFpEQmdu4poA4.jpg\",\n" +
        "      \"season_number\": 5\n" +
        "    }\n" +
        "  ],\n" +
        "  \"status\": \"Ended\",\n" +
        "  \"type\": \"Scripted\",\n" +
        "  \"vote_average\": 9.2,\n" +
        "  \"vote_count\": 152\n" +
        "}")
public class TvInfoEndpoint {
    @HeaderParam("Content-Type")
    String contentType;

    @UrlParam("api_key")
    String apiKey;

    @UrlPathParam("id")
    String tvId;
}
