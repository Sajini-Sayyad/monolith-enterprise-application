/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.rest.endpoint;

import com.mycompany.entapp.snowman.domain.exception.BusinessException;
import com.mycompany.entapp.snowman.domain.model.AppInfo;
import com.mycompany.entapp.snowman.domain.service.ApplicationInfoService;
import com.mycompany.entapp.snowman.infrastructure.rest.mappers.AppInfoResourceMapper;
import com.mycompany.entapp.snowman.infrastructure.rest.resources.AppInfoResource;
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
public class AppInfoRestEndpointUTest {

    @Mock
    private ApplicationInfoService applicationInfoService;

    @InjectMocks
    private AppInfoRestEndpoint classInTest = new AppInfoRestEndpoint();

    @Test
    public void testGetApplicationInformation() throws BusinessException {
        AppInfo appInfo = new AppInfo();
        appInfo.setId(1);
        appInfo.setVersion("1.0.0");

        AppInfoResource appInfoResource = new AppInfoResource();
        appInfoResource.setId(1);
        appInfoResource.setVersion("1.0.0");

        Mockito.when(applicationInfoService.getAppInfo()).thenReturn(appInfo);

        try (MockedStatic<AppInfoResourceMapper> mocked = Mockito.mockStatic(AppInfoResourceMapper.class)) {
            mocked.when(() -> AppInfoResourceMapper.mapAppInfoToResource(appInfo)).thenReturn(appInfoResource);

            ResponseEntity<AppInfoResource> response = classInTest.getApplicationInformation();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(appInfoResource, response.getBody());
        }
    }
}
