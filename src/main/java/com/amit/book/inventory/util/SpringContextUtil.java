package com.amit.book.inventory.util;


import com.amit.book.inventory.Config.ConfigApp;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContextUtil {

    @Getter
    private static final ApplicationContext context = new AnnotationConfigApplicationContext(ConfigApp.class);

    private SpringContextUtil() {
        // private constructor to prevent instantiation
    }

}