package com.jdragon.tljrobot.client.api.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 * <p>create time: 2021/12/28 23:29 </p>
 *
 * @author : Jdragon
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String username;

    private String password;

}
