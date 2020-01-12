package com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry;

import lombok.Data;

@Data
public class ShortCodeEntity {
    private CodeEntity[] codeEntities;
    private int codeLength;
    private String article;
    private String articleCodes;
}
