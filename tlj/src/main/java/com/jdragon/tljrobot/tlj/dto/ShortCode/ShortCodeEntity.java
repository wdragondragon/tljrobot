package com.jdragon.tljrobot.tlj.dto.ShortCode;

import lombok.Data;

@Data
public class ShortCodeEntity {
    private CodeEntity[] codeEntities;
    private int codeLenth;
    private String article;
    private String articleCodes;
}
