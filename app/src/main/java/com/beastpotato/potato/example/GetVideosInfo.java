package com.beastpotato.potato.example;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;
import com.beastpotato.potato.api.UrlPathParam;

/**
 * Created by Oleksiy on 2/6/2016.
 */
@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/timelines/{category}", jsonExample = "todo8")
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
