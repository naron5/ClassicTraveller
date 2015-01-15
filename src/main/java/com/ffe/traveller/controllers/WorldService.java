package com.ffe.traveller.controllers;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.validation.constraints.*;
/**
 * Created by darkmane on 1/13/15.
 */

@Path("/{ruleSet}/world")
public class WorldService {


    @GET
    @Path("/")
    public Response searchAllWorlds(@PathParam("ruleSet") String rules,
                                    @QueryParam("sector") String sector,
                                    @QueryParam("subsector") String subsector,
                                    @QueryParam("hex") @Pattern("\d\d\d\d") String hex,
                                    @QueryParam("upp") @Pattern("\w\w\w\w\w\w") String UPPs) {
        String output = "Jersey say : " + rules;

        return Response.status(200).entity(output).build();

    }

}
