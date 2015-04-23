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
package org.aklimuk.cas.rest.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Alexander Klimuk
 */
@ApplicationScoped
public class CarStorage {

    private int counter;

    private static Collection<Car> cars;

    /**
     * Initializes car storage.
     */
    /*
     * For simplicity we don't care about calling public non-final method from constructor.
     */
    public CarStorage() {
        cars = new ArrayList<Car>();
        createCar("Volkswagen", "Passat 1.8T Sport", "WOB-GX 741");
        createCar("Audi", "A5 Coupé", "IN-A 5103");
        createCar("BMW", "ACTIVE HYBRID 5", "M-KV 8597");
        createCar("Mercedes-Benz", "C 250 d 4MATIC", "S-W 2097");
        createCar("Opel", "Mokka Edition", "GG-CT 785");
    }

    public synchronized Car createCar(String brand, String model, String plate) {
        Integer id = ++counter;
        Car car = new Car(id, brand, model, plate);
        cars.add(car);
        return car;
    }

    public synchronized void deleteCarById(Integer id) {
        for (Iterator<Car> iterator = cars.iterator(); iterator.hasNext();) {
            Car car = (Car) iterator.next();
            if (car.id.equals(id)) {
                iterator.remove();
            }
        }
    }

    public synchronized Car findCarById(Integer id) {
        for (Car car : cars) {
            if (car.id.equals(id)) {
                return car;
            }
        }
        return null;
    }

    public synchronized Collection<Car> findCarsByBrand(String brand) {
        Collection<Car> carsByBrand = new ArrayList<Car>();
        for (Car car : cars) {
            if (car.brand.equals(brand)) {
                carsByBrand.add(car);
            }
        }
        return carsByBrand;
    }

    public synchronized Collection<Car> getAllCars() {
        return Collections.unmodifiableCollection(cars);
    }

}
