package io.github.eaux.passwordmanager.user_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.eaux.passwordmanager.user_auth.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.userId = :userId")
    public User findUserByUserId(Long userId);

    @Query("select u.passwordHash from User u where u.userId = :userId")
    public String getPasswordHashByUserId(Long userId);

    @Query("select u from User u where u.username = :username")
    public User findUserByUsername(String username);

    boolean existsByUsername(String username);

}
