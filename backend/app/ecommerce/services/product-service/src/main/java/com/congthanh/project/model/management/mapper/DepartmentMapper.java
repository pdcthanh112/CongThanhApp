package com.congthanh.project.model.management.mapper;

import com.congthanh.project.dto.management.DepartmentDTO;
import com.congthanh.project.entity.management.Department;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Department mapDepartmentDTOToEntity(DepartmentDTO departmentDTO) {
        return modelMapper.map(departmentDTO, Department.class);
    }

    public static DepartmentDTO mapDepartmentEntityToDTO(Department department) {
        return modelMapper.map(department, DepartmentDTO.class);
    }

}
