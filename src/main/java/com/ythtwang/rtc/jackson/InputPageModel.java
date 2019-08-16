package com.ythtwang.rtc.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName(value = "input")
public class InputPageModel extends Page {

    private String input;
}
