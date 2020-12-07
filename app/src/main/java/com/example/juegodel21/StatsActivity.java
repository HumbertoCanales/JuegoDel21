package com.example.juegodel21;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

import static com.example.juegodel21.MainActivity.URL_stats;

public class StatsActivity extends AppCompatActivity{
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        mostrarEstadisticas();
    }

    private void mostrarEstadisticas() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_stats, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray stats = response.getJSONArray("resultados");
                    Gson gsonConv = new Gson();
                    Type typePersonasList = new TypeToken<List<Persona>>() {
                    }.getType();
                    List<Persona> personasList = gsonConv.fromJson(stats.toString(), typePersonasList);
                    PersonaAdapter personaAdapter = new PersonaAdapter(personasList, StatsActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.recycler_stats);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(StatsActivity.this));
                    recyclerView.setAdapter(personaAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StatsActivity.this, "Ocurrió un error al momento de hacer la petición al servidor. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}

