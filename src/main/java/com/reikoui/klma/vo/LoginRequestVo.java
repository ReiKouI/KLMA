package com.reikoui.klma.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class LoginRequestVo {

    @NotNull(message = "账号不能为空")
    @Length(min = 11, max = 11, message = "账号必须为11位")
    private String id;
    @NotNull(message = "密码不能为空")
    @Length(min = 32, max = 32)
    private String password;


}
