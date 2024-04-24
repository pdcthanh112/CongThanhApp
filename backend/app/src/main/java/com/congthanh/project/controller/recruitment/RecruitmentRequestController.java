package com.congthanh.project.controller.recruitment;

import com.congthanh.project.service.company.RecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recruitment/recruitment-request")
public class RecruitmentRequestController {

  @Autowired
  private RecruitmentRequestService recruitmentRequestService;

//    @GetMapping("/getAll")
//    public ResponseEntity<> getAllRecruitmentRequest() {
//        return ResponseEntity.ok();
//    }
}
