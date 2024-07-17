package com.congthanh.project.controller.management;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.management.PositionDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.entity.management.Position;
import com.congthanh.project.service.management.PositionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management/position")
@Tag(name = "Position API", description = "Position API in CongThanhApp - Management")
public class PositionController {

  @Autowired
  private PositionService positionService;

  @GetMapping("/getAll")
  @PermitAll
  public ResponseEntity<Response<Object>> getAllPosition(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
    Object data = positionService.getAllPosition(page, limit);
    Response<Object> response = new Response<>();
    response.setData(data);
    response.setMessage("Get all successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/create")
  @PermitAll
  public ResponseEntity<Response> createPosition(@RequestBody PositionDTO positionDTO) {
    Position position = positionService.createPosition(positionDTO);
    Response<Object> response = new Response<>();
    response.setData(position);
    response.setMessage("Create successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }
}
