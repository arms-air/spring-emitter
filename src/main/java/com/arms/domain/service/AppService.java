package com.arms.domain.service;

import com.arms.app.helpers.Gravatar;
import com.arms.domain.entity.RelationShip;
import com.arms.domain.entity.User;
import com.arms.domain.repository.MicropostRepository;
import com.arms.domain.repository.RelationShipRepository;
import com.arms.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Created by arms_matsushita on 西暦2016/09/27.
 */
@Service
public class AppService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationShipRepository relationShipRepository;

    @Autowired
    MicropostRepository micropostRepository;

    /**
     *
     * @param principal
     * @return
     */
    public Integer getUserId(Principal principal) {
        if(principal == null)
            return null;
        else {
            Authentication auth = (Authentication) principal;
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User user = userRepository.findOneByEmail(userDetails.getUsername());
            if(user == null)
                return null;
            else
                return user.getId();
        }
    }

    /**
     *
     * @param userId
     * @return
     */
    public List<RelationShip> getFollowingListByUserId(int userId) {
        return relationShipRepository.findAllByFollowerId(userId);
    }

    /**
     *
     * @param userId
     * @return
     */
    public List<RelationShip> getFollowerListByUserId(int userId) {
        return relationShipRepository.findAllByUserId(userId);
    }

    /**
     *
     * @param principal
     * @return
     */
    public User findOne(Principal principal) {
        if(principal == null)
            return null;
        else {
            Authentication auth = (Authentication) principal;
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return userRepository.findOneByEmail(userDetails.getUsername());
        }
    }

    /**
     *
     * @param email
     * @return
     */
    public String getGravatarUrl(String email) {
        Gravatar graCtrl = new Gravatar();
        String gravatarId = graCtrl.md5Hex(email);
        return "http://secure.gravatar.com/avatar/" + gravatarId;
    }
}
