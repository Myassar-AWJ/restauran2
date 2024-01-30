package com.restaurant.restaurantdemo.application.service.user;

import com.restaurant.restaurantdemo.boundary.output.jpa.user.UserRepository;

import com.restaurant.restaurantdemo.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public  User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User createUser(@NotNull User user){
        if (userRepository.findByEmail(user.getEmail()) !=null){
            throw new IllegalStateException("this Email is already used before.");
        }
        String name=user.getName();
        String pass=user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }



    public void updateUser(@NotNull User userDetails, Long id, String email){
        if (userRepository.findByEmailAndIdNot(userDetails.getEmail(),id)!= null){
            throw new IllegalStateException("this Email is already used before.");
        }
        // Check if the username from JWT matches or if the user has admin role
        if(!userDetails.getEmail().equals(email) && !isAdmin(email)) {
            throw new AccessDeniedException("You don't have permission to update this user.");
        }
        User existingUser=userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setName(userDetails.getName());
        userRepository.save(existingUser);

    }

    @Transactional
    public void updateUserPassword(Long id, String newPassword, String email) {
        // Check if the email from JWT matches or if the user has admin role
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        if(!existingUser.getEmail().equals(email) && !isAdmin(email)) {
            throw new AccessDeniedException("You don't have permission to update the password.");
        }

        // Validate new password (if necessary)

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(existingUser);
    }



    private boolean isAdmin(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getName().equals(email)
                && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

}
