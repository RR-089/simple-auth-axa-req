package com.axa.simple_auth_axa_req.model;

import com.axa.simple_auth_axa_req.enums.PermissionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "permissions", schema = "auth_axa")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long id;

    @Column(name = "permission_type", nullable = false)
    private PermissionType type;

    @ManyToOne
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "role_id"
    )
    @JsonIgnoreProperties({"permissions", "user"})
    private Role role;
}
