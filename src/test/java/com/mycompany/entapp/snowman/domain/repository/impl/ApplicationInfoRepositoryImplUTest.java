/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.domain.repository.impl;

import com.mycompany.entapp.snowman.domain.model.AppInfo;
import com.mycompany.entapp.snowman.infrastructure.db.dao.ApplicationInfoDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationInfoRepositoryImplUTest {

    @Mock
    private ApplicationInfoDao applicationInfoDao;

    @InjectMocks
    private ApplicationInfoRepositoryImpl applicationInfoRepository = new ApplicationInfoRepositoryImpl();

    @Test
    public void testGetAppInfoMap() {
        List<AppInfo> appInfos = new ArrayList<>();
        AppInfo appInfo = new AppInfo();
        appInfo.setId(1);
        appInfo.setVersion("1.0.0");
        appInfos.add(appInfo);

        Mockito.when(applicationInfoDao.loadApplicationInfos()).thenReturn(appInfos);

        applicationInfoRepository.initialize();
        Map<Integer, AppInfo> appInfoMap = applicationInfoRepository.getAppInfoMap();

        assertEquals(1, appInfoMap.size());
        assertEquals(appInfo, appInfoMap.get(1));
    }
}
