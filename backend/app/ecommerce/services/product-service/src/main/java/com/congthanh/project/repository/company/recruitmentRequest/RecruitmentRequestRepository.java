package com.congthanh.project.repository.company.recruitmentRequest;

import com.congthanh.project.entity.company.RecruitmentRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface RecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Long>, RecruitmentRequestCustomRepository {

    Optional<RecruitmentRequest> getById(int id);

}
