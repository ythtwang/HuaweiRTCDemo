package com.ythtwang.rtc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    @JsonProperty("code")
    String code;
    @JsonProperty("description")
    String description;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
