package tyler.thomas.weatherapi;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService extends MainActivity {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";
    Context context;
    String cityID = "";

    public WeatherDataService(Context context) {
        this.context = context;
    }
    public interface VolleyResponseListener{
        void onError(String message);

        void onResponse(String cityID);

    }

//A callback is a way to schedule a method call to occur when another method finishes its task.
    public void getCityID(String cityName,VolleyResponseListener volleyResponseListener) {
        // Instantiate the RequestQueue.
        String url = QUERY_FOR_CITY_ID + cityName;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            cityID = "";
            try {
                JSONObject cityinfo = response.getJSONObject(0);
                cityID = cityinfo.getString("woeid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //This worked but didn't return anything to the main activity.
            volleyResponseListener.onResponse(cityID);


        }, error ->
        volleyResponseListener.onError("Something wrong"));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface ForecastByIDResponse{
        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModels);

    }
    public void getWeathertByID(String cityID,ForecastByIDResponse forecastByIDResponse) {
        List<WeatherReportModel> report = new ArrayList();
        //Get the JSON Object
        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");
                for (int i=0; i < consolidated_weather_list.length(); i++) {
                    WeatherReportModel one_day_weather = new WeatherReportModel();
                    JSONObject first_day_from_api = (JSONObject) consolidated_weather_list.get(i);
                    one_day_weather.setId(first_day_from_api.getInt("id"));
                    one_day_weather.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                    one_day_weather.setWeather_state_abbr(first_day_from_api.getString("weather_state_abbr"));
                    one_day_weather.setWind_direction_compass(first_day_from_api.getString("wind_direction_compass"));
                    one_day_weather.setCreated(first_day_from_api.getString("created"));
                    one_day_weather.setApplicable_date(first_day_from_api.getString("applicable_date"));
                    one_day_weather.setMin_temp(first_day_from_api.getLong("min_temp"));
                    one_day_weather.setMax_temp(first_day_from_api.getLong("max_temp"));
                    one_day_weather.setWind_speed(first_day_from_api.getLong("wind_speed"));
                    one_day_weather.setAir_pressure(first_day_from_api.getInt("air_pressure"));
                    one_day_weather.setHumidity(first_day_from_api.getInt("humidity"));
                    one_day_weather.setVisibility(first_day_from_api.getLong("visibility"));
                    one_day_weather.setPredictability(first_day_from_api.getInt("predictability"));
                    one_day_weather.setThe_temp(first_day_from_api.getLong("the_temp"));
                    report.add(one_day_weather);
                }
                forecastByIDResponse.onResponse(report);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
                //Get the property called "consolidated weather which is an array

                //get each item
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public interface ForecastByName{
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }
public void getWeatherByName(String cityID,ForecastByName forecastByName){
        //fetch the city id given the name
        //fetch the city forecast given the city id.
        getCityID(cityID, new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityID) {
            getWeathertByID(cityID, new ForecastByIDResponse() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(List<WeatherReportModel> weatherReportModels) {
                //We have the weather report.
                    forecastByName.onResponse(weatherReportModels);
                }
            });
            }
        });

    }
}

