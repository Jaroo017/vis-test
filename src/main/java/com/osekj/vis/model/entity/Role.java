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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ROLE_ID")
    private Long roleId;

    @NotBlank
    @Column(name = "ROLE_NAME")
    private String roleName;

    @NotEmpty
    @ManyToMany
    @JoinTable(name = "ROLE_PERMISSIONS",
            joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID"),},
            inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID", referencedColumnName = "PERMISSION_ID")})
    private Set<Permission> permissions;

    public Role(String roleName, Set<Permission> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }
}
