package com.congthanh.project.service.management;

import com.congthanh.project.dto.management.DepartmentDTO;
import com.congthanh.project.entity.management.Department;

public interface DepartmentService {
  public Object getAllDepartment(Integer page, Integer limit);

  public Department createDepartment(DepartmentDTO departmentDTO);

  public Department updateDepartment(DepartmentDTO departmentDTO);

  public boolean deleteDepartment(int id);
}
