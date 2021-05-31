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

        void onResponse(WeatherReportModel weatherReportModel);

    }
    public void getWeathertByID(String cityID,ForecastByIDResponse forecastByIDResponse) {
        List<WeatherReportModel> report = new ArrayList();
        //Get the JSON Object
        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");
                WeatherReportModel first_day = new WeatherReportModel();
                JSONObject first_day_from_api = (JSONObject) consolidated_weather_list.get(0);
                first_day.setId(first_day_from_api.getInt("id"));
                first_day.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                first_day.setWeather_state_abbr(first_day_from_api.getString("weather_state_abbr"));
                first_day.setWind_direction_compass(first_day_from_api.getString("wind_direction_compass"));
                first_day.setCreated(first_day_from_api.getString("created"));
                first_day.setApplicable_date(first_day_from_api.getString("applicable_date"));
                first_day.setMin_temp(first_day_from_api.getLong("min_temp"));
                first_day.setMax_temp(first_day_from_api.getLong("max_temp"));
                first_day.setWind_speed(first_day_from_api.getLong("wind_speed"));
                first_day.setAir_pressure(first_day_from_api.getInt("air_pressure"));
                first_day.setHumidity(first_day_from_api.getInt("humidity"));
                first_day.setVisibility(first_day_from_api.getLong("visibility"));
                first_day.setPredictability(first_day_from_api.getInt("predictability"));
                forecastByIDResponse.onResponse(first_day);


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

}
    //public List<WeatherReportModel> get CityForeCastByName(String cityID)

