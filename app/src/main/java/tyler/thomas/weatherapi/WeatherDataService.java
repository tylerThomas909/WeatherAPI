package tyler.thomas.weatherapi;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    Context context;
    String cityID = "";

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public String getCityID(String cityName){
        // Instantiate the RequestQueue.
        String url = QUERY_FOR_CITY_ID + cityName;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null, response -> {
           cityID = "";
            try {
                JSONObject cityinfo = response.getJSONObject(0);
                cityID = cityinfo.getString("woeid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(context,"City ID " + cityID,Toast.LENGTH_SHORT).show();
        }, error -> Toast.makeText(context,"Error!",Toast.LENGTH_SHORT).show());
        MySingleton.getInstance(context).addToRequestQueue(request);
        return  cityID;
    }
    //public List<WeatherReportModel> get CityForeCastByID(String cityID){

}
    //public List<WeatherReportModel> get CityForeCastByName(String cityID)
