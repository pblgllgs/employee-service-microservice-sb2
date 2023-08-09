package com.pblgllgs.employeeservice.entity;

import com.pblgllgs.employeeservice.dto.DepartmentDto;
import com.pblgllgs.employeeservice.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto {
    private EmployeeDto employeeDto;
    private DepartmentDto departmentDto;
}
