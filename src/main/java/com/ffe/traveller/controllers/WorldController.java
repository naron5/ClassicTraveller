package com.ffe.traveller.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffe.traveller.TravellerApp;
import com.ffe.traveller.classic.decoder.*;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by darkmane on 1/13/15.
 */

@Path("/{ruleSet}/")
public class WorldController extends HttpServlet {


    @GET
    @Path("/world")
    @Produces(MediaType.APPLICATION_JSON)
    public Planet[] searchAllWorlds(@PathParam("ruleSet") String rules,
                                                     @QueryParam("sector") String sector,
                                                     @QueryParam("subsector") String subsector,
                                                     @QueryParam("hex") String hex,
                                                     @QueryParam("upp") String UPPs) {
        String output = "Jersey say : " + rules + " " + sector + " " + subsector + " " + hex + " " + UPPs;
        System.out.println(output);
        Client esClient = TravellerApp.ElasticSearchClient();
        UniversalPlanetaryProfile upp = UniversalPlanetaryProfileMaker.CreateUniversalPlanetaryProfile(
                Starport.C, 7, 7, 7, 7, 7, 7
        );

        Planet p = PlanetMaker.CreatePlanet(null,null,null,null,null);

        Planet[] arr = new Planet[1];
        arr[0] = p;
        return arr;

    }

    @PUT
    @Path("/world")
    @Produces(MediaType.APPLICATION_JSON)
    public IndexResponse writeWorld(@Valid Planet planet) throws Exception {

        Client esClient = TravellerApp.ElasticSearchClient();

        ObjectMapper mapper = new ObjectMapper();
        IndexResponse response = null;


        response = esClient.prepareIndex("traveller", "world").setSource(mapper.writeValueAsString(planet)).execute().get();

        return response;



    }

    @PUT
    @Path("/world/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public StarSystem generateWorld(@Valid Planet planet) {


        StarSystem newStarSystem = StarSystemMaker.CreateStarSystem(planet);

        return newStarSystem;
    }



}
