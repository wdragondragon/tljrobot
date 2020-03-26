package com.jdragon.tljrobot.client.test.enumTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 08:04
 * @Description:
 */
public enum CodeInfoEnum {
    LOCK(1L,1L,"LOCK_TYPE","LOCK"),UNLOCK(1L,2L,"LOCK_TYPE","LOCK");
    public Long classId;
    public Long infoId;
    public String classCode;
    public String infoCode;
    CodeInfoEnum(Long classId,Long infoId,String classCode,String infoCode){
        this.classId = classId;
        this.infoId = infoId;
        this.classCode = classCode;
        this.infoCode = infoCode;
    }
    public static CodeInfoEnum getByInfoId(Long infoId){
        return CodeInfoEnum.valueOf(infoId+"");
    }
    public static List<CodeInfoEnum> getByClassId(Long classId){
        return Arrays.stream(CodeInfoEnum.values()).filter(item->item.classId.equals(classId)).collect(Collectors.toList());
    }
    public static CodeInfoEnum getByClassCodeAndInfoCode(String classCode,String infoCode){
        Optional<CodeInfoEnum> opt = Arrays.stream(CodeInfoEnum.values()).filter(item->item.classCode.equals(classCode)&&item.infoCode.equals(infoCode)).findFirst();
        return opt.orElse(null);
    }

    @Override
    public String toString() {
        return "CodeInfoEnum{" +
                "classId=" + classId +
                ", infoId=" + infoId +
                ", classCode='" + classCode + '\'' +
                ", infoCode='" + infoCode + '\'' +
                '}';
    }
}