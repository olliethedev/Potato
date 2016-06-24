package com.beastpotato.potato.example.JsonStrings;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.JsonToModel;

/**
 * Created by Oleksiy on 6/19/2016.
 */


public class TestJsonStrings {
    @JsonToModel(modelType = Constants.ModelType.GSON_AND_ORM_LITE_COMPAT, jsonString = "" +
            "{\"employees\":[\n" +
            "    {\"firstName\":\"John\", \"lastName\":\"Doe\"},\n" +
            "    {\"firstName\":\"Anna\", \"lastName\":\"Smith\"},\n" +
            "    {\"firstName\":\"Peter\", \"lastName\":\"Jones\"}\n" +
            "]}")
    public String EmployeesModel;

    @JsonToModel(jsonString = "{\n" +
            "  \"firstName\": \"John\",\n" +
            "  \"lastName\": \"Smith\",\n" +
            "  \"isAlive\": true,\n" +
            "  \"age\": 25,\n" +
            "  \"address\": {\n" +
            "    \"streetAddress\": \"21 2nd Street\",\n" +
            "    \"city\": \"New York\",\n" +
            "    \"state\": \"NY\",\n" +
            "    \"postalCode\": \"10021-3100\"\n" +
            "  },\n" +
            "  \"phoneNumbers\": [\n" +
            "    {\n" +
            "      \"type\": \"home\",\n" +
            "      \"number\": \"212 555-1234\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"office\",\n" +
            "      \"number\": \"646 555-4567\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"mobile\",\n" +
            "      \"number\": \"123 456-7890\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"children\": [],\n" +
            "  \"spouse\": null\n" +
            "}")
    public String PersonModel;

    @JsonToModel(modelType = Constants.ModelType.GSON_AND_ORM_LITE_COMPAT, jsonString = "{\"Consumer\":{\"Account_Status\":{\"AccountType\":null,\"Status\":0},\"Account_Status_Date\":\"\\/Date(1466704241000-0400)\\/\",\"Buyer\":\"\",\"Buyer_Com\":\"\",\"CREA_mail_updates\":false,\"CRMUID\":\"GHhd+4zPj\\/zkU0kIVRnwDVEYSpSVIPP6ROYAujjW14STn7q+Q5XktLc+hoSqxANnic+FajwkEkm6xo6RqWAyOw==\",\"Interested\":\"2\",\"Interested_Com\":\"2\",\"LoginProviderType\":{\"Type\":2},\"More_About_You\":\"\",\"REALTOR_mail_updates\":false,\"TermsOfUse\":{\"AgreeementVersion\":0,\"Agreement_Date\":\"\",\"Latest_Policy_Date\":\"2015-11-08=\"},\"UID\":\"\\/R5EfMnrlpS2nO5PEDBSZVhSdagGziC2L2Cr\\/1alOgbd7UDErMRPO8yycTOoeeigg+EsV8W00jQ=\",\"UIDSignature\":null,\"UserToken\":null,\"created\":\"\\/Date(1466704240000-0400)\\/\",\"data\":null,\"email\":null,\"errorMsg\":null,\"identities\":null,\"isRegistered\":false,\"isVerified\":false,\"lastLogin\":\"\\/Date(1466704714000-0400)\\/\",\"lastUpdated\":\"\\/Date(1466792860939-0400)\\/\",\"lastUpdatedTimestamp\":\"1466792860939\",\"loginProvider\":null,\"moreAboutYouCondition\":true,\"profile\":null,\"result\":null,\"revisedTermsCondition\":false,\"signatureTimestamp\":null,\"socialProviders\":\"linkedin,site\",\"socialTermsCondition\":true,\"unsubscribeLink\":\"\\/UnsubscribeEmails.aspx?p1=%2fR5EfMnrlpS2nO5PEDBSZVhSdagGziC2L2Cr%2f1alOgbd7UDErMRPO8yycTOoeeigg%2bEsV8W00jQ%3d&p2=77xCkepR%2bU0arAA81B5YWA5eKeE%3d\",\"user\":{\"Calculator\":null,\"Com_Search\":null,\"Com_View\":null,\"Comm_Lang\":\"\",\"Compares\":null,\"Display_Name\":\"Oleksiy\",\"Favourites\":null,\"Notes\":null,\"Notes_include\":false,\"OptInOut\":null,\"Realtors\":null,\"Res_Search\":null,\"Res_View\":null,\"Searches\":null,\"Unsub_Reasons\":\"\",\"address\":null,\"birthDay\":null,\"birthMonth\":null,\"birthYear\":null,\"city\":null,\"country\":\"Canada\",\"email\":\"3martynov@gmail.com\",\"firstName\":\"Oleksiy\",\"gender\":null,\"hometown\":null,\"identities\":null,\"lastName\":\"Martynov\",\"locale\":\"\",\"phones\":null,\"photoURL\":null,\"relationshipStatus\":null,\"state\":\"Toronto, Canada Area\",\"thumbnailURL\":null,\"zip\":null}},\"ErrorCode\":{\"Description\":\"Success\",\"Id\":200,\"LogId\":\"\"},\"Response\":null}")
    public String AccountInfoResponse;
}
