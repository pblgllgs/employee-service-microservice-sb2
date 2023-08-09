package com.pblgllgs.employeeservice.repository;

import com.pblgllgs.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
