package com.ythtwang.rtc.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "dsl", visible = true)
//@JsonSubTypes({@JsonSubTypes.Type(value = InputPageModel.class, name = "input")
//        , @JsonSubTypes.Type(value = NumberPageModel.class, name = "number")})
public abstract class Page {

    private String dsl;
    private String name;
    private String uiType;
    private String label;
}