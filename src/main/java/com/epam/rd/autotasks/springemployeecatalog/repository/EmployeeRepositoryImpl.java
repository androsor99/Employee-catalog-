package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.repository.extractor.EmployeeExtractor;
import com.epam.rd.autotasks.springemployeecatalog.repository.extractor.EmployeeWithChainExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final String SELECT_ALL_SQL =
            "SELECT e.id          AS employee_id, " +
                    "e.firstName  AS employee_first_name, " +
                    "e.lastName   AS employee_last_name, " +
                    "e.middleName AS employee_middle_name, " +
                    "e.position   AS employee_position, " +
                    "e.manager    AS manager_id, " +
                    "e.hireDate   AS employee_hire_date, " +
                    "e.salary     AS employee_salary, " +
                    "e.department AS employee_department_id, "  +
                    "d.name       AS employee_department_name, " +
                    "d.location   AS employee_department_location, " +
                    "m.firstName  AS manager_first_Name, " +
                    "m.lastName   AS manager_last_name, " +
                    "m.middleName AS manager_middle_name, " +
                    "m.position   AS manager_position, " +
                    "m.hireDate   AS manager_hire_date, " +
                    "m.salary     AS manager_salary, " +
                    "m.department AS manager_department_id, "  +
                    "m_d.name     AS manager_department_name, " +
                    "m_d.location AS manager_department_location " +
            "FROM employee e " +
                    "LEFT JOIN employee m     ON e.manager = m.id " +
                    "LEFT JOIN department d   ON e.department = d.id " +
                    "LEFT JOIN department m_d ON m.department = m_d.id ";

    private static final String SELECT_BY_ID_WITH_CHAIN_SQL =
            "WITH RECURSIVE employees(id, firstname, lastname, middlename, position, manager, hiredate, salary, department, name, location) AS (" +
                "SELECT e.id, firstname, lastname, middlename, position, manager, hiredate, salary, department, name, location " +
                "FROM employee e " +
                        "LEFT JOIN department AS d ON department = d.id " +
                "WHERE e.id = ? " +
                "UNION ALL " +
                "SELECT e1.id, e1.firstname, e1.lastname, e1.middlename, e1.position, e1.manager, e1.hiredate, e1.salary, e1.department, d1.name, d1.location " +
                "FROM employee AS e1 " +
                        "JOIN employees AS m ON e1.id = m.manager " +
                        "LEFT JOIN department AS d1 ON e1.department = d1.id " +
            ") " +
            "SELECT * " +
                    "FROM employees ;";

    private static final String PAGING_SQL =
            "ORDER BY e.%s " +
            "LIMIT %d OFFSET %d ";

    private static final String SELECT_BY_ID_SQL = SELECT_ALL_SQL + "WHERE e.id = ? ";
    private static final String SELECT_AND_PAGING_ALL_SQL = SELECT_ALL_SQL + PAGING_SQL;
    private static final String SELECT_BY_MANAGER_ID_SQL = SELECT_ALL_SQL + "WHERE e.manager = ? " +  PAGING_SQL;
    private static final String SELECT_BY_DEPARTMENT_ID_SQL = SELECT_ALL_SQL + "WHERE e.department = ? " +  PAGING_SQL;
    private static final String SELECT_BY_DEPARTMENT_NAME_SQL = SELECT_ALL_SQL + "WHERE d.name = ? " +  PAGING_SQL;

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeExtractor employeeExtractor;
    private final EmployeeWithChainExtractor employeeWithChainExtractor;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate, EmployeeExtractor employeeExtractor, EmployeeWithChainExtractor employeeWithChainExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeExtractor = employeeExtractor;
        this.employeeWithChainExtractor = employeeWithChainExtractor;
    }

    @Override
    public List<Employee> findAll(Long page, Integer size, String sortField) {
        String formatQuery = String.format(SELECT_AND_PAGING_ALL_SQL, sortField, size, page);
        return jdbcTemplate.query(formatQuery, employeeExtractor);
    }

    @Override
    public List<Employee> findById(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL, new Object[]{id}, employeeExtractor);
    }

    @Override
    public Employee findByIdWithChain(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID_WITH_CHAIN_SQL, new Object[]{id}, employeeWithChainExtractor);
    }

    @Override
    public List<Employee> findByManagerId(Long id, Long page, Integer size, String sortField) {
        String formatQuery = String.format(SELECT_BY_MANAGER_ID_SQL, sortField, size, page);
        return jdbcTemplate.query(formatQuery, new Object[]{id}, employeeExtractor);
    }

    @Override
    public List<Employee> findByDepartmentId(Long id, Long page, Integer size, String sortField) {
        String formatQuery = String.format(SELECT_BY_DEPARTMENT_ID_SQL, sortField, size, page);
        return jdbcTemplate.query(formatQuery, new Object[]{id}, employeeExtractor);
    }

    @Override
    public List<Employee> findByDepartmentName(String name, long page, int size, String sortField) {
        String formatQuery = String.format(SELECT_BY_DEPARTMENT_NAME_SQL, sortField, size, page);
        return jdbcTemplate.query(formatQuery, new Object[]{name}, employeeExtractor);
    }
}
