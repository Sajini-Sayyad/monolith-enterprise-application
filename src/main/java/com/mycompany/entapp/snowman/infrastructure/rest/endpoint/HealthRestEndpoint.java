package com.mycompany.entapp.snowman.infrastructure.rest.endpoint;

import com.mycompany.entapp.snowman.infrastructure.rest.resources.HealthResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/health")
public class HealthRestEndpoint {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<HealthResource> getHealth() {
        HealthResource healthResource = new HealthResource(
                "UP",
                "Snowman Enterprise Application",
                Instant.now().toString()
        );
        return ResponseEntity.ok(healthResource);
    }
}
