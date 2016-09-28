package com.arms.domain.service;

import com.arms.app.user.UserAddForm;
import com.arms.app.user.UserEditForm;
import com.arms.app.user.UserFollowForm;
import com.arms.domain.component.PasswordEncoder;
import com.arms.domain.entity.Micropost;
import com.arms.domain.entity.RelationShip;
import com.arms.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arms_matsushita on 西暦2016/09/26.
 */
@Service
public class UserService extends AppService {

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     *
     * @return
     */
    public Page<User> findAllUser(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    /**
     *
     * @param userId
     * @return
     */
    public User findOne(int userId) {
        return userRepository.findOne(userId);
    }

    /**
     * Create new user.
     * @param userAddForm
     * @throws NoSuchAlgorithmException
     */
    public void createUser(UserAddForm userAddForm) throws NoSuchAlgorithmException {
        Date nowDate = Calendar.getInstance().getTime();
        User user = new User();
        user.setName(userAddForm.getName());
        user.setEmail(userAddForm.getEmail());
        user.setPassword(passwordEncoder.hashMD5(userAddForm.getPassword()));
        user.setCreated(nowDate);
        user.setUpdated(nowDate);
        userRepository.save(user);
    }

    /**
     *
     * @param userEditForm
     * @throws NoSuchAlgorithmException
     */
    public void updateUser(UserEditForm userEditForm) throws NoSuchAlgorithmException {
        Date nowDate = Calendar.getInstance().getTime();
        User user = userRepository.findOne(userEditForm.getUserId());
        user.setName(userEditForm.getName());
        // user.setEmail(userEditForm.getEmail());
        user.setPassword(passwordEncoder.hashMD5(userEditForm.getPassword()));
        user.setUpdated(nowDate);
    }

    /**
     *
     * @param userId
     */
    public void deleteUser(int userId) {
        userRepository.delete(userId);
    }

    /**
     *
     * @param userId
     * @param pageable
     * @return
     */
    public Page<User> findAllFollowing(int userId, Pageable pageable) {
        List<RelationShip> relationShipList = relationShipRepository.findAllByFollowerId(userId);
        List<Integer> emitterIdList = new ArrayList<>();
        for(RelationShip relationShip : relationShipList) {
            emitterIdList.add(relationShip.getUserId());
        }
        return userRepository.findByIdInOrderByUpdatedDesc(emitterIdList, pageable);
    }

    /**
     *
     * @param userId
     * @param pageable
     * @return
     */
    public Page<User> findAllFollower(int userId, Pageable pageable) {
        List<RelationShip> relationShipList = relationShipRepository.findAllByUserId(userId);
        List<Integer> followerIdList = new ArrayList<>();
        for(RelationShip relationShip : relationShipList) {
            followerIdList.add(relationShip.getFollowerId());
        }
        return userRepository.findByIdInOrderByUpdatedDesc(followerIdList, pageable);
    }

    /**
     *
     * @param userFollowForm
     */
    public void addFollow(UserFollowForm userFollowForm) {
        if(relationShipRepository.findOneByUserIdAndFollowerId(userFollowForm.getUserId(), userFollowForm.getFollowerId()) == null) {
            RelationShip relationShip = new RelationShip();
            relationShip.setUserId(userFollowForm.getUserId());
            relationShip.setFollowerId(userFollowForm.getFollowerId());
            relationShipRepository.save(relationShip);
        }
    }

    /**
     *
     * @param userFollowForm
     */
    public void deleteFollow(UserFollowForm userFollowForm) {
        RelationShip relationShip = relationShipRepository.findOneByUserIdAndFollowerId(userFollowForm.getUserId(), userFollowForm.getFollowerId());
        if(relationShip != null)
            relationShipRepository.delete(relationShip);
    }

    /**
     *
     * @param emitterUserId
     * @param followerUserId
     * @return
     */
    public boolean isFollow(int emitterUserId, int followerUserId) {
        if(relationShipRepository.findOneByUserIdAndFollowerId(emitterUserId, followerUserId) == null)
            return false;
        else
            return true;
    }

    /**
     *
     * @param userId
     * @return
     */
    public UserEditForm setUserEditForm(int userId) {
        User user = userRepository.findOne(userId);
        UserEditForm userEditForm = new UserEditForm();
        userEditForm.setUserId(user.getId());
        userEditForm.setName(user.getName());
        userEditForm.setEmail(user.getEmail());
        return userEditForm;
    }

    /**
     *
     * @param userId
     * @param followerId
     * @return
     */
    public UserFollowForm getUserFollowForm(int userId, int followerId) {
        UserFollowForm userFollowForm = new UserFollowForm();
        userFollowForm.setUserId(userId);
        userFollowForm.setFollowerId(followerId);
        return userFollowForm;
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Micropost> findAllMicropostByUserId(int userId, Pageable pageable) {
        return micropostRepository.findAllByUserIdOrderByUpdatedDesc(userId, pageable);
    }
}
