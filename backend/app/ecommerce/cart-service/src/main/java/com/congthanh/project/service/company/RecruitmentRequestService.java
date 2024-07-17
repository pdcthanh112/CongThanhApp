package com.congthanh.project.service.company;

import com.congthanh.project.dto.company.RecruitmentRequestDTO;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.entity.company.RecruitmentRequest;

public interface RecruitmentRequestService {

  public ResponseWithPagination<RecruitmentRequestDTO> getAllRecruitmetRequest(int pageNo, int pageSize);

  public RecruitmentRequest createRecruitmentRequest(RecruitmentRequestDTO recruitmentRequestDTO);

  public RecruitmentRequest updateRecruitmentRequest(RecruitmentRequestDTO recruitmentRequestDTO);

  public boolean deleteRecruitmentRequest(int id);

}
