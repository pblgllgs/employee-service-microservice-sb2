package com.pblgllgs.employeeservice.service;

import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.entity.ApiResponseDto;

import java.sql.SQLIntegrityConstraintViolationException;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto) throws SQLIntegrityConstraintViolationException;

    ApiResponseDto findEmployeeById(Long employeeId);
}
