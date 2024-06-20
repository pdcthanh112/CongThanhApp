package com.congthanh.project.repository.company.interviewSchedule;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;

public class InterviewScheduleCustomRepositoryImpl implements InterviewScheduleCustomRepository{

    @PersistenceContext
    @Qualifier("companyEntityManagerFactory")
    private EntityManager entityManager;

}
