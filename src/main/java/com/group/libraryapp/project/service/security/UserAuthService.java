package com.group.libraryapp.project.service.security;

import com.group.libraryapp.project.domain.user.User;
import com.group.libraryapp.project.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String privateId) throws UsernameNotFoundException {
        User user = userRepository.findByPrivateId(privateId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("user.getRole() = " + user.getRole());

        return new org.springframework.security.core.userdetails.User(
                user.getId(),
                user.getPw(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
