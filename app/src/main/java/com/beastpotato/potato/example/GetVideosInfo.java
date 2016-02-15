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
                        "   \"code\":\"\",\n" +
                        "   \"data\":{\n" +
                        "      \"count\":100,\n" +
                        "      \"anchorStr\":\"\",\n" +
                        "      \"records\":[\n" +
                        "         {\n" +
                        "            \"liked\":0,\n" +
                        "            \"videoDashUrl\":\"http://v.cdn.vine.co/r/videos_dashhd/584F3E7BEE1308986059115442176_4253661e252.5.1.10941098180513993257.mp4?versionId=7JYt7aQCsf7gfKG2L4mOUDBlNAf_KHA3\",\n" +
                        "            \"foursquareVenueId\":\"\",\n" +
                        "            \"userId\":935043086076256256,\n" +
                        "            \"private\":0,\n" +
                        "            \"videoWebmUrl\":null,\n" +
                        "            \"loops\":{\n" +
                        "               \"count\":1519176.0,\n" +
                        "               \"velocity\":122.38933333333335,\n" +
                        "               \"onFire\":1\n" +
                        "            },\n" +
                        "            \"thumbnailUrl\":\"http://v.cdn.vine.co/r/videos/584F3E7BEE1308986059115442176_4253661e252.5.1.10941098180513993257.mp4.jpg?versionId=q.NYGdoMV4KEKkLEFFStlIy_k15BqWm0\",\n" +
                        "            \"explicitContent\":0,\n" +
                        "            \"blocked\":0,\n" +
                        "            \"verified\":1,\n" +
                        "            \"avatarUrl\":\"http://v.cdn.vine.co/v/avatars/B41B41E5-B242-4ED5-B2FF-6E9D790DD03B-8147-000008DB5621E9F4.jpg?versionId=lyTonffRZqXJstBg7loUwx0p2NxQcj.C\",\n" +
                        "            \"comments\":{\n" +
                        "               \"count\":451,\n" +
                        "               \"anchorStr\":\"\",\n" +
                        "               \"records\":[\n" +
                        "\n" +
                        "               ],\n" +
                        "               \"previousPage\":null,\n" +
                        "               \"backAnchor\":\"\",\n" +
                        "               \"anchor\":null,\n" +
                        "               \"nextPage\":null,\n" +
                        "               \"size\":0\n" +
                        "            },\n" +
                        "            \"entities\":[\n" +
                        "\n" +
                        "            ],\n" +
                        "            \"videoLowURL\":\"http://v.cdn.vine.co/r/videos_r2/584F3E7BEE1308986059115442176_4253661e252.5.1.10941098180513993257.mp4?versionId=LhavtB.f87ke4MmwStm01PvCVkV4eqDv\",\n" +
                        "            \"vanityUrls\":[\n" +
                        "               \"ThomasSanders\"\n" +
                        "            ],\n" +
                        "            \"username\":\"Thomas Sanders\",\n" +
                        "            \"description\":\"Phone Battery Life \\ud83d\\udd0b\",\n" +
                        "            \"tags\":[\n" +
                        "\n" +
                        "            ],\n" +
                        "            \"permalinkUrl\":\"https://vine.co/v/i10b6O56XiF\",\n" +
                        "            \"promoted\":0,\n" +
                        "            \"postId\":1308986452134273024,\n" +
                        "            \"profileBackground\":\"0x68bf60\",\n" +
                        "            \"videoUrl\":\"http://v.cdn.vine.co/r/videos/584F3E7BEE1308986059115442176_4253661e252.5.1.10941098180513993257.mp4?versionId=E1qEPCE95v4pyBql1cO3K8m7veg62fXS\",\n" +
                        "            \"followRequested\":0,\n" +
                        "            \"created\":\"2016-02-09T23:35:03.000000\",\n" +
                        "            \"hasSimilarPosts\":1,\n" +
                        "            \"shareUrl\":\"https://vine.co/v/i10b6O56XiF\",\n" +
                        "            \"myRepostId\":0,\n" +
                        "            \"following\":0,\n" +
                        "            \"reposts\":{\n" +
                        "               \"count\":3786,\n" +
                        "               \"anchorStr\":\"\",\n" +
                        "               \"records\":[\n" +
                        "\n" +
                        "               ],\n" +
                        "               \"previousPage\":null,\n" +
                        "               \"backAnchor\":\"\",\n" +
                        "               \"anchor\":null,\n" +
                        "               \"nextPage\":null,\n" +
                        "               \"size\":0\n" +
                        "            },\n" +
                        "            \"likes\":{\n" +
                        "               \"count\":16874,\n" +
                        "               \"anchorStr\":\"1309030467064168448\",\n" +
                        "               \"records\":[\n" +
                        "                  {\n" +
                        "                     \"username\":\"Marco\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:57.000000\",\n" +
                        "                     \"userId\":1206105759117623296,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030467064168448\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Diego Flores\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:58.000000\",\n" +
                        "                     \"userId\":1099963296540733440,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030468074848256\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Audrey:)\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:59.000000\",\n" +
                        "                     \"userId\":1171626559937863680,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030472088711168\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Redd\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:59.000000\",\n" +
                        "                     \"userId\":1022776434021810176,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030473137360896\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Em_Rae03\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:30:00.000000\",\n" +
                        "                     \"userId\":1183889903822770176,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030477889691648\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Ashley\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:30:01.000000\",\n" +
                        "                     \"userId\":1294862732742578176,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030480104001536\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Ayashah Anwar\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:30:01.000000\",\n" +
                        "                     \"userId\":973554028544172032,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030482532601856\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Ddanieladd\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:30:02.000000\",\n" +
                        "                     \"userId\":1298464576240861184,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030484571250688\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Beighly Brintnall\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:30:02.000000\",\n" +
                        "                     \"userId\":923102216368300032,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030487091802112\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Jade\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:30:04.000000\",\n" +
                        "                     \"userId\":1194692459435790336,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030492934377472\n" +
                        "                  }\n" +
                        "               ],\n" +
                        "               \"previousPage\":null,\n" +
                        "               \"backAnchor\":\"\",\n" +
                        "               \"anchor\":1309030467064168448,\n" +
                        "               \"nextPage\":2,\n" +
                        "               \"size\":10\n" +
                        "            }\n" +
                        "         },\n" +
                        "         {\n" +
                        "            \"liked\":0,\n" +
                        "            \"videoDashUrl\":\"http://v.cdn.vine.co/r/videos_dashhd/731C91109E1308969041326956544_49a5b7cb702.5.1.14241148794362388091.mp4?versionId=oVqnLVyAbXdQTjQhuILn9pTYX6SW6Ble\",\n" +
                        "            \"foursquareVenueId\":\"\",\n" +
                        "            \"userId\":991200655933128704,\n" +
                        "            \"private\":0,\n" +
                        "            \"videoWebmUrl\":null,\n" +
                        "            \"loops\":{\n" +
                        "               \"count\":350831.0,\n" +
                        "               \"velocity\":15.496000000000002,\n" +
                        "               \"onFire\":0\n" +
                        "            },\n" +
                        "            \"thumbnailUrl\":\"http://v.cdn.vine.co/r/videos/731C91109E1308969041326956544_49a5b7cb702.5.1.14241148794362388091.mp4.jpg?versionId=vWgY5DA_VXHBUIRwX_U0xA_aCFZbBHJH\",\n" +
                        "            \"explicitContent\":0,\n" +
                        "            \"blocked\":0,\n" +
                        "            \"verified\":1,\n" +
                        "            \"avatarUrl\":\"http://v.cdn.vine.co/r/avatars/B4521B26DD1283309181734137856_4b108633ca6.4.0.jpg?versionId=amOGJjaoDuHvxwTt4NA.QfTo2ICwKhi0\",\n" +
                        "            \"comments\":{\n" +
                        "               \"count\":82,\n" +
                        "               \"anchorStr\":\"\",\n" +
                        "               \"records\":[\n" +
                        "\n" +
                        "               ],\n" +
                        "               \"previousPage\":null,\n" +
                        "               \"backAnchor\":\"\",\n" +
                        "               \"anchor\":null,\n" +
                        "               \"nextPage\":null,\n" +
                        "               \"size\":0\n" +
                        "            },\n" +
                        "            \"entities\":[\n" +
                        "               {\n" +
                        "                  \"vanityUrls\":[\n" +
                        "\n" +
                        "                  ],\n" +
                        "                  \"title\":\"Nash Cash\",\n" +
                        "                  \"range\":[\n" +
                        "                     36,\n" +
                        "                     45\n" +
                        "                  ],\n" +
                        "                  \"link\":\"vine://user-id/943631722321436672\",\n" +
                        "                  \"type\":\"mention\",\n" +
                        "                  \"id\":943631722321436672\n" +
                        "               },\n" +
                        "               {\n" +
                        "                  \"link\":\"vine://tag/ayetwinz\",\n" +
                        "                  \"range\":[\n" +
                        "                     46,\n" +
                        "                     55\n" +
                        "                  ],\n" +
                        "                  \"type\":\"tag\",\n" +
                        "                  \"id\":1012258057834737664,\n" +
                        "                  \"title\":\"ayetwinz\"\n" +
                        "               }\n" +
                        "            ],\n" +
                        "            \"videoLowURL\":\"http://v.cdn.vine.co/r/videos_r2/731C91109E1308969041326956544_49a5b7cb702.5.1.14241148794362388091.mp4?versionId=G8HoxZJkUswUhcB5ZyQAQBy6ofANs.nK\",\n" +
                        "            \"vanityUrls\":[\n" +
                        "               \"Aye.Twinz\"\n" +
                        "            ],\n" +
                        "            \"username\":\"Aye Twinz\",\n" +
                        "            \"description\":\"When someone acting like a b**** \\ud83d\\ude02w/Nash Cash #ayetwinz\",\n" +
                        "            \"tags\":[\n" +
                        "\n" +
                        "            ],\n" +
                        "            \"permalinkUrl\":\"https://vine.co/v/i12tQjTBqj1\",\n" +
                        "            \"promoted\":0,\n" +
                        "            \"postId\":1308969044686548992,\n" +
                        "            \"profileBackground\":\"0x333333\",\n" +
                        "            \"videoUrl\":\"http://v.cdn.vine.co/r/videos/731C91109E1308969041326956544_49a5b7cb702.5.1.14241148794362388091.mp4?versionId=t4wtyqui_S4FaFfcfR0RHj5SUCJl0MsV\",\n" +
                        "            \"followRequested\":0,\n" +
                        "            \"created\":\"2016-02-09T22:25:53.000000\",\n" +
                        "            \"hasSimilarPosts\":1,\n" +
                        "            \"shareUrl\":\"https://vine.co/v/i12tQjTBqj1\",\n" +
                        "            \"myRepostId\":0,\n" +
                        "            \"following\":0,\n" +
                        "            \"reposts\":{\n" +
                        "               \"count\":1246,\n" +
                        "               \"anchorStr\":\"\",\n" +
                        "               \"records\":[\n" +
                        "\n" +
                        "               ],\n" +
                        "               \"previousPage\":null,\n" +
                        "               \"backAnchor\":\"\",\n" +
                        "               \"anchor\":null,\n" +
                        "               \"nextPage\":null,\n" +
                        "               \"size\":0\n" +
                        "            },\n" +
                        "            \"likes\":{\n" +
                        "               \"count\":7004,\n" +
                        "               \"anchorStr\":\"1309030364458754048\",\n" +
                        "               \"records\":[\n" +
                        "                  {\n" +
                        "                     \"username\":\"Jack Lehman\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:33.000000\",\n" +
                        "                     \"userId\":1084325523330871296,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030364458754048\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"ehh jenn\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:38.000000\",\n" +
                        "                     \"userId\":930277593964482560,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030383874084864\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"luhGrizzo525\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:41.000000\",\n" +
                        "                     \"userId\":1115498235201531904,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030397719797760\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"(br)avery\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:47.000000\",\n" +
                        "                     \"userId\":971296519414345728,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030421576765440\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Maha Muhammad\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:52.000000\",\n" +
                        "                     \"userId\":1090800836285722624,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030443320020992\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"WarDrake\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:55.000000\",\n" +
                        "                     \"userId\":1217667713359556608,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030456515264512\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"kenn\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:58.000000\",\n" +
                        "                     \"userId\":1301398620460978176,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030471321133056\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Jeremy Galagher\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:29:59.000000\",\n" +
                        "                     \"userId\":964309875528204288,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030475243094016\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"Valley Gurl\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:30:02.000000\",\n" +
                        "                     \"userId\":1227503604270100480,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030488098611200\n" +
                        "                  },\n" +
                        "                  {\n" +
                        "                     \"username\":\"'Nya\",\n" +
                        "                     \"verified\":0,\n" +
                        "                     \"vanityUrls\":[\n" +
                        "\n" +
                        "                     ],\n" +
                        "                     \"created\":\"2016-02-10T02:30:02.000000\",\n" +
                        "                     \"userId\":1213936940396027904,\n" +
                        "                     \"user\":{\n" +
                        "                        \"private\":0\n" +
                        "                     },\n" +
                        "                     \"likeId\":1309030488199032832\n" +
                        "                  }\n" +
                        "               ],\n" +
                        "               \"previousPage\":null,\n" +
                        "               \"backAnchor\":\"\",\n" +
                        "               \"anchor\":1309030364458754048,\n" +
                        "               \"nextPage\":2,\n" +
                        "               \"size\":10\n" +
                        "            }\n" +
                        "         }\n" +
                        "      ],\n" +
                        "      \"previousPage\":null,\n" +
                        "      \"backAnchor\":\"\",\n" +
                        "      \"anchor\":null,\n" +
                        "      \"nextPage\":2,\n" +
                        "      \"size\":20\n" +
                        "   },\n" +
                        "   \"success\":true,\n" +
                        "   \"error\":\"\"\n" +
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
