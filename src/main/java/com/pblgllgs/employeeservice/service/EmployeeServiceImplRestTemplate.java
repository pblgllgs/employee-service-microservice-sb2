package com.pblgllgs.employeeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblgllgs.employeeservice.dto.DepartmentDto;
import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.dto.OrganizationDto;
import com.pblgllgs.employeeservice.entity.ApiResponseDto;
import com.pblgllgs.employeeservice.entity.Employee;
import com.pblgllgs.employeeservice.exception.ResourceNotFoundException;
import com.pblgllgs.employeeservice.repository.EmployeeRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Qualifier("restTemplate")
public class EmployeeServiceImplRestTemplate implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;
    private static  final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImplRestTemplate.class);


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = objectMapper.convertValue(employeeDto, Employee.class);
        Employee employeeSaved = employeeRepository.save(employee);
        return objectMapper.convertValue(employeeSaved, EmployeeDto.class);
    }

    @Override
//    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry( name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    public ApiResponseDto findEmployeeById(Long employeeId) {
        LOGGER.info("inside getEmployeeById method");
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeDb =
                employeeRepository
                        .findById(employeeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        ResponseEntity<DepartmentDto> responseEntityDepartment =
                restTemplate
                        .getForEntity(
                                "http://DEPARTMENT-SERVICE/api/v1/department/"+employeeDb.getDepartmentCode(),
                                DepartmentDto.class
                        );

        ResponseEntity<OrganizationDto> responseEntityOrganization =
                restTemplate
                        .getForEntity(
                                "http://ORGANIZATION-SERVICE/api/v1/department/"+employeeDb.getOrganizationCode(),
                                OrganizationDto.class
                        );

        return new ApiResponseDto(
                objectMapper.convertValue(employeeDb, EmployeeDto.class),
                responseEntityDepartment.getBody(),
                responseEntityOrganization.getBody()
        );
    }

    public ApiResponseDto getDefaultDepartment(Long employeeId, Exception ex) {
        LOGGER.info("inside getDefaultDepartment method");
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeDb =
                employeeRepository
                        .findById(employeeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(2L);
        departmentDto.setDepartmentName("DEV");
        departmentDto.setDepartmentCode("002-DEV");
        departmentDto.setDepartmentDescription("DEV");

        OrganizationDto organizationDto =  new OrganizationDto();
        departmentDto.setId(1L);
        departmentDto.setDepartmentName("DEV");
        departmentDto.setDepartmentCode("001-DEV");
        departmentDto.setDepartmentDescription("DEV");

        return new ApiResponseDto(
                objectMapper.convertValue(employeeDb, EmployeeDto.class),
                departmentDto,
                organizationDto
        );
    }
}
