package tyler.thomas.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
Button btn_CityID;
Button btn_getWeatherByID;
Button btn_getWeatherByName;
EditText et_dataInput;
ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Assigning values to each control in the layout.
        btn_CityID = findViewById(R.id.btn_getCityId);
        btn_getWeatherByID = findViewById(R.id.btn_getWeatherByCityID);
        btn_getWeatherByName = findViewById(R.id.btn_getWeatherByCityName);
        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherReport = findViewById(R.id.lv_weatherReports);
        //Click listeners for each button.
        btn_CityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Semantic error
                WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
                //This didn't return anything.
                String cityID = weatherDataService.getCityID(et_dataInput.getText().toString());
                Toast.makeText(MainActivity.this,"Returned an ID of "+ cityID, Toast.LENGTH_SHORT).show();
            }
        });
        btn_getWeatherByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Hello2",Toast.LENGTH_SHORT).show();
            }
        });
        btn_getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this," Three",Toast.LENGTH_SHORT).show();
            }
        });

    }
}