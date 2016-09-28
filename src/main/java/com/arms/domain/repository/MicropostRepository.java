package com.arms.domain.repository;

import com.arms.domain.entity.Micropost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by arms_matsushita on 西暦2016/09/27.
 */
@Repository
public interface MicropostRepository extends JpaRepository<Micropost, Integer> {

    List<Micropost> findAllByUserId(int userId);
    Page<Micropost> findAllByUserIdOrderByUpdatedDesc(int userId, Pageable pageable);
    Page<Micropost> findByIdInOrderByUpdatedDesc(Collection id, Pageable pageable);
}
