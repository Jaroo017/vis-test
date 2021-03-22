package com.osekj.vis.model.entity;

import com.osekj.vis.model.enums.PermissionType;
import com.osekj.vis.converter.PermissionTypeToStringConverter;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
public class Permission {

    @Id
    @Column(name = "PERMISSION_ID")
    private Long permissionId;

    @NotBlank
    @Convert(converter = PermissionTypeToStringConverter.class)
    @Column(name = "PERMISSION_NAME")
    private PermissionType permissionName;
}
