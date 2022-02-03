package com.epam.rd.autotasks.springemployeecatalog.repository.extractor;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.repository.mapper.EmployeeBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeWithChainExtractor implements ResultSetExtractor<Employee> {

    @Override
    public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
        Employee employee = null;
        while (rs.next()) {
           employee = buildEmployee()
                   .id(rs.getLong("id"))
                   .fullName(rs.getString("firstname"),
                           rs.getString("lastname"),
                           rs.getString("middlename"))
                   .position(rs.getString("position"))
                   .hired(rs.getDate("hiredate"))
                   .salary(rs.getBigDecimal("salary"))
                   .department(rs.getLong("department"),
                           rs.getString("name"),
                           rs.getString("location"))
                   .manager(extractData(rs))
                   .build();
        }
        return employee;
    }

    private EmployeeBuilder buildEmployee() {
        return new EmployeeBuilder();
    }
}
