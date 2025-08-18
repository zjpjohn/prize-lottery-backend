package com.prize.lottery.infrast.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;


@Configuration
public class ValidationConfigurer {

    @Bean
    public static Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                         .configure()
                         .failFast(true)
                         .buildValidatorFactory()
                         .getValidator();
    }

    @Bean
    public static MethodValidationPostProcessor methodValidationPostProcessor(@Autowired Validator validator) {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator);
        return processor;
    }

}
