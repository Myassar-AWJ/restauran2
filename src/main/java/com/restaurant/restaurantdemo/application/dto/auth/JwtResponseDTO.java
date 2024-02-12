package com.restaurant.restaurantdemo.application.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponseDTO { // you can use records https://www.baeldung.com/java-record-keyword
    private String accessToken;
}
