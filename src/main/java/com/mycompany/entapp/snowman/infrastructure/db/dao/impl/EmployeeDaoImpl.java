/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.db.dao.impl;

import com.mycompany.entapp.snowman.domain.model.Employee;
import com.mycompany.entapp.snowman.infrastructure.db.dao.AbstractHibernateDao;
import com.mycompany.entapp.snowman.infrastructure.db.dao.EmployeeDao;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl extends AbstractHibernateDao implements EmployeeDao {
    @Override
    public Employee retrieveEmployee(int employeeId) {
        return (Employee) getCurrentSession().get(Employee.class, employeeId);
    }

    @Override
    public void saveEmployee(Employee employee) {
        getCurrentSession().saveOrUpdate(employee);
    }

    @Override
    public void deleteEmployee(int employeeId) {
        Employee employee = retrieveEmployee(employeeId);
        if (employee != null) {
            getCurrentSession().delete(employee);
        }
    }
}
