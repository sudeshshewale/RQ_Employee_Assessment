package com.example.rqchallenge.employees.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.rqchallenge.constants.EmployeeConstants;
import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.service.IEmployeeService;
import com.example.rqchallenge.exceptionhandler.EmployeeInputException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EmployeeControllerImpl implements IEmployeeController {

	private static final Logger log = LoggerFactory.getLogger(EmployeeControllerImpl.class);

	@Autowired
	IEmployeeService iEmployeeService;

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
		List<Employee> listEmployees = iEmployeeService.getAllEmployees();
		ResponseEntity<List<Employee>> response = null;
		log.info(EmployeeConstants.INFO_EMPLOYEE_LIST_SIZE, listEmployees.size());
		if (listEmployees.size() > 0) {
			response = new ResponseEntity<>(listEmployees, HttpStatus.FOUND);
		} else {
			log.error(EmployeeConstants.ERROR_NO_LIST);
			response = new ResponseEntity<>(listEmployees, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) throws IOException {
		List<Employee> listEmployees = iEmployeeService.getEmployeesByNameSearch(searchString);
		ResponseEntity<List<Employee>> response = null;
		log.info(EmployeeConstants.INFO_EMPLOYEE_LIST_SIZE, listEmployees.size());
		if (listEmployees.size() > 0) {
			response = new ResponseEntity<>(listEmployees, HttpStatus.FOUND);
		} else {
			log.error(EmployeeConstants.ERROR_NO_LIST);
			response = new ResponseEntity<>(listEmployees, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) throws IOException {
		Employee employee = iEmployeeService.getEmployeeById(id);		
		ResponseEntity<Employee> response = null;
		if (employee!=null) {
			response = new ResponseEntity<>(employee, HttpStatus.FOUND);
		} else {
			log.error(EmployeeConstants.ERROR_NO_LIST);
			response = new ResponseEntity<>(employee, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() throws IOException {
		Integer higestSalary = iEmployeeService.getHighestSalaryOfEmployees();
		log.info(EmployeeConstants.INFO_HIGEST_SALARY, higestSalary);
		ResponseEntity<Integer> response = new ResponseEntity<>(higestSalary, HttpStatus.OK);
		return response;
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() throws IOException {
		List<String> listEmployees = iEmployeeService.getTopTenHighestEarningEmployeeNames();
		ResponseEntity<List<String>> response = null;
		if (listEmployees.size() > 0) {
			response = new ResponseEntity<>(listEmployees, HttpStatus.FOUND);
		} else {
			log.error(EmployeeConstants.ERROR_NO_LIST);
			response = new ResponseEntity<>(listEmployees, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@Override
	public ResponseEntity<String> createEmployee(Map<String, Object> employeeInput) throws IOException {
		Employee newEmployee = new Employee();		
		try {
			if (null != employeeInput.get("employee_name"))
				newEmployee.setEmployee_name(employeeInput.get("employee_name").toString());
			if (null != employeeInput.get("employee_age"))
				newEmployee.setEmployee_age(Integer.parseInt(employeeInput.get("employee_age").toString()));
			if (null != employeeInput.get("employee_salary"))
				newEmployee.setEmployee_salary(Integer.parseInt(employeeInput.get("employee_salary").toString()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new EmployeeInputException(EmployeeConstants.ERROR_EMPLOYEE_INPUT_MESSAGE);
		}

		String status = iEmployeeService.createEmployee(newEmployee);

		ResponseEntity<String> response = null;
		if (null!=status && status.equalsIgnoreCase("success")) {
			log.info(EmployeeConstants.INFO_CREATED);
			response = new ResponseEntity<>(status, HttpStatus.CREATED);
		} else {
			log.error(EmployeeConstants.ERROR_CREATE_FAILED);
			response = new ResponseEntity<>("failed", HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) throws IOException {
		String status = iEmployeeService.deleteEmployeeById(id);
		ResponseEntity<String> response = null;
		if (status != null) {
			log.info(EmployeeConstants.INFO_DELETED);
			response = new ResponseEntity<>(status, HttpStatus.OK);
		} else {
			log.error(EmployeeConstants.ERROR_DELETE_FAILED);
			response = new ResponseEntity<>(EmployeeConstants.ERROR_DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}

}
