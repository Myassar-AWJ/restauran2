package com.restaurant.restaurantdemo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class LoggerService {

    private static final Logger logger = LoggerFactory.getLogger(LoggerService.class);

    public void info(String msg) {
        logger.info(msg);
        // Your service logic here
    }

    public void error(String msg, String e) {
        logger.error(msg, e);
        // Your service logic here
    }

    public void error(String msg, Throwable e) {
        logger.error(msg, e.getCause());
        // Your service logic here
    }
}
