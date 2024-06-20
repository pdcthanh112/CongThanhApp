package com.congthanh.project.repository.management;

import com.congthanh.project.entity.management.Department;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

  Department findByName(String name);

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE mydream.department SET status = 'Deteted' WHERE id = ?1")
  boolean deleteDepartment(int id);

}
