/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.rest.mappers;

import com.mycompany.entapp.snowman.infrastructure.rest.resources.EmployeeResource;
import com.mycompany.entapp.snowman.domain.model.Employee;
import com.mycompany.entapp.snowman.domain.model.EmployeeRole;

public final class EmployeeResourceMapper {

    private EmployeeResourceMapper() {
    }

    public static EmployeeResource mapEmployeeToEmployeeResource(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeResource employeeResource = new EmployeeResource();
        employeeResource.setEmployeeId(employee.getId());
        employeeResource.setFirstName(employee.getFirstname());
        employeeResource.setSecondName(employee.getSurname());
        if (employee.getRole() != null) {
            employeeResource.setRole(employee.getRole().getRole());
        }
        return employeeResource;
    }

    public static Employee mapEmployeeResourceToEmployee(EmployeeResource employeeResource) {
        if (employeeResource == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(employeeResource.getEmployeeId());
        employee.setFirstname(employeeResource.getFirstName());
        employee.setSurname(employeeResource.getSecondName());
        if (employeeResource.getRole() != null) {
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setRole(employeeResource.getRole());
            employee.setRole(employeeRole);
        }
        return employee;
    }
}
