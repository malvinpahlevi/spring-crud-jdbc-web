package com.yanmalvin.springapp.controller;

import com.yanmalvin.springapp.model.Employee;
import com.yanmalvin.springapp.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public ModelAndView getAllEmployee(){
        ModelAndView model = new ModelAndView();
        List<Employee> list = employeeService.getAllEmployees();

        model.addObject("employee_list", list);
        model.setViewName("employee_list");

        return model;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView editEmployee(@PathVariable int id){
        ModelAndView model = new ModelAndView();

        Employee employee = employeeService.findEmployeeById(id);
        model.addObject("employeeForm", employee);

        model.setViewName("employee_form");
        return model;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addEmployee(){
        ModelAndView model = new ModelAndView();

        Employee employee = new Employee();
        model.addObject("employeeForm", employee);

        model.setViewName("employee_form");
        return model;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveOrUpdate(@ModelAttribute("employeeForm") Employee employee){
        if (employee.getEmployeeId() != null) {
            employeeService.updateEmployee(employee);
        } else {
            employeeService.addEmployee(employee);
        }

        return new ModelAndView("redirect:/employee/list");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteEmployee(@PathVariable("id") int id){
        employeeService.deleteEmployee(id);

        return new ModelAndView("redirect:/employee/list");
    }
}
