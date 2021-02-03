package ru.magenta.distance_calculator.api.controllers;

import ru.magenta.distance_calculator.db.DBService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/city")
public class CityController {

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCities(){
        try {
            return Response.ok().entity(new DBService().getAllCities()).build();
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/byId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCityById(@PathParam("id") String id){
        try {
            return Response.ok().entity(new DBService().getCityById(id).orElseThrow(DataNotFoundException::new)).build();
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

}
