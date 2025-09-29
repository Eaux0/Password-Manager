package io.github.eaux.passwordmanager.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.eaux.passwordmanager.backend.model.UserPassword;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Long> {

    @Query("select up from UserPassword up where up.userId = :userId")
    List<UserPassword> findAllByUserId(Long userId);

    @Query("select up from UserPassword up where up.groupId = :groupId")
    List<UserPassword> findAllByGroupId(Long groupId);

    @Query("select up from UserPassword up where up.userId = :userId and (up.groupName like concat('%', :searchString, '%') or up.groupDescription like concat('%', :searchString, '%'))")
    List<UserPassword> findAllByUserIdAndSearchString(Long userId, String searchString);

}
