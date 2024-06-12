package com.congthanh.project.serviceImpl.management;

import com.congthanh.project.constant.common.StateStatus;
import com.congthanh.project.dto.management.PositionDTO;
import com.congthanh.project.model.ecommerce.response.PaginationInfo;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.entity.management.Department;
import com.congthanh.project.entity.management.Position;
import com.congthanh.project.repository.management.DepartmentRepository;
import com.congthanh.project.repository.management.PositionRepository;
import com.congthanh.project.service.management.PositionService;
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
public class PositionServiceImplement implements PositionService {

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private DepartmentRepository departmentRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public Object getAllPosition(Integer page, Integer limit) {
    if (page != null && limit != null) {
      Pageable pageable = PageRequest.of(page, limit);
      Page<Position> result = positionRepository.findAll(pageable);
      ResponseWithPagination<PositionDTO> response = new ResponseWithPagination<>();
      if (result.hasContent()) {
        List<PositionDTO> list = new ArrayList<>();
        for (Position position : result.getContent()) {
          PositionDTO positionDTO = PositionDTO.builder()
                  .id(position.getId())
                  .name(position.getName())
                  .department(position.getDepartment().getName())
                  .status(position.getStatus())
                  .build();
          list.add(positionDTO);
        }
        PaginationInfo paginationInfo = PaginationInfo.builder()
                .page(page)
                .limit(limit)
                .totalPage(result.getTotalPages())
                .totalElement(result.getTotalElements())
                .build();
        response.setResponseList(list);
        response.setPaginationInfo(paginationInfo);
      } else {
        throw new RuntimeException("List empty exception");
      }
      return response;
    } else {
      List<Position> list = positionRepository.findAll();
      List<PositionDTO> result = new ArrayList<>();
      for (Position position : list) {
        PositionDTO positionDTO = PositionDTO.builder()
                .id(position.getId())
                .name(position.getName())
                .department(position.getDepartment().getName())
                .status(position.getStatus())
                .build();
        result.add(positionDTO);
      }
      return result;
    }
  }

  @Override
  public Position createPosition(PositionDTO positionDTO) {
    Optional<Position> existPosition = positionRepository.findByName(positionDTO.getName());
    if (existPosition.isPresent()) {
      throw new RuntimeException("Position ton tai");
    } else {
      Optional<Department> department = departmentRepository.findById(Integer.parseInt(positionDTO.getDepartment()));
      Position position = Position.builder()
              .name(positionDTO.getName())
              .department(department.get())
              .status(StateStatus.STATUS_ACTIVE)
              .build();
      Position response = positionRepository.save(position);
      return response;
    }
  }

  @Override
  public Position updatePosition(PositionDTO positionDTO) {
    return null;
  }

  @Override
  public boolean deletePosition(int id) {
    return false;
  }
}
