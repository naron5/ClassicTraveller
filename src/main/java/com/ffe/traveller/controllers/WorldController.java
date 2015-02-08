package com.ffe.traveller.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffe.traveller.TravellerApp;
import com.ffe.traveller.classic.decoder.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by darkmane on 1/13/15.
 */

@Path("/{ruleSet}/")
public class WorldController extends HttpServlet {


    @GET
    @Path("/world")
    @Produces(MediaType.APPLICATION_JSON)
    public UniversalPlanetaryProfile[] searchAllWorlds(@PathParam("ruleSet") String rules,
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
        UniversalPlanetaryProfile[] arr = new UniversalPlanetaryProfile[1];
        arr[0] = upp;
        return arr;

    }

    @PUT
    @Path("/world")
    @Produces(MediaType.APPLICATION_JSON)
    public IndexResponse writeWorld(@Valid UniversalPlanetaryProfile upp) throws Exception {

        Client esClient = TravellerApp.ElasticSearchClient();

        ObjectMapper mapper = new ObjectMapper();
        IndexResponse response = null;


        response = esClient.prepareIndex("traveller", "world").setSource(mapper.writeValueAsString(upp)).execute().get();

        return response;



    }

    @PUT
    @Path("/world/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public StarSystem generateWorld(@Valid UniversalPlanetaryProfile upp) {


        StarSystem newStarSystem = StarSystemMaker.CreateStarSystem(upp);

        return newStarSystem;
    }



}
