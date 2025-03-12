package com.example.journalApp.API.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

// When we hit the URL the data comes here in this class format.
// The process of converting the JSON into a java class is known as deserialization.
@Component
@Getter
@Setter
public class WeatherResponse {

    @Getter
    @Setter
    public class Current{

        private  int temperature;

        @JsonProperty("weather_descriptions")
//        This JSON property means inside the JSON the property name is defined as "weather_descriptions" and we have declared in camelCase convension.
//        as a result it will convert the property into this camelCase convension.
        private List<String> weatherDescription;

        @JsonProperty("feelslike")
        private int feelsLike;
    }

    private Current current;

}
