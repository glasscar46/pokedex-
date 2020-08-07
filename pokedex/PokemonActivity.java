package edu.harvard.cs50.pokedex;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.M)
public class PokemonActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView numberTextView;
    private TextView type1TextView;
    private TextView type2TextView;
    private String url;
    private RequestQueue requestQueue;
    private   static  final String shared_Pref = "shared_prefs";
    static boolean state;
    Button button;
    String name;
 //   ArrayList<String> caughtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        url = getIntent().getStringExtra("url");
        nameTextView = findViewById(R.id.pokemon_name);
        numberTextView = findViewById(R.id.pokemon_number);
        type1TextView = findViewById(R.id.pokemon_type1);
        type2TextView = findViewById(R.id.pokemon_type2);
        Button button=findViewById(R.id.botton_catch_state);
        load();
      /*  state = getPreferences(Context.MODE_PRIVATE).getBoolean(name, false);
        if(state){
            button.setText("release");
        }
        else {
            button.setText("catch");
        }*/
    }

    public void load() {
        type1TextView.setText("");
        type2TextView.setText("");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    nameTextView.setText(response.getString("name"));
                    numberTextView.setText(String.format("#%03d", response.getInt("id")));
                    name = response.getString("name");
                    JSONArray typeEntries = response.getJSONArray("types");
                    for (int i = 0; i < typeEntries.length(); i++) {
                        JSONObject typeEntry = typeEntries.getJSONObject(i);
                        int slot = typeEntry.getInt("slot");
                        String type = typeEntry.getJSONObject("type").getString("name");

                        if (slot == 1) {
                            type1TextView.setText(type);
                        }
                        else if (slot == 2) {
                            type2TextView.setText(type);
                        }
                    }
                } catch (JSONException e) {
                    Log.e("cs50", "Pokemon json error", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("cs50", "Pokemon details error", error);
            }
        });

        requestQueue.add(request);
    }


    public void  toggleCatch(View view){
        state = getPreferences(Context.MODE_PRIVATE).getBoolean(name, false);
         button = view.findViewById(R.id.botton_catch_state);
         if(button.getText() == "catch"){
             button.setText("release");
             getPreferences(Context.MODE_PRIVATE).edit().putBoolean(name, true).commit();
         }
         else {
             button.setText("catch");
             getPreferences(Context.MODE_PRIVATE).edit().putBoolean(name, false).commit();
         }

    }
     /*private  void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(shared_Pref,MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         Gson gson = new Gson();
         String json = gson.toJson(caughtList);
         editor.putString("taskList",json);
         editor.apply();
     }
     private  void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(shared_Pref, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList",null);
         Type type = new TypeToken<ArrayList<String>>() {}.getType();
         caughtList = gson.fromJson(json,type);
         if(caughtList == null){
             caughtList = new ArrayList<>();
         }
     }*/
}
