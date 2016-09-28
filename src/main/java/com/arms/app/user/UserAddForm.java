package com.arms.app.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by arms_matsushita on 西暦2016/09/24.
 */
@Data
public class UserAddForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
