package com.jdragon.tljrobot.client.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 10619
 */
@Data
@AllArgsConstructor
public class SendRobotMessageDto {
    int paragraph;
    String title;
    String content;
    Long groupId;
    String token;
    public SendRobotMessageDto(String content, Long groupId, String token){
        this.content = content;
        this.groupId = groupId;
        this.token = token;
    }
}
