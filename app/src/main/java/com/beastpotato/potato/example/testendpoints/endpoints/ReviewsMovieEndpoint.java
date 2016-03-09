package com.beastpotato.potato.example.testendpoints.endpoints;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;
import com.beastpotato.potato.api.UrlPathParam;

/**
 * Created by Oleksiy on 2/21/2016.
 */
@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/movie/{id}/reviews", jsonExample = "{\n" +
        "  \"id\": 49026,\n" +
        "  \"page\": 1,\n" +
        "  \"results\": [\n" +
        "    {\n" +
        "      \"id\": \"5010553819c2952d1b000451\",\n" +
        "      \"author\": \"Travis Bell\",\n" +
        "      \"content\": \"I felt like this was a tremendous end to Nolan's Batman trilogy. The Dark Knight Rises may very well have been the weakest of all 3 films but when you're talking about a scale of this magnitude, it still makes this one of the best movies I've seen in the past few years.\\r\\n\\r\\nI expected a little more _Batman_ than we got (especially with a runtime of 2:45) but while the story around the fall of Bruce Wayne and Gotham City was good I didn't find it amazing. This might be in fact, one of my only criticisms—it was a long movie but still, maybe too short for the story I felt was really being told. I feel confident in saying this big of a story could have been split into two movies.\\r\\n\\r\\nThe acting, editing, pacing, soundtrack and overall theme were the same 'as-close-to-perfect' as ever with any of Christopher Nolan's other films. Man does this guy know how to make a movie!\\r\\n\\r\\nYou don't have to be a Batman fan to enjoy these movies and I hope any of you who feel this way re-consider. These 3 movies are without a doubt in my mind, the finest display of comic mythology ever told on the big screen. They are damn near perfect.\",\n" +
        "      \"url\": \"http://j.mp/QSjAK2\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"5013bc76760ee372cb00253e\",\n" +
        "      \"author\": \"Chris\",\n" +
        "      \"content\": \"I personally thought this film is on par if not better than the Dark Knight.\\r\\n\\r\\nWhilst some think the film is too long for the story I didn't find this. The length of the film is longer than some (but doesn't feel it), I liked that the film took it's time rather than shoving more elements in it - I think this contributed to the dramatic ending (much like a classical piece of music will be relaxed and calm before the final crescendo).\\r\\n\\r\\nAt the end of the day whether you like this film will boil down to if you like films Christopher Nolan has directed and/or you like the Christopher Nolan Batman series so far.\\r\\n\\r\\nStupendously good film in my opinion.\",\n" +
        "      \"url\": \"http://j.mp/P18dg1\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"51429a7519c29552e10eba16\",\n" +
        "      \"author\": \"GeekMasher\",\n" +
        "      \"content\": \"The Dark Knight Rises is one of the best movies to come out in 2012. The story compels you to watch it time and time again. It also has I of, I my opinion, the best bad guys in any movie, Bane! Batman was well played as all ways and the cast where well selected. I think this movie is the best batman to see the light of day or the darkest nights (pun intended).\",\n" +
        "      \"url\": \"http://j.mp/19nyIWg\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"total_pages\": 1,\n" +
        "  \"total_results\": 3\n" +
        "}")
public class ReviewsMovieEndpoint {
    @HeaderParam("Content-Type")
    String contentType;

    @UrlParam("api_key")
    String apiKey;

    @UrlParam("page")
    Integer page;

    @UrlPathParam("id")
    String movieId;
}
