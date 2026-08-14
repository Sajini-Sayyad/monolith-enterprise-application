/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.rest.endpoint;

import com.mycompany.entapp.snowman.domain.ProjectTestHelper;
import com.mycompany.entapp.snowman.domain.model.Project;
import com.mycompany.entapp.snowman.domain.service.ProjectService;
import com.mycompany.entapp.snowman.infrastructure.rest.mappers.ProjectResourceMapper;
import com.mycompany.entapp.snowman.infrastructure.rest.resources.ProjectResource;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRestEndpointUTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectRestEndpoint classUnderTest = new ProjectRestEndpoint();

    @Test
    public void testGetProject() {
        final int projectId = 1;
        Project project = ProjectTestHelper.getProject();

        ProjectResource expectedProjectResource = new ProjectResource();
        expectedProjectResource.setProjectId(1);
        expectedProjectResource.setTitle("Project Title");
        expectedProjectResource.setDateStarted(new DateTime(2018, 1, 1, 12, 0, 0).toDate());
        expectedProjectResource.setDateEnded(new DateTime(2018, 1, 1, 13, 0, 0).toDate());

        Mockito.when(projectService.getProject(projectId)).thenReturn(project);

        try (MockedStatic<ProjectResourceMapper> mocked = Mockito.mockStatic(ProjectResourceMapper.class)) {
            mocked.when(() -> ProjectResourceMapper.mapToProjectResource(project)).thenReturn(expectedProjectResource);

            ResponseEntity<ProjectResource> response = classUnderTest.getProject(projectId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedProjectResource, response.getBody());
        }
    }

    @Test
    public void testCreateProject() {
        Project project = ProjectTestHelper.getProject();

        ProjectResource projectResource = new ProjectResource();
        projectResource.setProjectId(1);
        projectResource.setTitle("Project Title");
        projectResource.setDateStarted(new DateTime(2018, 1,1, 12, 0, 0).toDate());

        Mockito.doNothing().when(projectService).createProject(ArgumentMatchers.any(Project.class));

        ResponseEntity<?> response = classUnderTest.createProject(projectResource);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProject() {
        final int projectId = 1;

        Mockito.doNothing().when(projectService).deleteProject(projectId);

        classUnderTest.deleteProject(projectId);

        Mockito.verify(projectService, Mockito.times(1)).deleteProject(projectId);
    }

    @Test
    public void testUpdateProject() {
        Project project = ProjectTestHelper.getProject();

        ProjectResource projectResource = new ProjectResource();
        projectResource.setProjectId(1);
        projectResource.setTitle("Project Title");
        projectResource.setDateStarted(new DateTime(2018, 1,1, 12, 0, 0).toDate());

        Mockito.doNothing().when(projectService).updateProject(ArgumentMatchers.any(Project.class));

        ResponseEntity<?> response = classUnderTest.updateProject(projectResource);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
