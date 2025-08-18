package com.prize.lottery.infrast.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;


@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ValidationConfigurer {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public static Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                         .configure()
                         .failFast(true)
                         .buildValidatorFactory()
                         .getValidator();
    }

    @Bean
    public static MethodValidationPostProcessor methodValidationPostProcessor(Validator validator) {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator);
        return processor;
    }
}
