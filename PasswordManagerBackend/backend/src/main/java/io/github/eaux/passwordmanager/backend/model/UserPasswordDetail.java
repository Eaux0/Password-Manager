package io.github.eaux.passwordmanager.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserPasswordDetails")
public class UserPasswordDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPasswordDetailId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_password_id")
    private Long userPasswordId;

    @Column(name = "user_password_name")
    private String userPasswordName;

    @Column(name = "user_password_description")
    private String userPasswordDescription;

}
