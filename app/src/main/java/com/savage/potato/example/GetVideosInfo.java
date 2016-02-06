package com.savage.potato.example;

import com.savage.potato.api.Body;
import com.savage.potato.api.Constants;
import com.savage.potato.api.Endpoint;
import com.savage.potato.api.HeaderParam;
import com.savage.potato.api.UrlParam;
import com.savage.potato.api.UrlPathParam;

/**
 * Created by Oleksiy on 2/6/2016.
 */
@Endpoint(httpMethod = Constants.Http.GET, relativeUrl = "/timelines/{category}", jsonExample = "todo")
public abstract class GetVideosInfo {
    @UrlPathParam("category")
    String category;
    @UrlParam("sort")
    String sortType;
    @HeaderParam("X-Mashape-Key")
    String apiKey;
    @HeaderParam("Accept")
    String contentType;
    @Body
    String body;
}
