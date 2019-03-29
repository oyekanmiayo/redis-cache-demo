package com.ayomideoyekanmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@SpringBootApplication(scanBasePackages = "com.ayomideoyekanmi")
@EnableCaching
public class RedisCacheDemoApplication implements CommandLineRunner {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

    public RedisCacheDemoApplication(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(RedisCacheDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	    LOG.info("Saving users. Current user count is {}.",userRepository.count());

        User ayomide = new User("Ayomide:", 20000);
        User malcolm = new User("Malcolm", 290000);
        User funmi = new User("Funmilayo", 120000);

        userRepository.save(ayomide);
        userRepository.save(malcolm);
        userRepository.save(funmi);

        LOG.info("Done saving users. Current count is {}.",userRepository.count());

        LOG.info("Saving Roles, current count is {}.", roleRepository.count());

        Role firstRole = new Role("ADMINISTRATOR", "COSMOS");
        Role secondRole = new Role("USER", "MONEYTOR");

        roleRepository.save(firstRole);
        roleRepository.save(secondRole);

        LOG.info("Done Saving Roles, current count is now {}.", roleRepository.count());

    }
}
