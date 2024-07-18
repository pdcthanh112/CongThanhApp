package com.congthanh.project.entity.company;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "interview_schedule")
public class InterviewSchedule {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "numeric")
  private Long id;

  private String candidateId;

  private String type;

  private String address;

  private Date date;

  private Time time;

  private String linkMeeting;

  private String room;

  private int round;

  private String status;
}
