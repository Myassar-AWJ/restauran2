package com.restaurant.restaurantdemo.application.service.Jwt;

import com.restaurant.restaurantdemo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.restaurant.restaurantdemo.boundary.output.jpa.user.UserRepository;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
             throw new UsernameNotFoundException("could not found user..!!");
        }
        return  new CustomUserDetails(user);;
    }
}
