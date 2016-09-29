package com.arms.domain.service;

import com.arms.app.user.*;
import com.arms.domain.component.PasswordEncoder;
import com.arms.domain.entity.Micropost;
import com.arms.domain.entity.RelationShip;
import com.arms.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<UserListForm> findAllUser(Pageable pageable){
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserListForm> userListFormList = new ArrayList<>();
        for(User user : userPage) {
            UserListForm userListForm = new UserListForm();
            userListForm.setId(user.getId());
            userListForm.setName(user.getName());
            userListForm.setImage(getGravatarUrl(user.getEmail()));
            userListFormList.add(userListForm);
        }
        return new PageImpl<>(userListFormList);
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
    public Page<UserFollowingForm> findAllFollowing(int userId, Pageable pageable) {
        List<RelationShip> relationShipList = relationShipRepository.findAllByFollowerId(userId);
        List<Integer> emitterIdList = new ArrayList<>();
        for(RelationShip relationShip : relationShipList) {
            emitterIdList.add(relationShip.getUserId());
        }
        Page<User> userPage = userRepository.findByIdInOrderByUpdatedDesc(emitterIdList, pageable);
        List<UserFollowingForm> userFollowingFormList = new ArrayList<>();
        for(User user : userPage) {
            UserFollowingForm userFollowingForm = new UserFollowingForm();
            userFollowingForm.setId(user.getId());
            userFollowingForm.setName(user.getName());
            userFollowingForm.setImage(getGravatarUrl(user.getEmail()));
            userFollowingFormList.add(userFollowingForm);
        }
        return new PageImpl<>(userFollowingFormList);
    }

    /**
     *
     * @param userId
     * @param pageable
     * @return
     */
    public Page<UserFollowerForm> findAllFollower(int userId, Pageable pageable) {
        List<RelationShip> relationShipList = relationShipRepository.findAllByUserId(userId);
        List<Integer> followerIdList = new ArrayList<>();
        for(RelationShip relationShip : relationShipList) {
            followerIdList.add(relationShip.getFollowerId());
        }
        Page<User> userPage = userRepository.findByIdInOrderByUpdatedDesc(followerIdList, pageable);
        List<UserFollowerForm> userFollowerFormList = new ArrayList<>();
        for(User user : userPage) {
            UserFollowerForm followerForm = new UserFollowerForm();
            followerForm.setId(user.getId());
            followerForm.setName(user.getName());
            followerForm.setImage(getGravatarUrl(user.getEmail()));
            userFollowerFormList.add(followerForm);
        }
        return new PageImpl<>(userFollowerFormList);
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
