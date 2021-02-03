package ru.magenta.distance_calculator.api.controllers;

import ru.magenta.distance_calculator.api.models.request.DistanceCalculatingRequest;
import ru.magenta.distance_calculator.api.models.request.DistanceP2PCalculatingRequest;
import ru.magenta.distance_calculator.math.MathService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/calculate")
public class CalculateController {
    @POST
    @Path("/p2p/customPoints")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response calculate(DistanceP2PCalculatingRequest request){
        try {
            return Response.ok().entity(new MathService().getDistanceBetweenTwoPoints(request.getFirstPosition(), request.getSecondPosition())).build();
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/byCities")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculate(DistanceCalculatingRequest request){
        try {
            return Response.ok().entity(new MathService().getCalculatingDistanceResult(request)).build();
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/byAllCities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculatebyAll(){
        try {
            return Response.ok().entity(new MathService().getCalculatingDistanceResult()).build();
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }


}
