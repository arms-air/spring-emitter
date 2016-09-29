package com.arms.app.home;

import lombok.Data;

import java.util.Date;

/**
 * Created by arms_matsushita on 西暦2016/09/29.
 */
@Data
public class HomeListForm {

    private int userId;
    private String userName;

    private int micropostId;
    private String micropostContent;
    private Date updated;

    private String userImage;
}
