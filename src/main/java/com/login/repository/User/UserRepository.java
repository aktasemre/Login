package com.login.repository.User;

import com.login.entity.enums.RoleType;
import com.login.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT (u) FROM User u INNER JOIN u.userRole r WHERE r.roleType =?1")
    long countAdmin(RoleType roleType);


}
