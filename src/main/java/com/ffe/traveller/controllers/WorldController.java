package com.ffe.traveller.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffe.traveller.TravellerApp;
import com.ffe.traveller.classic.decoder.StarSystem;
import org.elasticsearch.client.Client;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
/**
 * Created by darkmane on 1/13/15.
 */

@Path("/{ruleSet}/")
public class WorldController extends HttpServlet {


    @GET
    @Path("/world")
    public static Response searchAllWorlds(@PathParam("ruleSet") String rules,
                                    @QueryParam("sector") String sector,
                                    @QueryParam("subsector") String subsector,
                                    @QueryParam("hex") String hex,
                                    @QueryParam("upp") String UPPs) {
        String output = "Jersey say : " + rules + " " + sector + " " + subsector + " " + hex + " " + UPPs;
        System.out.println(output);
        Client esClient = TravellerApp.ElasticSearchClient();
        return Response.status(200).entity(output).build();

    }

    @PUT
    @Path("/world")
    public static Response createWorld(@BeanParam StarSystem upp){

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
