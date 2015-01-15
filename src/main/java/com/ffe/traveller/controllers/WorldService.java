package com.ffe.traveller.controllers;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
/**
 * Created by darkmane on 1/13/15.
 */

@Path("/{ruleSet}/world")
public class WorldService {


    @GET
    @Path("/search")
    public Response searchAllWorlds(@PathParam("ruleSet") String rules) {
        String output = "Jersey say : " + rules;

        return Response.status(200).entity(output).build();

    }

}
