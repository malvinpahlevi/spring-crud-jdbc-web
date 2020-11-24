package com.yanmalvin.springapp.dao;

import com.yanmalvin.springapp.model.Employee;
import com.yanmalvin.springapp.model.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String SELECT_ALL_EMPLOYEE = "SELECT * FROM employees";
    private String SELECT_BY_ID = "SELECT * FROM employees WHERE employee_id = ?";
    private String INSERT_EMPLOYEE = "INSERT INTO employees(employee_id, first_name, last_name, email, phone, job_title) VALUES(?,?,?,?,?,?)";
    private String UPDATE_EMPLOYEE_BY_ID = "UPDATE employees SET first_name=?, last_name=?, email=?, phone=?, job_title=? WHERE employee_id=?";
    private String DELETE_EMPLOYEE_BY_ID = "DELETE FROM employees WHERE employee_id=?";

    @Override
    public List<Employee> getAllEmployees() {
        String query = SELECT_ALL_EMPLOYEE;
        RowMapper<Employee> rowMapper = new EmployeeRowMapper();
        List<Employee> list = jdbcTemplate.query(query, rowMapper);

        return list;
    }

    @Override
    public Employee findEmployeeById(int id) {
        String query = SELECT_BY_ID;
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(query, rowMapper, id);

        return employee;
    }

    @Override
    public void addEmployee(Employee employee) {
        String query = INSERT_EMPLOYEE;
        jdbcTemplate.update(query,
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getJobTitle());
    }

    @Override
    public void updateEmployee(Employee employee) {
        String query = UPDATE_EMPLOYEE_BY_ID;
        jdbcTemplate.update(query,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getJobTitle(),
                employee.getEmployeeId());
    }

    @Override
    public void deleteEmployee(int id) {
        String query = DELETE_EMPLOYEE_BY_ID;
        jdbcTemplate.update(query, id);
    }
}
