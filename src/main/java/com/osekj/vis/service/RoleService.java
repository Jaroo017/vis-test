package com.osekj.vis.service;

import com.osekj.vis.dto.RoleDto;
import com.osekj.vis.dto.RoleResponseDto;
import com.osekj.vis.model.entity.Permission;
import com.osekj.vis.model.entity.Role;
import com.osekj.vis.model.repository.PermissionRepository;
import com.osekj.vis.model.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.osekj.vis.model.enums.PermissionType.fromString;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public List<RoleResponseDto> getAllRoles() {
        return roleRepository.findAll().stream().map(role ->
                new RoleResponseDto(role.getRoleId(), role.getRoleName(), convertPermissionsToString(role.getPermissions())))
                .collect(Collectors.toList());
    }

    @Transactional
    public RoleResponseDto createRole(RoleDto roleDto) {
        checkIfRoleExists(roleDto.getName());
        Role save = roleRepository.save(new Role(roleDto.getName(), processPermissions(roleDto.getPermissions())));
        return new RoleResponseDto(save.getRoleId(), save.getRoleName(), convertPermissionsToString(save.getPermissions()));
    }

    @Transactional
    public RoleResponseDto updateRole(Long id, RoleDto roleDto) {
        Role role = roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        checkIfRoleExists(roleDto.getName());
        role.setRoleName(roleDto.getName());
        role.setPermissions(processPermissions(roleDto.getPermissions()));
        return new RoleResponseDto(role.getRoleId(), role.getRoleName(), convertPermissionsToString(role.getPermissions()));
    }

    @Transactional
    public void deleteRole(Long id) {
        if (roleRepository.findById(id).isPresent()) {
            roleRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    private void checkIfRoleExists(String roleName) {
        if (roleRepository.existsByRoleName(roleName)) {
            throw new IllegalArgumentException();
        }
    }

    private Set<Permission> processPermissions(List<String> permissions) {
        Set<Permission> permissionList = new LinkedHashSet<>();
        permissions.forEach(permission -> {
            Optional<Permission> byPermissionName =
                    permissionRepository.findByPermissionName(fromString(permission));
            if (byPermissionName.isPresent()) {
                permissionList.add(byPermissionName.get());
            } else {
                throw new EntityNotFoundException();
            }
        });
        return permissionList;
    }

    private static List<String> convertPermissionsToString(Set<Permission> permissionList) {
        return permissionList.stream()
                .map(permission -> permission.getPermissionName().name())
                .collect(Collectors.toList());
    }
}
