package com.nseit.dominos.config;

import com.nseit.dominos.model.OrderUser;
import com.nseit.dominos.model.Role;
import com.nseit.dominos.repositary.OrderUserRepository;
import com.nseit.dominos.repositary.RoleRepository;
import com.nseit.dominos.service.OrderUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

@Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
    public class SecurityConfig {

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return new OrderUserDetailsService();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService());
            authProvider.setPasswordEncoder(bCryptPasswordEncoder());

            return authProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/auth/**")
                    .permitAll()
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/api/admin/**")
//                    .hasAnyRole(Role.ROLE_ADMIN)
//                    .antMatchers("/api/domino/**")
//                    .hasAnyRole(Role.ROLE_ADMIN, Role.ROLE_USER)
//                    .anyRequest()
//                    .authenticated()
                    .and().httpBasic().and()
                    .formLogin();

            httpSecurity.authenticationProvider(authenticationProvider());

            return httpSecurity.build();
        }

    @Component
    public static class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

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
            Role userRole = createRoleIfNotFound(Role.USER);
            Role adminRole = createRoleIfNotFound(Role.ADMIN);

            // Create users
            createUserIfNotFound("admin", adminRole);

            alreadySetup = true;
        }

        @Transactional
        private Role createRoleIfNotFound(final String name) {
            Role role = roleRepository.findByName("UserName");
           if (role == null) {
                role = new Role();
                role.setName(name);
                role = roleRepository.save(role);
            }
            return role;
        }

        @Transactional
        private OrderUser createUserIfNotFound(final String name, final Role role) {
            OrderUser orderUser = orderUserRepository.findByUserName(name);
            if (orderUser == null) {
                orderUser = new OrderUser(name, bCryptPasswordEncoder.encode("admin"));
                orderUser.setRoles(Set.of(role));
                orderUser = orderUserRepository.save(orderUser);
            }
            return orderUser;
        }
    }
}
