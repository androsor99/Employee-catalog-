package com.epam.rd.autotasks.springemployeecatalog.repository.mapper;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static java.sql.Types.NULL;

public class EmployeeBuilder {
    private Long id;
    private FullName fullName;
    private Position position;
    private LocalDate hired;
    private BigDecimal salary;
    private Employee manager;
    private Department department;

    public EmployeeBuilder() {
        // TODO document why this constructor is empty
    }

    public EmployeeBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder fullName(String first, String second, String middle) {
        this.fullName = new FullName(first, second, middle);
        return this;
    }

    public EmployeeBuilder position(String position) {
        this.position = Position.valueOf(position);
        return this;
    }

    public EmployeeBuilder hired(Date hired) {
        this.hired = hired.toLocalDate();
        return this;
    }

    public EmployeeBuilder salary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public EmployeeBuilder manager(Employee manager) {
        this.manager = manager;
        return this;
    }

    public EmployeeBuilder department(Long id, String name, String location) {
        if (id == NULL) this.department = null;
        else {
            this.department = new Department(id, name, location) ;
        }
        return this;
    }

    public Employee build() {
        return new Employee(id, fullName, position, hired, salary, manager, department);
    }
}
