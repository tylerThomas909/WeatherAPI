package tyler.thomas.weatherapi;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService extends MainActivity {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
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
    //public List<WeatherReportModel> get CityForeCastByID(String cityID){

}
    //public List<WeatherReportModel> get CityForeCastByName(String cityID)

