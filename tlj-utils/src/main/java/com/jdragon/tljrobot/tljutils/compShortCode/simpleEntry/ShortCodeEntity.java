package com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry;

import lombok.Data;

@Data
public class ShortCodeEntity {
    private CodeEntity[] codeEntities;
    private int codeLength;
    private String article;
    private String articleCodes;
    private double articleAverCodes;
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder()
                .append("[\n编码长度:"+codeLength+",\n文章编码:"+articleCodes+",\n编码详细:\n");
        for(CodeEntity codeEntity : codeEntities)
            str.append(codeEntity.toString()).append("\n");
        str.append("]");
        return str.toString();
    }
}
