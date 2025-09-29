package io.github.eaux.passwordmanager.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.eaux.passwordmanager.backend.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g where g.userId = :userId and (g.groupName like concat('%', :searchString, '%') or g.groupDescription like concat('%', :searchString, '%'))")
    List<Group> findAllByUserIdAndSearchString(Long userId, String searchString);

    @Query("select g from Group g where g.userId = :userId")
    List<Group> findAllByUserId(Long userId);

}
