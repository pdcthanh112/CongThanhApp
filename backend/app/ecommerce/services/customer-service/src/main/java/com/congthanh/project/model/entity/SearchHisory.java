package com.congthanh.project.model.entity;

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
@Table(name = "search_history")
public class SearchHisory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "numeric")
    private Long id;

    private String userId;

    private String keyword;

    @Column(name = "search_time")
    private long searchTime;

}
