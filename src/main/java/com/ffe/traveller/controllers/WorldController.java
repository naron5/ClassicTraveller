package com.ffe.traveller.controllers;


import com.ffe.traveller.classic.decoder.UniversalPlanetaryProfile;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.validation.constraints.*;
/**
 * Created by darkmane on 1/13/15.
 */

@Path("/{ruleSet}/world")
public class WorldController {


    @GET
    @Path("/")
    public static Response searchAllWorlds(@PathParam("ruleSet") String rules,
                                    @QueryParam("sector") String sector,
                                    @QueryParam("subsector") String subsector,
                                    @QueryParam("hex") String hex,
                                    @QueryParam("upp") String UPPs) {
        String output = "Jersey say : " + rules;

        return Response.status(200).entity(output).build();

    }

    @PUT
    @Path("/")
    public static Response createWorld(@BeanParam UniversalPlanetaryProfile upp){


        return Response.status(200).entity("Booyah").build();
    }

}
