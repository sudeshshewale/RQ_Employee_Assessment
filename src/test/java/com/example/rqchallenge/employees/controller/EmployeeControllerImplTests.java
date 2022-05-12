package com.example.rqchallenge.employees.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.service.IEmployeeService;

@WebMvcTest(EmployeeControllerImpl.class)
public class EmployeeControllerImplTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IEmployeeService service;

	@Test
	public void getAllEmployeesFound() throws Exception {
		List<Employee> list = new ArrayList<>();

		Employee emp1 = new Employee();
		emp1.setId(1);
		emp1.setEmployee_name("Tiger Nixon");
		emp1.setEmployee_age(61);
		emp1.setEmployee_salary(320800);
		emp1.setProfile_image("");

		Employee emp2 = new Employee();
		emp2.setId(2);
		emp2.setEmployee_name("Garrett Winters");
		emp2.setEmployee_age(63);
		emp2.setEmployee_salary(170750);
		emp2.setProfile_image("");

		Employee emp3 = new Employee();
		emp3.setId(3);
		emp3.setEmployee_name("Ashton Cox");
		emp3.setEmployee_age(66);
		emp3.setEmployee_salary(86000);
		emp3.setProfile_image("");

		Employee emp4 = new Employee();
		emp4.setId(4);
		emp4.setEmployee_name("Cedric Kelly");
		emp4.setEmployee_age(22);
		emp4.setEmployee_salary(433060);
		emp4.setProfile_image("");

		Employee emp5 = new Employee();
		emp5.setId(5);
		emp5.setEmployee_name("Airi Satou");
		emp5.setEmployee_age(33);
		emp5.setEmployee_salary(162700);
		emp5.setProfile_image("");

		list.add(emp1);
		list.add(emp2);
		list.add(emp3);
		list.add(emp4);
		list.add(emp5);

		when(service.getAllEmployees()).thenReturn(list);
		this.mockMvc.perform(get("/")).andExpect(status().isFound());
	}

	@Test
	public void getAllEmployeesNotFound() throws Exception {
		List<Employee> list = new ArrayList<>();

		when(service.getAllEmployees()).thenReturn(list);
		this.mockMvc.perform(get("/")).andExpect(status().isNotFound());
	}

	@Test
	public void getEmployeesByNameSearchFound() throws Exception {
		List<Employee> list = new ArrayList<>();

		Employee emp1 = new Employee();
		emp1.setId(1);
		emp1.setEmployee_name("Tiger Nixon");
		emp1.setEmployee_age(61);
		emp1.setEmployee_salary(320800);
		emp1.setProfile_image("");

		Employee emp2 = new Employee();
		emp2.setId(2);
		emp2.setEmployee_name("Garrett Winters");
		emp2.setEmployee_age(63);
		emp2.setEmployee_salary(170750);
		emp2.setProfile_image("");

		Employee emp3 = new Employee();
		emp3.setId(3);
		emp3.setEmployee_name("Ashton Cox");
		emp3.setEmployee_age(66);
		emp3.setEmployee_salary(86000);
		emp3.setProfile_image("");

		Employee emp4 = new Employee();
		emp4.setId(4);
		emp4.setEmployee_name("Cedric Kelly");
		emp4.setEmployee_age(22);
		emp4.setEmployee_salary(433060);
		emp4.setProfile_image("");

		Employee emp5 = new Employee();
		emp5.setId(5);
		emp5.setEmployee_name("Airi Satou");
		emp5.setEmployee_age(33);
		emp5.setEmployee_salary(162700);
		emp5.setProfile_image("");

		list.add(emp1);
		list.add(emp2);
		list.add(emp3);
		list.add(emp4);
		list.add(emp5);

		when(service.getEmployeesByNameSearch("Ashton")).thenReturn(list);
		this.mockMvc.perform(get("/search/Ashton")).andExpect(status().isFound());
	}

	@Test
	public void getEmployeesByNameSearchNotFound() throws Exception {
		List<Employee> list = new ArrayList<>();

		when(service.getEmployeesByNameSearch("Berry")).thenReturn(list);
		this.mockMvc.perform(get("/search/Berry")).andExpect(status().isNotFound());
	}

	@Test
	public void getEmployeeByIdFound() throws Exception {
		Employee emp5 = new Employee();
		emp5.setId(5);
		emp5.setEmployee_name("Airi Satou");
		emp5.setEmployee_age(33);
		emp5.setEmployee_salary(162700);
		emp5.setProfile_image("");

		when(service.getEmployeeById("5")).thenReturn(emp5);
		this.mockMvc.perform(get("/5")).andExpect(status().isFound());
	}

	@Test
	public void getEmployeeByIdNotFound() throws Exception {

		when(service.getEmployeeById("23")).thenReturn(null);
		this.mockMvc.perform(get("/23")).andExpect(status().isNotFound());
	}

	@Test
	public void getHighestSalaryOfEmployees() throws Exception {

		when(service.getHighestSalaryOfEmployees()).thenReturn(433060);
		this.mockMvc.perform(get("/highestSalary")).andExpect(status().isOk());
	}

	@Test
	public void getTopTenHighestEarningEmployeeNamesFound() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("Tiger Nixon");
		list.add("Garrett Winters");
		list.add("Ashton Cox");
		list.add("Cedric Kelly");
		list.add("Airi Satou");
		list.add("Tiger Nixon2");
		list.add("Garrett Winters2");
		list.add("Ashton Cox2");
		list.add("Cedric Kelly2");
		list.add("Airi Satou2");

		when(service.getTopTenHighestEarningEmployeeNames()).thenReturn(list);
		this.mockMvc.perform(get("/topTenHighestEarningEmployeeNames")).andExpect(status().isFound());
	}

	@Test
	public void getTopTenHighestEarningEmployeeNamesNone() throws Exception {
		List<String> list = new ArrayList<>();

		when(service.getTopTenHighestEarningEmployeeNames()).thenReturn(list);
		this.mockMvc.perform(get("/topTenHighestEarningEmployeeNames")).andExpect(status().isNotFound());
	}

	@Test
	public void createEmployeeFailed() throws Exception {
		when(service.createEmployee(new Employee())).thenReturn("success");
		mockMvc.perform(MockMvcRequestBuilders.post("/").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isExpectationFailed());
	}

	@Test
	public void deleteEmployeeById() throws Exception {

		when(service.deleteEmployeeById("5")).thenReturn("Airi Satou");
		this.mockMvc.perform(delete("/5")).andExpect(status().isOk());
	}

	@Test
	public void deleteEmployeeByIdFailed() throws Exception {
		when(service.deleteEmployeeById("51")).thenReturn(null);
		this.mockMvc.perform(delete("/51")).andExpect(status().isExpectationFailed());
	}

}