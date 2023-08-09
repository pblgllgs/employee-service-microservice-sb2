package com.pblgllgs.employeeservice.controller;

import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable("employeeId") Long id) {
        return new ResponseEntity<>(employeeService.findEmployeeById(id), HttpStatus.OK);
    }

}
