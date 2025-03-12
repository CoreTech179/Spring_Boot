package com.example.journalApp.service;

import com.example.journalApp.API.response.WeatherResponse;
import com.example.journalApp.cache.ApplicationCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// @Component
@Service // From 15/01/2025 we will use service annotation where the actual business logic is written.
// Such that when someone see this annotation they will know instantly that here business logic is written only.
public class WeatherService {

//    private static final String apiKey = "b724587cea7c180932ab609b6a2c3c26";

//    Now here the problem is that the API key is hard-coded here, and it is not recommended to do it like this. Because may be when we deploy our application
//    the API_KEY is also get public and everybody will use this key, therefore we can write this API_KEY inside the application.properties file and
//    access it with the help of @value("${property_name}") annotation.

    @Value("${weather.api.key}")
    private String apiKey;

//    private  static final String URL = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

//    Now here also ww have hard coded the URL which should not be done, therefore we will store this URL inside the db for flexibility.

    @Autowired
    private ApplicationCache applicationCacheObj;

    @Autowired
    private RestTemplate restTemplateObj;
//    Basically this is a class in Java which helps us to hit the URL with th help of our java code.

    @Autowired
    private RedisService redisServiceObj;

    public WeatherResponse getWeather(String city){
        WeatherResponse cachedResponse =  redisServiceObj.get("weather_of_" +city, WeatherResponse.class);
        if(cachedResponse != null){
            return cachedResponse;
        }
        else{
//        Update the URL and make it a proper URL such that if we hit the URL it will provide us the result.
            String finalURL = applicationCacheObj.APP_CACHE.get("weather_api").replace("<apiKey>",apiKey).replace("<city>",city);
            ResponseEntity<WeatherResponse> response = restTemplateObj.exchange(finalURL, HttpMethod.GET,null, WeatherResponse.class);
            if(response.getBody() != null){
                redisServiceObj.set("weather_of_" + city, response.getBody(), 300L);
            }
            return response.getBody();
        }

//        Here "null" represents we are not sending any Headers therefore we make it null.
//        Headers means the endpoint is expecting something like basic Authentication, etc.

    }

}
