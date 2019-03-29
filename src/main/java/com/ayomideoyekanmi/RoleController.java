package com.ayomideoyekanmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Cacheable(value = "roleMapping", key = "#roleId")
    @RequestMapping(value = "/role/{roleId}")
    public Role getRole(@PathVariable String roleId){
        LOG.info("Getting user with ID {}.", roleId);
        return roleRepository.findById(Long.valueOf(roleId)).get();
    }

    @CachePut(value = "roleMapping", key = "#role.id")
    @PutMapping(value = "role/update")
    public Role updateRoleById(@RequestBody Role role){
        roleRepository.save(role);
        return role;
    }

    @CacheEvict(value = "roleMapping", key = "#roleId")
    @RequestMapping(value = "role/delete/{roleId}", method = RequestMethod.DELETE)
    public void deleteRoleById(@PathVariable String roleId){
        LOG.info("Deleting Role with ID {}.", roleId);
        roleRepository.deleteRoleById(Long.valueOf(roleId));
    }

}
