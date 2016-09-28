package com.arms.domain.repository;

import com.arms.domain.entity.RelationShip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by arms_matsushita on 西暦2016/09/27.
 */
@Repository
public interface RelationShipRepository extends JpaRepository<RelationShip, Integer> {

    List<RelationShip> findAllByFollowerId(int followerId);
    Page<RelationShip> findAllByFollowerId(int followerId, Pageable pageable);
    List<RelationShip> findAllByUserId(int userId);
    Page<RelationShip> findAllByUserId(int userId, Pageable pageable);
    RelationShip findOneByUserIdAndFollowerId(int userId, int followerId);
}
