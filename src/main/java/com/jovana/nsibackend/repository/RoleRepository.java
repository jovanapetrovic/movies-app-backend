package com.jovana.nsibackend.repository;

import com.jovana.nsibackend.model.Role;
import com.jovana.nsibackend.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Created by jovana on 05.11.2018
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
