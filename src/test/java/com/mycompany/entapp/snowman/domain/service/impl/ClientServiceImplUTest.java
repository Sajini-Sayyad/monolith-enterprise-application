/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.domain.service.impl;

import com.mycompany.entapp.snowman.domain.exception.SnowmanException;
import com.mycompany.entapp.snowman.domain.model.Client;
import com.mycompany.entapp.snowman.domain.repository.ClientRepository;
import com.mycompany.entapp.snowman.domain.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplUTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService = new ClientServiceImpl();

    @Test
    public void testGetClient() {
        final int clientId = 1;
        Client client = new Client();
        client.setId(clientId);
        client.setClientName("Client");

        Mockito.when(clientRepository.getClient(clientId)).thenReturn(client);

        Client actualClient = clientService.getClient(clientId);

        assertEquals(client, actualClient);
    }

    @Test
    public void testCreateClient() throws SnowmanException {
        final int clientId = 1;
        Client client = new Client();
        client.setId(clientId);
        client.setClientName("Client");

        Mockito.when(clientRepository.getClient(clientId)).thenReturn(null);
        Mockito.doNothing().when(clientRepository).createClient(client);

        clientService.createClient(client);

        Mockito.verify(clientRepository, times(1)).createClient(client);
    }

    @Test(expected = SnowmanException.class)
    public void testCreateClientThrowsSnowmanExceptionIfClientAlreadyExists() throws SnowmanException {
        final int clientId = 1;
        Client client = new Client();
        client.setId(clientId);
        client.setClientName("Client");

        Mockito.when(clientRepository.getClient(clientId)).thenReturn(client);

        clientService.createClient(client);
    }

    @Test
    public void testUpdateClient() throws SnowmanException {
        final int clientId = 1;
        Client client = new Client();
        client.setId(clientId);
        client.setClientName("Client");

        Mockito.when(clientRepository.getClient(clientId)).thenReturn(client);
        Mockito.doNothing().when(clientRepository).updateClient(client);

        clientService.updateClient(client);

        Mockito.verify(clientRepository, times(1)).updateClient(client);
    }

    @Test(expected = SnowmanException.class)
    public void testUpdateClientThrowsSnowmanExceptionIfClientDoesNotExist() throws SnowmanException {
        final int clientId = 1;
        Client client = new Client();
        client.setId(clientId);
        client.setClientName("Client");

        Mockito.when(clientRepository.getClient(clientId)).thenReturn(null);

        clientService.updateClient(client);
    }

    @Test
    public void testDeleteClient() throws SnowmanException {
        final int clientId = 1;
        Client client = new Client();
        client.setId(clientId);
        client.setClientName("Client");

        Mockito.when(clientRepository.getClient(clientId)).thenReturn(client);
        Mockito.doNothing().when(clientRepository).deleteClient(clientId);

        clientService.deleteClient(clientId);

        Mockito.verify(clientRepository, times(1)).deleteClient(clientId);
    }

    @Test(expected = SnowmanException.class)
    public void testDeleteClientThrowsSnowmanExceptionIfClientDoesNotExist() throws SnowmanException {
        final int clientId = 1;

        Mockito.when(clientRepository.getClient(clientId)).thenReturn(null);

        clientService.deleteClient(clientId);
    }
}
