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
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
@Qualifier("webClient")
public class EmployeeServiceImplWebClient implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImplWebClient.class);
    private final EmployeeRepository employeeRepository;
    private final WebClient webClient;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = objectMapper.convertValue(employeeDto, Employee.class);
        Employee employeeSaved = employeeRepository.save(employee);
        return objectMapper.convertValue(employeeSaved, EmployeeDto.class);
    }

    @Override
//    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    public ApiResponseDto findEmployeeById(Long employeeId) {
        LOGGER.info("inside getEmployeeById method webclient");
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeDb =
                employeeRepository
                        .findById(employeeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/v1/department/" + employeeDb.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        OrganizationDto organizationDto = webClient.get()
                .uri("http://localhost:8082/api/v1/organization/" + employeeDb.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();

        return new ApiResponseDto(
                objectMapper.convertValue(employeeDb, EmployeeDto.class),
                departmentDto,
                organizationDto
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
