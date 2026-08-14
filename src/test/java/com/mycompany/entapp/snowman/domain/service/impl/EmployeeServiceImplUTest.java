/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.domain.service.impl;

import com.mycompany.entapp.snowman.domain.EmployeeTestHelper;
import com.mycompany.entapp.snowman.domain.model.Employee;
import com.mycompany.entapp.snowman.domain.repository.EmployeeRepository;
import com.mycompany.entapp.snowman.domain.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplUTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService = new EmployeeServiceImpl();

    @Test
    public void testGetEmployee() {
        final int employeeId = 1;
        Employee employee = EmployeeTestHelper.getEmployee();

        Mockito.when(employeeRepository.findEmployee(employeeId)).thenReturn(employee);

        Employee actualEmployee = employeeService.getEmployee(employeeId);

        assertEquals(employee, actualEmployee);
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = EmployeeTestHelper.getEmployee();

        Mockito.doNothing().when(employeeRepository).saveEmployee(employee);

        employeeService.createEmployee(employee);

        Mockito.verify(employeeRepository, Mockito.times(1)).saveEmployee(employee);
    }

    @Test
    public void testDeleteEmployee() {
        final int employeeId = 1;

        Mockito.doNothing().when(employeeRepository).removeEmployee(employeeId);

        employeeService.deleteEmployee(employeeId);

        Mockito.verify(employeeRepository, Mockito.times(1)).removeEmployee(employeeId);
    }
}
