package com.arms.domain.service;

import com.arms.domain.entity.Micropost;
import com.arms.domain.entity.RelationShip;
import com.arms.domain.repository.MicropostRepository;
import com.arms.domain.repository.RelationShipRepository;
import com.arms.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arms_matsushita on 西暦2016/09/27.
 */
@Service
public class HomeService extends AppService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MicropostRepository micropostRepository;

    @Autowired
    RelationShipRepository relationShipRepository;

    /**
     *
     * @param userId
     * @return
     */
    public List<Integer> getMyMicropost(int userId) {
        List<Micropost> micropostList = micropostRepository.findAllByUserId(userId);
        List<Integer> micropostIdList = new ArrayList<>();
        for(Micropost micropost : micropostList) {
            micropostIdList.add(micropost.getId());
        }
        return micropostIdList;
    }

    /**
     *
     * @param userId
     * @param micropostIdList
     */
    public void setFollowingMicropost(int userId, List<Integer> micropostIdList) {
        List<RelationShip> relationShipList = relationShipRepository.findAllByFollowerId(userId);
        for(RelationShip relationShip : relationShipList) {
            List<Micropost> micropostList = micropostRepository.findAllByUserId(relationShip.getUserId());
            for(Micropost micropost : micropostList) {
                micropostIdList.add(micropost.getId());
            }
        }
    }

    /**
     *
     * @param micropostIdList
     * @param pageable
     * @return
     */
    public Page<Micropost> findAllByIdList(List<Integer> micropostIdList, Pageable pageable) {
        return micropostRepository.findByIdInOrderByUpdatedDesc(micropostIdList, pageable);
    }
}
