package com.example.empresa25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UsuarioActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText jetusuario,jetnombre,jetcorreo,jetclave;
    CheckBox jcbactivo;

    RequestQueue rq;
    JsonRequest jrq;

    String usr, nombre,correo,clave;
    byte sw;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        //ocultar barra, asociar objetos Java con objetos xml, iniciar cola de consultar

        getSupportActionBar().hide();
        jetusuario=findViewById(R.id.etusuario);
        jetcorreo=findViewById(R.id.etcorreo);
        jetclave=findViewById(R.id.etclave);
        jetnombre=findViewById(R.id.etnombre);
        jcbactivo=findViewById(R.id.cbactivo);

        rq = Volley.newRequestQueue(this);
        sw=0;
    }

    public void Guardar(View view){
        usr=jetusuario.getText().toString();
        nombre=jetnombre.getText().toString();
        correo=jetcorreo.getText().toString();
        clave=jetclave.getText().toString();
        if(usr.isEmpty() || nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos.", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else{
            if(sw==0){
                url="http://localhost:3306/WebServices/registrocorreo.php";
            }else{
                url="http://localhost:3306/WebServices/actualiza.php";
                sw=0;
            }

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Limpiar_campos();
                            Toast.makeText(getApplicationContext(), "Registro de usuario realizado!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Registro de usuario incorrecto!", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("usr",jetusuario.getText().toString().trim());
                    params.put("nombre", jetnombre.getText().toString().trim());
                    params.put("correo",jetcorreo.getText().toString().trim());
                    params.put("clave",jetclave.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
        }
    }

    public void Eliminar(View view){
        usr=jetusuario.getText().toString();
        if(usr.isEmpty()){
            Toast.makeText(this, "El usuario es requerido", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else{
            url="http://localhost:3306/WebServices/elimina.php";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Limpiar_campos();
                            Toast.makeText(getApplicationContext(), "Usuario eliminado!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Usuario no eliminado!", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("usr",jetusuario.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
        }
    }


    public void Anular(View view){
        usr=jetusuario.getText().toString();
        if(usr.isEmpty()){
            Toast.makeText(this, "El usuario es requerido", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else{
            url="http://localhost:3306/WebServices/anula.php";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Limpiar_campos();
                            Toast.makeText(getApplicationContext(), "Usuario anulado!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Usuario no anulado!", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("usr",jetusuario.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
        }
    }

    public void Consultar(View view){
        usr=jetusuario.getText().toString();
        if(usr.isEmpty()){
            Toast.makeText(this, "Usuario es requerido para la búsqueda", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else{
            url = "http://localhost:3306/WebService/consulta.php?usr="+usr;
            jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            rq.add(jrq);
        }
    }

    public void Limpiar(View view){
        Limpiar_campos();
    }

    public void Regresar(View view){
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }

    public void Limpiar_campos(){
        sw=0;
        jetcorreo.setText("");
        jetnombre.setText("");
        jetusuario.setText("");
        jetclave.setText("");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        sw=1;
        Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try{
            jsonObject = jsonArray.getJSONObject(0);//posición 0 del arreglo porque por acá entra si encuentra un registro, y hay uno solo, entonces es el de la posición 0
            jetnombre.setText(jsonObject.optString("nombre"));
            jetcorreo.setText(jsonObject.optString("correo"));
            if(jsonObject.optString("activo").equals("si")){
                jcbactivo.setChecked(true);
            }else{
                jcbactivo.setChecked(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}