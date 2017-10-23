package com.ksm.robolo.roboloapp.repository;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.domain.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long>{

    VerificationToken findByToken(String token);
    VerificationToken findByUser(UserEntity user);
    List<VerificationToken> findAll();
}
