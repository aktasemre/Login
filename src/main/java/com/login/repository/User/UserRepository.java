package com.login.repository.User;

import com.login.entity.enums.RoleType;
import com.login.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT (u) FROM User u INNER JOIN u.userRole r WHERE r.roleType =?1")
    long countAdmin(RoleType roleType);


    @Query("SELECT u FROM User u WHERE " +
            "u.firstName LIKE %:q% OR u.lastName LIKE %:q% OR u.email LIKE %:q% OR " +
            "u.telephone LIKE %:q% OR u.dogumYeri LIKE %:q% OR " +
            "u.uyruk LIKE %:q% OR u.babaAdi LIKE %:q% OR u.anneAdi LIKE %:q%")
    List<User> araUser(@Param("q") String q);



}
