package com.ythtwang.rtc.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName(value = "number")
public class NumberPageModel extends Page {

    private Integer number;
}