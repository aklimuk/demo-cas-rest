/*
 * Copyright 2015 Alexander Klimuk
 * aklimuk@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.aklimuk.cas.rest.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * For simplicity of DEMO we use hardcoded URLs and other constants. Feel free to use parameters
 * instead.
 * 
 * @author Alexander Klimuk
 */
public class CasTicketProvider {

    private String username;

    private String password;

    private String ticketGrantingTicket;

    public CasTicketProvider(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String detectCasServerUrl(String service) {
        // We send a plain request, without any authentication info.
        // We expect that the REST service replies with status 401 (Not Authorized)
        // and tells us where we can request a ticket.
        Response response = ClientBuilder.newClient().target(service).request().get();

        if (response.getStatus() != 401) {
            throw new RuntimeException("Expected status 401, but was " + response.getStatus());
        }

        String casServerUrl = response.getHeaders().getFirst("Location").toString();

        System.out.println("CAS server URL: " + casServerUrl);

        return casServerUrl;
    }

    protected String getTicketGrantingTicket(String casServerUrl) {
        String login = "username=" + username + "&password=" + password;
        Response response = ClientBuilder.newClient().target(casServerUrl).request()
                .post(Entity.entity(login, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatus() != 201) {
            throw new RuntimeException("Unexpected status: " + response.getStatus());
        }

        String location = response.getHeaders().getFirst("Location").toString();

        System.out.println("Ticket Granting Ticket: " + location);

        return location;
    }

    public String getServiceTicket(String service) {
        if (ticketGrantingTicket == null) {
            String casServerUrl = detectCasServerUrl(service);
            ticketGrantingTicket = getTicketGrantingTicket(casServerUrl);
        }

        Response response = ClientBuilder.newClient().target(ticketGrantingTicket).request()
                .post(Entity.entity("service=" + service, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Unexpected status: " + response.getStatus());
        }

        String ticket = response.readEntity(String.class);

        System.out.println("Service Ticket: " + ticket);

        return ticket;
    }

}
