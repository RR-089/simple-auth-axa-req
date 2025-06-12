package com.axa.simple_auth_axa_req.repository;

import com.axa.simple_auth_axa_req.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
