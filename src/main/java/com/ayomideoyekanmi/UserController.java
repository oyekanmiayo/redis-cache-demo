package com.ayomideoyekanmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Cacheable(value = "users", key = "#userId.concat(#userId)")
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userId){
        LOG.info("Getting user with ID {}.", userId);
        return userRepository.findById(Long.valueOf(userId)).get();
    }


    @CachePut(value = "users", key = "#user.id")
    @PutMapping("user/update")
    public User updatePersonById(@RequestBody User user){
        userRepository.save(user);
        return user;
    }

    @CacheEvict(value = "users", key = "#userId")
    @DeleteMapping("user/delete/{userId}")
    public void deleteUserById(@PathVariable String userId){
        LOG.info("Deleting user with id {}.", userId);
        userRepository.deleteUserById(Long.valueOf(userId));
    }

}
