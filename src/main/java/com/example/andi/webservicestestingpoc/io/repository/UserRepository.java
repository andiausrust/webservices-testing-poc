package com.example.andi.webservicestestingpoc.io.repository;

import com.example.andi.webservicestestingpoc.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}
