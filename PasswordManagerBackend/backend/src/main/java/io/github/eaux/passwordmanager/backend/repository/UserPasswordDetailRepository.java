package io.github.eaux.passwordmanager.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.eaux.passwordmanager.backend.model.UserPasswordDetail;

public interface UserPasswordDetailRepository extends JpaRepository<UserPasswordDetail, Long> {
    List<UserPasswordDetail> findAllByUserId(Long userId);

    List<UserPasswordDetail> findAllByUserPasswordId(Long userPasswordId);

    @Query("select upd from UserPasswordDetail upd where upd.userId = :userId and (upd.groupName like concat('%', :searchString, '%') or upd.groupDescription like concat('%', :searchString, '%'))")
    List<UserPasswordDetail> findAllByUserIdAndSearchString(Long userId, String searchString);
}
