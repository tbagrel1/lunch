package com.tbagrel1.lunch.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.MediaType;

public abstract class BaseController {
    public static final String MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected String ok(Object obj) {
        ObjectNode response = OBJECT_MAPPER.createObjectNode();
        response.put("success", true);
        try {
            response.put("data", OBJECT_MAPPER.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // TODO: improve error handling
        }
        return response.toString();
    }

    protected String err(Object message) {
        ObjectNode response = OBJECT_MAPPER.createObjectNode();
        response.put("success", false);
        response.put("message", response.toString());
        return response.toString();
    }
}
