package com.congthanh.project.entity.company;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recruitment_request")
public class RecruitmentRequest {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "numeric")
  private Long id;

  private String name;

  private int amount;

  private String address;

  private Date expiriedDate;

  @Column(columnDefinition = "text")
  private String description;

  @Column(columnDefinition = "text")
  private String requirement;

  @Column(columnDefinition = "text")
  private String benefit;

  private String status;
}
