/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.domain.repository.impl;

import com.mycompany.entapp.snowman.domain.ProjectTestHelper;
import com.mycompany.entapp.snowman.domain.model.Project;
import com.mycompany.entapp.snowman.domain.repository.ProjectRepository;
import com.mycompany.entapp.snowman.infrastructure.db.dao.ProjectDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRepositoryImplUTest {

    @Mock
    private ProjectDao projectDao;

    @InjectMocks
    private ProjectRepository projectRepository = new ProjectRepositoryImpl();

    @Test
    public void testGetProject() {
        final int projectId = 1;
        Project expectedProject = ProjectTestHelper.getProject();

        Mockito.when(projectDao.retrieveProject(projectId)).thenReturn(expectedProject);

        Project actualProject = projectRepository.findProject(projectId);

        assertEquals(expectedProject, actualProject);
    }

    @Test
    public void testCreateProject() {
        Project project = ProjectTestHelper.getProject();

        Mockito.doNothing().when(projectDao).saveProject(project);

        projectRepository.saveProject(project);

        Mockito.verify(projectDao, Mockito.times(1)).saveProject(project);
    }

    @Test
    public void testDeleteProject() {
        final int projectId = 1;

        Mockito.doNothing().when(projectDao).removeProject(projectId);

        projectRepository.removeProject(projectId);

        Mockito.verify(projectDao, Mockito.times(1)).removeProject(projectId);
    }
}
