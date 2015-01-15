package com.ffe.traveller;


import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;

import com.ffe.traveller.controllers.WorldService;

/**
 * Created by darkmane on 1/13/15.
 */
public class TravellerApp extends ResourceConfig {

    public TravellerApp() {


        packages(true, "com.ffe.traveller.controllers");

        register(WorldService.class);
        register(ValidationFeature.class);
//        register(HttpMethodBeforeFilter.class);
//        register(HttpMethodAfterFilter.class);
//        register(UnauthorizedMapper.class);
//        register(BaseExceptionMapper.class);
    }
}

