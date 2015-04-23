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

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Alexander Klimuk
 */
public class CasTicketProviderTest {

    @Test
    public void getServiceTicket() {
        CasTicketProvider casTicketProvider = new CasTicketProvider("Martin", "123");
        String ticket = casTicketProvider.getServiceTicket("http://localhost:8080/rentacar-rest/secured/cars");
        Assert.assertTrue(ticket.contains("ST"));
    }

}
