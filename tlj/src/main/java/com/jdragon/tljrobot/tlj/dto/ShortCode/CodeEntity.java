package com.jdragon.tljrobot.tlj.dto.ShortCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeEntity {
    private String word;
    private String wordCode;
    private String words;
    private String wordsCode;
    private int type;
}
