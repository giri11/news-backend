package com.newsapp.config;

import com.newsapp.model.Menu;
import com.newsapp.model.Role;
import com.newsapp.model.User;
import com.newsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role : user.getRoles()){
            for(Menu menu : role.getMenus()){
                authorities.add(new SimpleGrantedAuthority("ROLE_" +menu.getMenuAccess()));
            }
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
//                .authorities(user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
//                        .collect(Collectors.toList()))
                .authorities(authorities)
                .accountExpired(!user.getActive())
                .accountLocked(!user.getActive())
                .credentialsExpired(false)
                .disabled(!user.getActive())
                .build();
    }

}
