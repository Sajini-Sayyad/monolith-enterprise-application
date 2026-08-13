package com.mycompany.entapp.snowman.infrastructure.rest.endpoint;

import com.mycompany.entapp.snowman.infrastructure.rest.resources.HealthResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class HealthRestEndpointUTest {

    @InjectMocks
    private HealthRestEndpoint systemUnderTest = new HealthRestEndpoint();

    @Test
    public void testGetHealthShouldReturnStatusUp() {
        ResponseEntity<HealthResource> responseEntity = systemUnderTest.getHealth();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("UP", responseEntity.getBody().getStatus());
        assertEquals("Snowman Enterprise Application", responseEntity.getBody().getService());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }
}
