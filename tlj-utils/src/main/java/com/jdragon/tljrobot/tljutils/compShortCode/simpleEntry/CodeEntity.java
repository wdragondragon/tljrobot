package com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeEntity {
    private int index;
    private String word;
    private String wordCode;
    private String words;
    private String wordsCode;
    private int next;
    private int type;
    private boolean bold;
    @Override
    public String toString() {
        return "[编号:"+index+",下一跳:"+next+",类型:"+type+",加粗:"+bold+",单字:"+word+",单字编码:"+wordCode+",词语"+words+
                ",词语编码"+wordsCode+"]";
    }
}
