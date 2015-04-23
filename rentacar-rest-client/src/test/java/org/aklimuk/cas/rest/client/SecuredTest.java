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

import java.util.Collection;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * <p>
 * This test calls REST service that is secured with CAS.
 * </p>
 * <p>
 * For transparency of the DEMO we prefer code duplication, not code reuse, abstract classes,
 * parameters, etc. Compare to {@link NonSecuredTest}.
 * </p>
 * 
 * @author Alexander Klimuk
 */
public class SecuredTest {

    private SecuredRestClient client;

    @BeforeTest
    public void beforeTest() {
        client = new SecuredRestClient();
    }

    @Test
    public void createCar() throws Exception {
        ClientCar car = client.createCar("Porsche", "Cayman GTS", "S-AB 915");
        Assert.assertNotNull(car);
        Assert.assertNotNull(car.id);
        Assert.assertEquals(car.brand, "Porsche");
    }

    @Test
    public void findAllCars() throws Exception {
        Collection<ClientCar> cars = client.findAllCars();
        Assert.assertTrue(cars.size() >= 5);
    }

    @Test
    public void findCarById() throws Exception {
        ClientCar carA = client.findCarById(1);
        Assert.assertEquals(carA.id, new Integer(1));

        ClientCar carB = client.findCarById(5);
        Assert.assertEquals(carB.id, new Integer(5));
    }

}
