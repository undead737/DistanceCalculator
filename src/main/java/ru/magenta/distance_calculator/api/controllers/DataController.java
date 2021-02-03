package ru.magenta.distance_calculator.api.controllers;

import ru.magenta.distance_calculator.data.ApplicationDataService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

//Content-Type  multipart/form-data
@Path("/data")
public class DataController {
    @POST
    @Path("/xml/import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importD(InputStream mass){
        try {
            new ApplicationDataService().importData(mass);
            return Response.ok().build();
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/xml/export")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response exportD(){
        try {
            return Response.ok(new ApplicationDataService().exportData()).header("Content-Disposition",
                    "attachment; filename=\"export.xml\"").build();
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
}