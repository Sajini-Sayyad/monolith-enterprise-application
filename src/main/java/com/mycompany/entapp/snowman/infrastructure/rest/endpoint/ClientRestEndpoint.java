/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.rest.endpoint;

import com.mycompany.entapp.snowman.domain.exception.SnowmanException;
import com.mycompany.entapp.snowman.infrastructure.rest.mappers.ClientResourceMapper;
import com.mycompany.entapp.snowman.infrastructure.rest.resources.ClientResource;
import com.mycompany.entapp.snowman.domain.model.Client;
import com.mycompany.entapp.snowman.domain.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientRestEndpoint {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET)
    public ResponseEntity<ClientResource> getClientInfo(@PathVariable("clientId") Integer clientId) {
        Client client = clientService.getClient(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        ClientResource clientResource = ClientResourceMapper.mapToClientResource(client);
        return ResponseEntity.ok(clientResource);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity createClientInfo(@Valid @RequestBody ClientResource clientResource) {
        Client client = ClientResourceMapper.mapToClient(clientResource);
        try {
            clientService.createClient(client);
            return ResponseEntity.ok().build();
        } catch (SnowmanException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity updateClientInfo(@Valid @RequestBody ClientResource clientResource) {
        Client client = ClientResourceMapper.mapToClient(clientResource);
        try {
            clientService.updateClient(client);
            return ResponseEntity.ok().build();
        } catch (SnowmanException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{clientId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteClientInfo(@PathVariable("clientId") Integer clientId) {
        try {
            clientService.deleteClient(clientId);
            return ResponseEntity.ok().build();
        } catch (SnowmanException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
