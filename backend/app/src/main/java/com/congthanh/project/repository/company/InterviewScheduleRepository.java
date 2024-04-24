package com.congthanh.project.repository.company;

import com.congthanh.project.entity.company.InterviewSchedule;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {
}
