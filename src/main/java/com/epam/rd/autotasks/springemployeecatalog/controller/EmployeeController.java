package com.epam.rd.autotasks.springemployeecatalog.controller;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployees(@PageableDefault(size = 50) Pageable pageable) {
        return employeeServiceImpl.getAll(pageable);
    }

    @GetMapping(value = "/by_manager/{managerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployeesByManagerId(@PathVariable Long managerId,
                                                  @PageableDefault(size = 50) Pageable pageable) {
        return employeeServiceImpl.getEmployeesByManagerId(managerId, pageable);
    }

    @GetMapping(value = "/by_department/{value}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployeesByDepIdOrDepName(@PathVariable String value,
                                                       @PageableDefault(size = 50) Pageable pageable) {
        return employeeServiceImpl.getEmployeesByDepIdOrDepName(value, pageable);
    }

    @GetMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployee(@PathVariable String employeeId,
                                          @RequestParam(required = false) boolean full_chain) {
        return employeeServiceImpl.getEmployeeById(employeeId, full_chain);
    }
}
