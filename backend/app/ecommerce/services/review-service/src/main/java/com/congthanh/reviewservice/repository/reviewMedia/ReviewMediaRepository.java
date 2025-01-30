package com.congthanh.reviewservice.repository.reviewMedia;

import com.congthanh.reviewservice.model.entity.ReviewMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewMediaRepository extends JpaRepository<ReviewMedia, Long> {
}
