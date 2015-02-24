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
    @Path("/starsystem")
    @Produces(MediaType.APPLICATION_JSON)
    public StarSystem searchAllWorlds(@PathParam("ruleSet") String rules,
                                      @QueryParam("sector") String sector,
                                      @QueryParam("subsector") String subsector,
                                      @QueryParam("hex") String hex,
                                      @QueryParam("upp") String UPPs) {
        Client esClient = TravellerApp.ElasticSearchClient();

        Planet p = PlanetMaker.CreatePlanet(null, null, null, null, null, null, null, null, null, null, null, null, null);

        return StarSystemMaker.CreateStarSystem(null, p);

    }

    @PUT
    @Path("/starsystem")
    @Produces(MediaType.APPLICATION_JSON)
    public IndexResponse writeWorld(@Valid StarSystem system) throws Exception {

        Client esClient = TravellerApp.ElasticSearchClient();

        ObjectMapper mapper = new ObjectMapper();
        IndexResponse response = null;


        response = esClient.prepareIndex("traveller", "world").setSource(mapper.writeValueAsString(system)).execute().get();

        return response;


    }

    @PUT
    @Path("/starsystem/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public StarSystem generateWorld() {


        StarSystem newStarSystem = StarSystemMaker.CreateStarSystem();

        return newStarSystem;
    }

    @PUT
    @Path("/starsystem/generate/planet")
    @Produces(MediaType.APPLICATION_JSON)
    public StarSystem generateWorld(Planet planet) {


        StarSystem newStarSystem = StarSystemMaker.CreateStarSystem(null, planet);

        return newStarSystem;
    }

    @PUT
    @Path("/starsystem/generate/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public StarSystem generateWorld(UniversalPlanetaryProfile upp) {

        Planet planet = PlanetMaker.CreatePlanet(null, null, null, null, null,
                upp.getDiameter(), upp.getAtmosphere(), upp.getHydro(), upp.getPopulation(),
                upp.getGovernment(), upp.getLaw_level(), null, null);
        StarSystem newStarSystem = StarSystemMaker.CreateStarSystem(null, planet);

        return newStarSystem;
    }

}
