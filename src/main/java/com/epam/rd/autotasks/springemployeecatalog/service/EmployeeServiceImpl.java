package com.epam.rd.autotasks.springemployeecatalog.service;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.repository.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    public static final String REGEX_NUMBER = "\\p{N}*";

    private final EmployeeRepositoryImpl employeeRepositoryImpl;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepositoryImpl employeeRepositoryImpl) {
        this.employeeRepositoryImpl = employeeRepositoryImpl;
    }

    @Override
    public List<Employee> getAll(Pageable pageable) {
        long page = pageable.getOffset();
        int size = pageable.getPageSize();
        String order = getOrder(pageable);
        String sort = Sorter.find(order).orElse("id");
        return employeeRepositoryImpl.findAll(page, size, sort);
    }

    @Override
    public List<Employee> getEmployeesByManagerId(Long id, Pageable pageable) {
        long page = pageable.getOffset();
        int size = pageable.getPageSize();
        String order = getOrder(pageable);
        String sort = Sorter.find(order).orElse("id");
        return employeeRepositoryImpl.findByManagerId(id, page, size, sort);
    }

    @Override
    public List<Employee> getEmployeesByDepIdOrDepName(String value, Pageable pageable) {
        long page = pageable.getOffset();
        int size = pageable.getPageSize();
        String order = getOrder(pageable);
        String sort = Sorter.find(order).orElse("id");
        if (value.matches(REGEX_NUMBER)) {
            return employeeRepositoryImpl.findByDepartmentId(Long.parseLong(value), page, size, sort);
        } else {
            return employeeRepositoryImpl.findByDepartmentName(value.toUpperCase(Locale.ROOT), page, size, sort);
        }
    }

    @Override
    public Employee getEmployeeById(String id, boolean fullChain) {
        if (fullChain) {
            return employeeRepositoryImpl.findByIdWithChain(Long.parseLong(id));
        } else {
            List<Employee> employees = employeeRepositoryImpl.findById(Long.parseLong(id));
            if (employees.isEmpty()) return null;
            else return employees.get(0);
        }
    }

    private String getOrder(Pageable pageable) {
        return !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0).getProperty() : "";
    }
}

