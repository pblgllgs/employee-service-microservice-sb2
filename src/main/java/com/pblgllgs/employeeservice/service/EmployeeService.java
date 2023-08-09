package com.pblgllgs.employeeservice.service;

import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.entity.ApiResponseDto;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    ApiResponseDto findEmployeeById(Long employeeId);
}
