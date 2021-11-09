package com.acertainscientific.meetup.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
public class LoginInfo {
    private Integer code;
    private Map token;
    private String auth;
}
