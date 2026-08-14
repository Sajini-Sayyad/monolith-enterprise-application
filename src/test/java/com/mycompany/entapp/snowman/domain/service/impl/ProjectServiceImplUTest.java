/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.domain.service.impl;

import com.mycompany.entapp.snowman.domain.ProjectTestHelper;
import com.mycompany.entapp.snowman.domain.model.Project;
import com.mycompany.entapp.snowman.domain.repository.ProjectRepository;
import com.mycompany.entapp.snowman.domain.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplUTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService = new ProjectServiceImpl();

    @Test
    public void testGetProject() {
        final int projectId = 1;
        Project expectedProject = ProjectTestHelper.getProject();

        Mockito.when(projectRepository.findProject(projectId)).thenReturn(expectedProject);

        Project actualProject = projectService.getProject(projectId);

        assertEquals(expectedProject, actualProject);
    }

    @Test
    public void testCreateProject() {
        Project project = ProjectTestHelper.getProject();

        Mockito.doNothing().when(projectRepository).saveProject(ArgumentMatchers.any(Project.class));

        projectService.createProject(project);

        Mockito.verify(projectRepository, Mockito.times(1)).saveProject(project);
    }

    @Test
    public void testUpdateProject() {
        Project project = ProjectTestHelper.getProject();

        Mockito.doNothing().when(projectRepository).saveProject(ArgumentMatchers.any(Project.class));

        projectService.updateProject(project);

        Mockito.verify(projectRepository, Mockito.times(1)).saveProject(project);
    }

    @Test
    public void testDeleteProject() {
        final int projectId = 1;

        Mockito.doNothing().when(projectRepository).removeProject(projectId);

        projectService.deleteProject(projectId);

        Mockito.verify(projectRepository, Mockito.times(1)).removeProject(projectId);
    }
}
