package com.pblgllgs.employeeservice.client;

import com.pblgllgs.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("DEPARTMENT-SERVICE")
public interface DepartmentClient {

    @GetMapping("/api/v1/department/{departmentCode}")
    ResponseEntity<DepartmentDto> findDepartmentById(@PathVariable("departmentCode") String code);
}
