package com.restaurant.restaurantdemo.boundary.output.jpa.user;

import com.restaurant.restaurantdemo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByEmailAndIdNot(String email, Long id);
}
