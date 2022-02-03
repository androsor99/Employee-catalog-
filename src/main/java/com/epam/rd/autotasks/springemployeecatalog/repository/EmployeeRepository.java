package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository {

    List<Employee> findAll(Long page, Integer size, String sortField);

    List<Employee> findById(Long id);

    Employee findByIdWithChain(Long id);

    List<Employee> findByManagerId(Long id, Long page, Integer size, String sortField);

    List<Employee> findByDepartmentId(Long id, Long page, Integer size, String sortField);

    List<Employee> findByDepartmentName(String name, long page, int size, String sortField);
}
