/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.domain.repository.impl;

import com.mycompany.entapp.snowman.domain.EmployeeTestHelper;
import com.mycompany.entapp.snowman.domain.model.Employee;
import com.mycompany.entapp.snowman.domain.repository.EmployeeRepository;
import com.mycompany.entapp.snowman.infrastructure.db.dao.EmployeeDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeRepositoryImplUTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

    @Test
    public void testGetEmployee() {
        final int employeeId = 1;
        Employee expectedEmployee = EmployeeTestHelper.getEmployee();

        Mockito.when(employeeDao.retrieveEmployee(employeeId)).thenReturn(expectedEmployee);

        Employee actualEmployee = employeeRepository.findEmployee(employeeId);

        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = EmployeeTestHelper.getEmployee();

        Mockito.doNothing().when(employeeDao).saveEmployee(employee);

        employeeRepository.saveEmployee(employee);

        Mockito.verify(employeeDao, Mockito.times(1)).saveEmployee(employee);
    }

    @Test
    public void testDeleteEmployee() {
        final int employeeId = 1;

        Mockito.doNothing().when(employeeDao).deleteEmployee(employeeId);

        employeeRepository.removeEmployee(employeeId);

        Mockito.verify(employeeDao, Mockito.times(1)).deleteEmployee(employeeId);
    }
}
