package com.osekj.vis.model.repository;

import com.osekj.vis.model.entity.Permission;
import com.osekj.vis.model.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "select p from Permission p where p.permissionName = :permissionName")
    Optional<Permission> findByPermissionName(@Param("permissionName") PermissionType permissionName);
}
