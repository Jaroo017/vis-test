package com.osekj.vis.model.repository;

import com.osekj.vis.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleName(String roleName);
}
