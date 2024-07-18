package com.congthanh.project.service.management;

import com.congthanh.project.dto.management.PositionDTO;
import com.congthanh.project.entity.management.Position;

public interface PositionService {

  public Object getAllPosition(Integer page, Integer limit);

  public Position createPosition(PositionDTO positionDTO);

  public Position updatePosition(PositionDTO positionDTO);

  public boolean deletePosition(int id);
}
