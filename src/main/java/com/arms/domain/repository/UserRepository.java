package com.arms.domain.repository;

import com.arms.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by arms_matsushita on 西暦2016/09/26.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findOneByEmail(String email);
    Page<User> findByIdInOrderByUpdatedDesc(Collection id, Pageable pageable);
}
