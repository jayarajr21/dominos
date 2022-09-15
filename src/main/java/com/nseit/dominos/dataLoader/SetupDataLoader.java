package com.nseit.dominos.dataLoader;

import com.nseit.dominos.model.OrderUser;
import com.nseit.dominos.model.Role;
import com.nseit.dominos.repositary.RoleRepository;
import com.nseit.dominos.repositary.OrderUserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

import static com.nseit.dominos.model.Role.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private OrderUserRepository orderUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

//         Create user roles
        Role userRole = createRoleIfNotFound(ROLE_USER);
        Role adminRole = createRoleIfNotFound(ROLE_ADMIN);

        // Create users
        createUserIfNotFound("admin", adminRole);

        alreadySetup = true;
    }

    @Transactional
    private Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role = roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private @NotNull OrderUser createUserIfNotFound(final String name, final Role role) {
        OrderUser user = orderUserRepository.findByUserName(name);
        if (user == null) {
            user = new OrderUser(name, bCryptPasswordEncoder.encode("admin"));
            user.setRoles(Set.of(role));
            user = orderUserRepository.save(user);
        }
        return user;
    }
}
