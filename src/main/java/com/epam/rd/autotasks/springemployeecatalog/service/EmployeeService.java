package com.epam.rd.autotasks.springemployeecatalog.service;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    List<Employee> getAll(Pageable pageable);

    List<Employee> getEmployeesByManagerId(Long id, Pageable pageable);

    List<Employee> getEmployeesByDepIdOrDepName(String value, Pageable pageable);

    Employee getEmployeeById(String id, boolean fullChain);
}
