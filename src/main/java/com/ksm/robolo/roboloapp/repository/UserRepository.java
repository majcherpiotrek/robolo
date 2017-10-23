package com.ksm.robolo.roboloapp.repository;

import com.ksm.robolo.roboloapp.domain.VerificationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.enabled = true WHERE u.id = :id")
    void setUserEnabled(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.password = :password WHERE u.id = :id")
    void setNewPassword(@Param("password") String password, @Param("id") UUID id);
}
