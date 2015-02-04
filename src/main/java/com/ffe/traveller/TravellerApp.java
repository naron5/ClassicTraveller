package com.ffe.traveller;


import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;


/**
 * Created by darkmane on 1/13/15.
 */
public class TravellerApp extends ResourceConfig {

    private static Client client = null;
    private static Properties properties = null;

    static {
        loadProperties();
//        TransportClient cl = new TransportClient();
//        InetSocketTransportAddress addr = new InetSocketTransportAddress(properties.getProperty("elastic_search.server"),
//                Integer.parseInt(properties.getProperty("elastic_search.port")));
//        cl.addTransportAddress(addr);
//        client = cl;
    }

    public TravellerApp() {


        packages(true, "com.ffe.traveller.controllers");

//        register(WorldController.class);
//        register(ValidationFeature.class);
//        register(HttpMethodBeforeFilter.class);
//        register(HttpMethodAfterFilter.class);
//        register(UnauthorizedMapper.class);
//        register(BaseExceptionMapper.class);
    }

    public static Client ElasticSearchClient() {


        return client;
    }

    private static void loadProperties() {
        if (properties == null) {
            Map<String, String> envMap = System.getenv();
            String env = "development";
            if (envMap.containsKey("ENVIRONMENT"))
                env = envMap.get("ENVIRONMENT");

            Properties prop = new Properties();
            InputStream in = TravellerApp.class.getResourceAsStream("traveller." + env + ".properties");

            try {
                prop.load(in);
                in.close();
                properties = prop;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

