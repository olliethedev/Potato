package com.beastpotato.potato.example;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;
import com.beastpotato.potato.api.UrlPathParam;

/**
 * Created by Oleksiy on 2/6/2016.
 */
@Endpoint(httpMethod = Constants.Http.GET,
        relativeUrl = "/timelines/{category}",
        jsonExample =
                "{\n" +
                        "  \"count\": 1,\n" +
                        "  \"records\": [\n" +
                        "    {\n" +
                        "      \"avatarUrl\": \"http://vines.s3.amazonaws.com/avatars/FC220C82-1370-4340-887C-01254FBAFB7D-4274-000002E4D4C0B0D4.jpg\",\n" +
                        "      \"comments\": {\n" +
                        "        \"count\": 1,\n" +
                        "        \"nextPage\": null,\n" +
                        "        \"previousPage\": null,\n" +
                        "        \"records\": [\n" +
                        "          {\n" +
                        "            \"avatarUrl\": \"https://vines.s3.amazonaws.com/avatars/D6360E08-0BF5-4750-A883-9F9F09F91E06-25016-0000191A7355BDF7.jpg\",\n" +
                        "            \"comment\": \"When pizza's on a bagel you can eat pizza anytime!\",\n" +
                        "            \"commentId\": 898291497017937900,\n" +
                        "            \"created\": \"2013-01-02T16:19:56.000000\",\n" +
                        "            \"location\": \"Brooklyn, NY\",\n" +
                        "            \"userId\": 110,\n" +
                        "            \"username\": \"Kristian Bauer\"\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"size\": 10\n" +
                        "      },\n" +
                        "      \"created\": \"2013-01-02T16:12:23.000000\",\n" +
                        "      \"description\": \"Pizza??? For breakfast?!?!\",\n" +
                        "      \"foursquareVenueId\": null,\n" +
                        "      \"latitude\": 40.70322799682617,\n" +
                        "      \"liked\": false,\n" +
                        "      \"likes\": {\n" +
                        "        \"count\": 1,\n" +
                        "        \"nextPage\": null,\n" +
                        "        \"previousPage\": null,\n" +
                        "        \"records\": [\n" +
                        "          {\n" +
                        "            \"avatarUrl\": \"https://vines.s3.amazonaws.com/avatars/1194B685-5366-40A9-B36A-A3403AA35716-2999-000000D804A9A9AA.jpg\",\n" +
                        "            \"created\": \"2013-01-02T16:26:28.000000\",\n" +
                        "            \"likeId\": 898293141113806800,\n" +
                        "            \"location\": \"NYC\",\n" +
                        "            \"userId\": 5,\n" +
                        "            \"username\": \"Dom Hofmann\"\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"size\": 10\n" +
                        "      },\n" +
                        "      \"location\": \"NY USA\",\n" +
                        "      \"longitude\": -73.94598388671875,\n" +
                        "      \"postId\": 898289598352990200,\n" +
                        "      \"postToFacebook\": 0,\n" +
                        "      \"postToTwitter\": 0,\n" +
                        "      \"promoted\": 0,\n" +
                        "      \"tags\": null,\n" +
                        "      \"thumbnailUrl\": \"http://vines.s3.amazonaws.com/thumbs/04DAF1DE-189D-4A14-829B-84932099763E-3016-000001F8531FB4AA_0.9.1.mp4.jpg\",\n" +
                        "      \"userId\": 108,\n" +
                        "      \"username\": \"Bobby McKenna\",\n" +
                        "      \"videoLowURL\": null,\n" +
                        "      \"videoUrl\": \"http://vines.s3.amazonaws.com/videos/04DAF1DE-189D-4A14-829B-84932099763E-3016-000001F8531FB4AA_0.9.1.mp4\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"size\": 1\n" +
                        "}")
public abstract class GetVideosInfo {
    @UrlPathParam("category")
    String category;
    @UrlParam("sort")
    String sortType;
    @HeaderParam("X-Mashape-Key")
    String apiKey;
    @HeaderParam("Accept")
    String contentType;
}
