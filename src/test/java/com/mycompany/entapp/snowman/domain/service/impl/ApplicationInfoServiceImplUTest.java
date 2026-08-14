/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.domain.service.impl;

import com.mycompany.entapp.snowman.domain.exception.BusinessException;
import com.mycompany.entapp.snowman.domain.model.AppInfo;
import com.mycompany.entapp.snowman.domain.repository.ApplicationInfoRepository;
import com.mycompany.entapp.snowman.domain.service.ApplicationInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationInfoServiceImplUTest {

    @Mock
    private ApplicationInfoRepository applicationInfoRepository;

    @InjectMocks
    private ApplicationInfoService applicationInfoService = new ApplicationInfoServiceImpl();

    @Test
    public void testGetAppInfo() throws BusinessException {
        AppInfo appInfo = new AppInfo();
        appInfo.setId(1);
        appInfo.setVersion("1.0.0");

        Map<Integer, AppInfo> appInfoMap = new HashMap<>();
        appInfoMap.put(1, appInfo);

        Mockito.when(applicationInfoRepository.getAppInfoMap()).thenReturn(appInfoMap);

        AppInfo actualAppInfo = applicationInfoService.getAppInfo();

        assertEquals(appInfo, actualAppInfo);
    }
}
