package com.ffe.traveller.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffe.traveller.TravellerApp;
import com.ffe.traveller.classic.decoder.StarSystem;
import com.ffe.traveller.classic.decoder.Starport;
import com.ffe.traveller.classic.decoder.UniversalPlanetaryProfile;
import com.ffe.traveller.classic.decoder.UniversalPlanetaryProfileMaker;
import org.elasticsearch.client.Client;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * Created by darkmane on 1/13/15.
 */

@Path("/{ruleSet}/")
public class WorldController extends HttpServlet {


    @GET
    @Path("/world")
    @Produces(MediaType.APPLICATION_JSON)
    public UniversalPlanetaryProfile searchAllWorlds(@PathParam("ruleSet") String rules,
                                    @QueryParam("sector") String sector,
                                    @QueryParam("subsector") String subsector,
                                    @QueryParam("hex") String hex,
                                    @QueryParam("upp") String UPPs) {
        String output = "Jersey say : " + rules + " " + sector + " " + subsector + " " + hex + " " + UPPs;
        System.out.println(output);
        Client esClient = TravellerApp.ElasticSearchClient();
        UniversalPlanetaryProfile upp =  UniversalPlanetaryProfileMaker.CreateUniversalPlanetaryProfile(
                Starport.C, 7, 7, 7, 7, 7, 7
        );
        return upp;

    }

    @PUT
    @Path("/world")
    public Response createWorld(@BeanParam StarSystem upp){

        Client esClient = TravellerApp.ElasticSearchClient();

        ObjectMapper mapper = new ObjectMapper();
        String starSystemString = "";
        try{
           starSystemString = mapper.writeValueAsString(upp);
        } catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        System.out.println(starSystemString);

				
        return Response.status(200).entity("Booyah").build();
    }

}
