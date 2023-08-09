package com.pblgllgs.employeeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.entity.Employee;
import com.pblgllgs.employeeservice.exception.ResourceNotFoundException;
import com.pblgllgs.employeeservice.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = objectMapper.convertValue(employeeDto, Employee.class);
        Employee employeeSaved = employeeRepository.save(employee);
        return objectMapper.convertValue(employeeSaved, EmployeeDto.class);
    }

    @Override
    public EmployeeDto findEmployeeById(Long employeeId) {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeDb =
                employeeRepository
                        .findById(employeeId)
                        .orElseThrow( () -> new ResourceNotFoundException("Employee","id",employeeId));
        return objectMapper.convertValue(employeeDb,EmployeeDto.class);
    }
}
