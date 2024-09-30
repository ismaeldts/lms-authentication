package com.lms.auth.infrastructure.adapter.jpa.repository;

import com.lms.auth.infrastructure.adapter.jpa.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {


  @Query("SELECT u FROM UserEntity u WHERE u.email = :email OR u.phoneNumber = :phoneNumber")
  Optional<UserEntity> findByEmailOrPhoneNumber(
      @Param("email") String email,
      @Param("phoneNumber") String phoneNumber);
}
