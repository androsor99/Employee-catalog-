package com.epam.rd.autotasks.springemployeecatalog.repository.mapper;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

@Component
public class EmployeeMapper {

    public Employee mapEmployee(ResultSet rs) throws SQLException {
        return manageEmployee(rs, "employee");
    }

    private Employee manageEmployee(ResultSet rs, String prefix) throws SQLException {
        if (rs.getLong(prefix + "_id") == NULL) {
            return null;
        }
        return buildEmployee()
                .id(rs.getLong(prefix + "_id"))
                .fullName(rs.getString(prefix + "_first_Name"),
                        rs.getString(prefix + "_last_name"),
                        rs.getString(prefix + "_middle_name"))
                .position(rs.getString(prefix + "_position"))
                .hired(rs.getDate(prefix + "_hire_date"))
                .salary(rs.getBigDecimal(prefix + "_salary"))
                .department(rs.getLong(prefix + "_department_id"),
                        rs.getString(prefix + "_department_name"),
                        rs.getString(prefix + "_department_location"))
                .manager(prefix.equals("manager") ? null : manageEmployee(rs, "manager"))
                .build();
    }

    private EmployeeBuilder buildEmployee() {
        return new EmployeeBuilder();
    }
}
