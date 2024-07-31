package com.congthanh.project.repository.company.recruitmentRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;

public class RecruitmentRequestCustomRepositoryImpl implements RecruitmentRequestCustomRepository{

    @PersistenceContext
    @Qualifier("companyEntityManagerFactory")
    private EntityManager entityManager;

}
