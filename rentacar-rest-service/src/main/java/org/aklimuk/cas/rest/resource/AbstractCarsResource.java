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

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Alexander Klimuk
 */
public abstract class AbstractCarsResource {

    @Inject
    private CarStorage carStorage;

    @Context
    private UriInfo uriInfo;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("id") Integer id) {
        Car car = carStorage.findCarById(id);
        if (car != null) {
            return Response.ok(car).build();
        } else {
            throw new NotFoundException("Car not found for id " + id);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCars() {
        Collection<Car> cars = carStorage.getAllCars();
        return Response.ok(cars).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCar(Car carDef) {
        Car newCar = carStorage.createCar(carDef.brand, carDef.model, carDef.plate);
        URI carUri = uriInfo.getAbsolutePathBuilder().path("/{id}").build(newCar.id);
        return Response.created(carUri).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCar(@PathParam("id") Integer id) {
        carStorage.deleteCarById(id);
        return Response.ok().build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCar(@PathParam("id") Integer id, @FormParam("brand") String brand, @FormParam("model") String model,
            @FormParam("plate") String plate) {
        Car car = carStorage.findCarById(id);
        if (car != null) {
            car.brand = brand;
            car.model = model;
            car.plate = plate;
            return Response.ok().build();
        } else {
            throw new NotFoundException("Car not found for id " + id);
        }
    }
}
