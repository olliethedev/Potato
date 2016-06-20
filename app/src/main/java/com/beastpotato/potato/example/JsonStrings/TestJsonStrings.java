package com.beastpotato.potato.example.JsonStrings;

import com.beastpotato.potato.api.JsonToModel;

/**
 * Created by Oleksiy on 6/19/2016.
 */


public class TestJsonStrings {
    @JsonToModel(jsonString = "" +
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
}
