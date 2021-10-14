package com.vinayak.blog.services.serviceImplementation;

import com.vinayak.blog.dto.UserDto;
import com.vinayak.blog.models.Role;
import com.vinayak.blog.models.User;
import com.vinayak.blog.repositories.UserRepository;
import com.vinayak.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String USER_ROLE = "USER";

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserDetails(UserDto registrationDto) {
        User user = new User(registrationDto.getName(),registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()) , List.of(new Role(USER_ROLE)));
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            System.out.println("Current User Details: " + username);
            user = userRepository.findByEmail(username);
        }
        return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
