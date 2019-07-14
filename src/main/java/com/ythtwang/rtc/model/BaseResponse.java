package com.ythtwang.rtc.model;

public class BaseResponse {
    String code;
    String description;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
