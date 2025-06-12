package com.axa.simple_auth_axa_req.repository;

import com.axa.simple_auth_axa_req.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByIdIn(List<Long> ids);
}
