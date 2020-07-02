package com.loan_application.mappers;

import com.loan_application.domain.application.Application;
import com.loan_application.representation.ApplicationDto;
import org.springframework.stereotype.Component;


@Component
public class ApplicationMapper {


    public ApplicationMapper() {

    }

    public static ApplicationDto mapToApplicationDto(Application application) {
        return ApplicationDto.builder()
                .appId(application.getAppId())
                .principal(application.getPrincipal())
                .term(application.getTerm())
                .status(application.getStatus())
                .submissionDate(application.getSubmissionDate())
                .user(application.getUser())
                .build();


    }

    public static Application mapToApplication(ApplicationDto applicationDto) {
        return Application.builder()
                .principal(applicationDto.getPrincipal())
                .term(applicationDto.getTerm())
                .build();

    }
}
