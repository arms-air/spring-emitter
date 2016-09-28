package com.arms.app.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by arms_matsushita on 西暦2016/09/25.
 */
@Data
public class UserEditForm {

    @NotNull
    private Integer userId;

    @NotEmpty
    private String name;

    private String email;

    @NotEmpty
    private String password;
}
