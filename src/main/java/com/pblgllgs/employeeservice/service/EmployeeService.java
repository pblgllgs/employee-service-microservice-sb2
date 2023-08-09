package com.pblgllgs.employeeservice.service;

import com.pblgllgs.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto findEmployeeById(Long employeeId);
}
