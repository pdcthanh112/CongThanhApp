package com.congthanh.project.serviceImpl.management;

import com.congthanh.project.constant.common.StateStatus;
import com.congthanh.project.dto.management.DepartmentDTO;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.entity.management.Department;
import com.congthanh.project.repository.management.DepartmentRepository;
import com.congthanh.project.service.management.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImplement implements DepartmentService {

  @Autowired
  private DepartmentRepository departmentRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public Object getAllDepartment(Integer page, Integer limit) {
    if (page != null && limit != null) {
      Pageable pageable = PageRequest.of(page, limit);
      Page<Department> result = departmentRepository.findAll(pageable);
      ResponseWithPagination<DepartmentDTO> response = new ResponseWithPagination<>();
      if (result.hasContent()) {
        List<DepartmentDTO> list = new ArrayList<>();
        for (Department department : result.getContent()) {
          DepartmentDTO departmentDTO = DepartmentDTO.builder()
                  .id(department.getId())
                  .name(department.getName())
                  .phone(department.getPhone())
                  .build();
          list.add(departmentDTO);
        }
        response.setResponseList(list);
        response.setTotalPage(result.getTotalPages());
      } else {
        throw new RuntimeException("List empty exception");
      }
      return response;
    } else {
      List<Department> list = departmentRepository.findAll();
      List<DepartmentDTO> result = new ArrayList<>();
      for (Department department : list) {
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        result.add(departmentDTO);
      }
      return result;
    }
  }

  @Override
  public Department createDepartment(DepartmentDTO departmentDTO) {
    Optional<Department> existDepartment = departmentRepository.findByName(departmentDTO.getName());
    if (existDepartment.isPresent()) {
      throw new RuntimeException("Department ton tai");
    } else {
      Department department = Department.builder()
              .name(departmentDTO.getName())
              .phone(departmentDTO.getPhone())
              .status(StateStatus.STATUS_ACTIVE)
              .build();
      Department response = departmentRepository.save(department);
      return response;
    }
  }

  @Override
  public Department updateDepartment(DepartmentDTO departmentDTO) {
    Department department = departmentRepository.findById(departmentDTO.getId()).orElseThrow(() -> new RuntimeException("Category not found"));

    department.setName(departmentDTO.getName());
    departmentRepository.save(department);
    return department;
  }

  @Override
  public boolean deleteDepartment(int id) {
    Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
    if (department.getStatus().equalsIgnoreCase(StateStatus.STATUS_DELETED)) {
      throw new RuntimeException("Department have deleted before");
    } else {
      boolean result = departmentRepository.deleteDepartment(id);
      return result;
    }
  }
}
