/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.rest.endpoint;

import com.mycompany.entapp.snowman.domain.EmployeeTestHelper;
import com.mycompany.entapp.snowman.domain.model.Employee;
import com.mycompany.entapp.snowman.domain.service.EmployeeService;
import com.mycompany.entapp.snowman.infrastructure.rest.mappers.EmployeeResourceMapper;
import com.mycompany.entapp.snowman.infrastructure.rest.resources.EmployeeResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeRestEndpointUTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeRestEndpoint employeeRestEndpoint = new EmployeeRestEndpoint();

    @Test
    public void testGetEmployee() {
        final int employeeId = 1;
        Employee employee = EmployeeTestHelper.getEmployee();

        EmployeeResource employeeResource = new EmployeeResource();
        employeeResource.setEmployeeId(1);
        employeeResource.setFirstName("Firstname");
        employeeResource.setSecondName("SecondName");
        employeeResource.setRole("Role");

        Mockito.when(employeeService.getEmployee(employeeId)).thenReturn(employee);

        try (MockedStatic<EmployeeResourceMapper> mocked = Mockito.mockStatic(EmployeeResourceMapper.class)) {
            mocked.when(() -> EmployeeResourceMapper.mapEmployeeToEmployeeResource(employee)).thenReturn(employeeResource);

            ResponseEntity<EmployeeResource> responseEntity = employeeRestEndpoint.getEmployee(employeeId);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(employeeResource, responseEntity.getBody());
        }
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = EmployeeTestHelper.getEmployee();

        EmployeeResource employeeResource = new EmployeeResource();
        employeeResource.setEmployeeId(1);
        employeeResource.setFirstName("Firstname");
        employeeResource.setSecondName("SecondName");
        employeeResource.setRole("Role");

        try (MockedStatic<EmployeeResourceMapper> mocked = Mockito.mockStatic(EmployeeResourceMapper.class)) {
            mocked.when(() -> EmployeeResourceMapper.mapEmployeeResourceToEmployee(employeeResource)).thenReturn(employee);

            ResponseEntity responseEntity = employeeRestEndpoint.createEmployee(employeeResource);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Mockito.verify(employeeService, Mockito.times(1)).createEmployee(employee);
        }
    }

    @Test
    public void testUpdateExistingEmployee() {
        Employee employee = EmployeeTestHelper.getEmployee();

        EmployeeResource employeeResource = new EmployeeResource();
        employeeResource.setEmployeeId(1);
        employeeResource.setFirstName("Firstname");
        employeeResource.setSecondName("SecondName");
        employeeResource.setRole("Role");

        try (MockedStatic<EmployeeResourceMapper> mocked = Mockito.mockStatic(EmployeeResourceMapper.class)) {
            mocked.when(() -> EmployeeResourceMapper.mapEmployeeResourceToEmployee(employeeResource)).thenReturn(employee);

            ResponseEntity responseEntity = employeeRestEndpoint.updateExistingEmployee(employeeResource);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Mockito.verify(employeeService, Mockito.times(1)).updateEmployee(employee);
        }
    }

    @Test
    public void testDeleteExistingEmployee() {
        final int employeeId = 1;

        ResponseEntity responseEntity = employeeRestEndpoint.deleteExistingEmployee(employeeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Mockito.verify(employeeService, Mockito.times(1)).deleteEmployee(employeeId);
    }
}
