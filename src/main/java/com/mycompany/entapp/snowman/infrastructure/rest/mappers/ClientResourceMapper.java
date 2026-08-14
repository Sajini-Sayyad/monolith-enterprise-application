/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.rest.mappers;

import com.mycompany.entapp.snowman.infrastructure.rest.resources.ClientResource;
import com.mycompany.entapp.snowman.domain.model.Client;

public final class ClientResourceMapper {

    private ClientResourceMapper() {
    }

    public static Client mapToClient(ClientResource clientResource) {
        if (clientResource == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientResource.getClientId());
        client.setClientName(clientResource.getClientName());
        if (clientResource.getProjects() != null) {
            client.setProjects(ProjectResourceMapper.mapToProjects(clientResource.getProjects()));
        }
        return client;
    }

    public static ClientResource mapToClientResource(Client client) {
        if (client == null) {
            return null;
        }
        ClientResource clientResource = new ClientResource();
        clientResource.setClientId(client.getId());
        clientResource.setClientName(client.getClientName());
        if (client.getProjects() != null) {
            clientResource.setProjects(ProjectResourceMapper.mapToProjectResources(client.getProjects()));
        }
        return clientResource;
    }
}
