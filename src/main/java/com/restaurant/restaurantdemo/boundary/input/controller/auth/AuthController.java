package com.restaurant.restaurantdemo.boundary.input.controller.auth;

import com.restaurant.restaurantdemo.application.dto.auth.JwtResponseDTO;
import com.restaurant.restaurantdemo.application.dto.auth.RegisterRequestDto;
import com.restaurant.restaurantdemo.application.dto.auth.UserLoginDTO;
import com.restaurant.restaurantdemo.application.service.jwt.JwtService;
import com.restaurant.restaurantdemo.application.service.user.UserService;
import com.restaurant.restaurantdemo.domain.user.Role;
import com.restaurant.restaurantdemo.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/login")
    public JwtResponseDTO Login(@RequestBody @Valid UserLoginDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtService.GenerateToken(authRequestDTO.getEmail());
            return JwtResponseDTO.builder()
                    .accessToken(token)
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/register")
    public JwtResponseDTO Register(@RequestBody @Valid RegisterRequestDto authRequestDTO){
        User user= modelMapper.map(authRequestDTO, User.class);
        user.setRole(Role.USER);
        User newUser = userService.createUser(user);
         if(newUser !=null){
            String token = jwtService.GenerateToken(newUser.getEmail());
            return JwtResponseDTO.builder()
                    .accessToken(token)
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }





}
