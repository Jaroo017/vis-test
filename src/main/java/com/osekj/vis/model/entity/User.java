package com.osekj.vis.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @NotBlank
    @Column(name = "USERNAME")
    private String username;

    @NotBlank
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
