package com.example.d2j_00.volleytutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editId, deleteId;
    TextView getArea;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = (EditText) findViewById(R.id.edit_id);
        deleteId = (EditText) findViewById(R.id.delete_id);
        getArea = (TextView) findViewById(R.id.get_area);
        queue = Volley.newRequestQueue(this);
    }

    public void getData(View v){
        getArea.setText("");
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET, "http://10.0.2.2:8000/books", null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response){
                        for(int i=0; i<response.length();i++){
                            try{
                                JSONObject data = response.getJSONObject(i);
                                getArea.append(data.getInt("id")+" - "+
                                                data.getString("title")+" - "+
                                                data.getString("author")+" - "+
                                                data.getInt("num_of_copy")+"\n");
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                }
        );
        queue.add(arrayRequest);
    }

    public void postData(View v){
        startActivity(new Intent(this, FormActivity.class));
    }

    public void updateData(View v){
        Intent it = new Intent(this, FormActivity.class);
        it.putExtra("edit_id", Integer.parseInt(editId.getText().toString()));
        startActivity(it);
    }

    public void deleteData(View v){

    }
}
