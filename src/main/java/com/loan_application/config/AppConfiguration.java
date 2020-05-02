package com.loan_application.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;


@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application.properties")
@Getter
public class AppConfiguration {


    @Value("${loan.min.principal.property}")
    private BigDecimal minPrincipal;
    @Value("${loan.max.principal.property}")
    private BigDecimal maxPrincipal;
    @Value("${loan.commission.property}")
    private BigDecimal commission;
    @Value("${loan.min.term.property}")
    private Integer minTerm;
    @Value("${loan.max.term.property}")
    private Integer maxTerm;
    @Value("#{ T(java.time.LocalTime).parse('${loan.min.time.property}')}")
    private LocalTime minTime;
    @Value("#{ T(java.time.LocalTime).parse('${loan.max.time.property}')}")
    private LocalTime maxTime;
    @Value("#{'${loan.extension.list.property}'.split(',')}")
    private List<Integer> extension;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
