package com.congthanh.project.entity.management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account", indexes = @Index(columnList = "account_id"))
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "numeric")
  private Long id;

  private String email;

  @Column(name = "account_id", nullable = false, unique = true)
  private String accountId;

  @Column(name = "emp_account", nullable = false, unique = true)
  private String empAccount;

  private String role;
}
