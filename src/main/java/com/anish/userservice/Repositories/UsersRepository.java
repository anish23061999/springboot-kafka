package com.anish.userservice.Repositories;

import com.anish.userservice.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    // You can define custom query methods here if needed

    @Query
    Optional<UsersEntity> findByMobileNumber(String mobileNumber);

}

