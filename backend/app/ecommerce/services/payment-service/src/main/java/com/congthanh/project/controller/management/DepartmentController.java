package com.congthanh.project.controller.management;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.management.DepartmentDTO;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.entity.management.Department;
import com.congthanh.project.service.management.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management/department")
@Tag(name = "Department API", description = "Department API in CongThanhApp - Management")
public class DepartmentController {

  @Autowired
  private DepartmentService departmentService;

  @GetMapping("/getAll")
  @PermitAll
//    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
  public ResponseEntity<Response<Object>> getAllDepartment(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
    Object data = departmentService.getAllDepartment(page, limit);
    Response<Object> response = new Response<>();
    response.setData(data);
    response.setMessage("Get all successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  //
//    @GetMapping("getById/{id}")
//    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
//    public ResponseEntity<ResponseDTO> getDepartmentById(@RequestParam("id") int id) {
//        ResponseDTO<DepartmentResponse> responseDTO = new ResponseDTO();
//        DepartmentResponse departmentResponse = departmentService.getDepartmentById(id);
//        responseDTO.setData(departmentResponse);
//        responseDTO.setMessage(DepartmentSuccessMessage.GET_DEPARTMENT_BY_ID);
//        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
//        return ResponseEntity.ok().body(responseDTO);
//    }
//
//    @GetMapping("getByName")
//    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
//    public ResponseEntity<ResponseDTO> getDepartmentByName(
//            @RequestParam("name") String name,
//            @RequestParam(defaultValue = "0") int pageNo,
//            @RequestParam(defaultValue = "10") int pageSize) {
//        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
//        ResponseWithTotalPage<DepartmentResponse> list = departmentService.getDepartmentByName(name, pageNo, pageSize);
//        response.setData(list);
//        response.setMessage(DepartmentSuccessMessage.GET_DEPARTMENT_BY_NAME);
//        response.setStatus(ResponseStatusDTO.SUCCESS);
//        return ResponseEntity.ok().body(response);
//    }
//    @GetMapping("/getIdName")
//    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
//    public ResponseEntity<ResponseDTO> getDepartmentIdAndName() {
//        ResponseDTO response = new ResponseDTO();
//        response.setData(departmentService.getDepartmentName());
//        response.setMessage(DepartmentSuccessMessage.GET_DEPARTMENT_ID_NAME);
//        response.setStatus(ResponseStatusDTO.SUCCESS);
//        return ResponseEntity.ok().body(response);
//    }
//
//    @PutMapping("edit/{id}")
//    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
//    public ResponseEntity<ResponseDTO> updateDepartment(@RequestParam("id") int id,
//                                                        @RequestBody DepartmentUpdateDTO updateDTO) {
//        ResponseDTO<DepartmentResponse> responseDTO = new ResponseDTO();
//        DepartmentResponse department = departmentService.updateDepartment(id, updateDTO);
//        responseDTO.setData(department);
//        responseDTO.setMessage(DepartmentSuccessMessage.UPDATE_DEPARTMENT_SUCCESS);
//        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
//        return ResponseEntity.ok().body(responseDTO);
//    }
//
  @PostMapping("/create")
  @PermitAll
//    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
  public ResponseEntity<Response> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
    Response<Department> response = new Response();
    Department department = departmentService.createDepartment(departmentDTO);
    response.setData(department);
    response.setMessage("Create successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }
//
//    @DeleteMapping("delete/{id}")
//    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
//    public ResponseEntity deleteDepartmnetById(@RequestParam("id") int id) {
//        ResponseDTO<Department> responseDTO = new ResponseDTO();
//        departmentService.deleteDepartmentById(id);
//        responseDTO.setMessage(DepartmentSuccessMessage.DELETE_DEPARTMENT_SUCCESS);
//        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
//        return ResponseEntity.ok().body(responseDTO);
//    }
//    @PatchMapping("/active/{id}")
//    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
//    public ResponseEntity activeDepartmnetById(@RequestParam("id") int id) {
//        ResponseDTO<Department> responseDTO = new ResponseDTO();
//        departmentService.activeDepartmentById(id);
//        responseDTO.setMessage(DepartmentSuccessMessage.ACTIVE_DEPARTMENT_SUCCESS);
//        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
//        return ResponseEntity.ok().body(responseDTO);
//    }
//
}
