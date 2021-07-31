package com.change.password.repository;

import com.change.password.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserEntity,Long> {

    /* Created by suditi on 2021-08-01 */
    @Query(value = "SELECT * FROM user_details where user_email_address=:user_email_address",nativeQuery = true)
    UserEntity findByEmail(@Param("user_email_address") String email);


    @Modifying
    @Query(value ="UPDATE user_details c SET c.user_password  = :userPassword WHERE c.user_email_address = :email", nativeQuery= true)
    @Transactional
    int updateUserPassword(@Param("userPassword") String userPassword, @Param("email") String email);


}
