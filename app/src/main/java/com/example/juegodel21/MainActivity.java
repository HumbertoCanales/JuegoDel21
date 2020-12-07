package com.example.juegodel21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String URL_stats = "https://www.ramiro174.com/api/obtener/numero";
    private static final String URL_obtener = "https://www.ramiro174.com/api/numero";
    private static final String URL_enviar = "https://www.ramiro174.com/api/enviar/numero";
    RequestQueue requestQueue;
    private int num = 0;
    private int sum = 0;
    private TextView resultado;
    private Random random;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        resultado = findViewById(R.id.txv_num);
        layout = findViewById(R.id.linear_cartas);
        random = new Random();
        Button btnPedir = findViewById(R.id.btn_pedir);
        btnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedirNumero();
            }
        });
        Button btnPlantar = findViewById(R.id.btn_plantar);
        btnPlantar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarNumero();
            }
        });
        Button btnRestart = findViewById(R.id.btn_restart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciar();
            }
        });
        ImageView ivStats = findViewById(R.id.iv_stats);
        ivStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), StatsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void pedirNumero(){
        if(sum < 21) {
            JsonObjectRequest obtNum = new JsonObjectRequest(Request.Method.GET, URL_obtener, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        num = response.getInt("numero");
                        sum += num;
                        layout.addView(imagenCarta(num));
                        resultado.setText(String.valueOf(sum));
                        if(sum == 21){
                            Toast.makeText(MainActivity.this, "Blackjack!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Ocurrió un error al momento de hacer la petición al servidor. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
                }
            });
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(obtNum);
        }else{
            Toast.makeText(MainActivity.this,"Superaste el limite de 21. Envía tu resultado o reinicia el juego.",Toast.LENGTH_SHORT).show();
        }
    }

    private void enviarNumero(){
            JSONObject jugador=new JSONObject();
            try {
                jugador.put("nombre", "Humberto");
                jugador.put("numero", sum);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest envNum= new JsonObjectRequest(Request.Method.POST, URL_enviar, jugador, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(MainActivity.this,"Resultado enviado correctamente. Revisa las estadísticas",Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,"Ocurrió un error al momento de hacer la petición al servidor. Inténtalo de nuevo.",Toast.LENGTH_SHORT).show();
                }
            });
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(envNum);
            reiniciar();
    }

    private void reiniciar(){
        sum = 0;
        resultado.setText(String.valueOf(sum));
        if(layout.getChildCount()>0)
        {
            layout.removeAllViews();
        }
    }

    private ImageView imagenCarta(int valor)
    {
        ImageView img = new ImageView(this);
        switch (valor)
        {
            case 1:
            case 11:
                img.setImageResource(R.drawable.c1);
                break;
            case 2:
                img.setImageResource(R.drawable.c2);
                break;
            case 3:
                img.setImageResource(R.drawable.c3);
                break;
            case 4:
                img.setImageResource(R.drawable.c4);
                break;
            case 5:
                img.setImageResource(R.drawable.c5);
                break;
            case 6:
                img.setImageResource(R.drawable.c6);
                break;
            case 7:
                img.setImageResource(R.drawable.c7);
                break;
            case 8:
                img.setImageResource(R.drawable.c8);
                break;
            case 9:
                img.setImageResource(R.drawable.c9);
                break;
            case 10:
                /*int rn = random.nextInt(11-13) + 11 + 13;
                if(rn == 11) {
                    img.setImageResource(R.drawable.c11);
                }else if(rn == 12) {
                    img.setImageResource(R.drawable.c12);
                }else {
                    img.setImageResource(R.drawable.c13);
                }*/
                img.setImageResource(R.drawable.c10);
                break;
        }
        img.setLayoutParams(new LinearLayout.LayoutParams(300,370));
        return img;
    }
}