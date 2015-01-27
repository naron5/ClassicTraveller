package com.ffe.traveller;


import com.ffe.traveller.controllers.WorldController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;

/**
 * Created by darkmane on 1/13/15.
 */
public class TravellerApp extends ResourceConfig {

    public TravellerApp() {


        packages(true, "com.ffe.traveller.controllers");

        register(WorldController.class);
        register(ValidationFeature.class);
//        register(HttpMethodBeforeFilter.class);
//        register(HttpMethodAfterFilter.class);
//        register(UnauthorizedMapper.class);
//        register(BaseExceptionMapper.class);
    }
}

