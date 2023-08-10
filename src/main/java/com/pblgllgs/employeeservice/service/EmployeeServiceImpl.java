package com.pblgllgs.employeeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblgllgs.employeeservice.client.DepartmentClient;
import com.pblgllgs.employeeservice.dto.DepartmentDto;
import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.entity.ApiResponseDto;
import com.pblgllgs.employeeservice.entity.Employee;
import com.pblgllgs.employeeservice.exception.ResourceNotFoundException;
import com.pblgllgs.employeeservice.repository.EmployeeRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    //    private final RestTemplate restTemplate;
//    private final WebClient webClient;
    private final DepartmentClient departmentClient;

    private static  final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);


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
                        .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
//        RestTemplate
//        ResponseEntity<DepartmentDto> responseEntity =
//                restTemplate
//                        .getForEntity(
//                                "http://DEPARTMENT-SERVICE/api/v1/department/"+employeeDb.getDepartmentCode(),
//                                DepartmentDto.class
//                        );

//        WebClient
//        DepartmentDto departmentDto = webClient.get()
//                .uri("http://localhost:8080/api/v1/department/" + employeeDb.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();

        return new ApiResponseDto(
                objectMapper.convertValue(employeeDb, EmployeeDto.class),
                departmentClient.findDepartmentById(employeeDb.getDepartmentCode()).getBody()
        );
    }

    public ApiResponseDto getDefaultDepartment(Long employeeId, Exception ex) {
        LOGGER.info("inside getDefaultDepartment method");
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeDb =
                employeeRepository
                        .findById(employeeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(2L);
        departmentDto.setDepartmentName("DEV");
        departmentDto.setDepartmentCode("002-DEV");
        departmentDto.setDepartmentDescription("DEV");

        return new ApiResponseDto(
                objectMapper.convertValue(employeeDb, EmployeeDto.class),
                departmentDto
        );
    }
}
