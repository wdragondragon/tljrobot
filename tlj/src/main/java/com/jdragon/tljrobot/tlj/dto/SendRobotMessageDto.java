package com.jdragon.tljrobot.tlj.dto;

import lombok.Data;

/**
 * @author 10619
 */
@Data
public class SendRobotMessageDto {
    int paragraph;
    String title;
    String content;
    Long groupId;
    String token;
}
