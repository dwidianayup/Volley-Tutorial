package com.example.d2j_00.volleytutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {
    private int editId;
    private EditText edtTitle, edtAuthor, edtNum;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtTitle = (EditText) findViewById(R.id.title);
        edtAuthor = (EditText) findViewById(R.id.author);
        edtNum = (EditText) findViewById(R.id.num_of_copy);

        Intent it = getIntent();
        editId = it.getIntExtra("edit_id", 0);
        queue = Volley.newRequestQueue(this);
    }

    public void saveData(View view) {
        int method = Request.Method.POST;
        String url = "http://10.0.2.2:8000/books";

        if(editId > 0){
            method = Request.Method.PUT;
            url = url+"/"+editId;
        }
        StringRequest sr = new StringRequest(method, url,
                new Response.Listener<String>(){
                    @Override
                   public void onResponse(String response){
                        Toast.makeText(getBaseContext(), "Data berhasil disimpan",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("title", edtTitle.getText().toString());
                params.put("author", edtAuthor.getText().toString());
                params.put("num_of_copy", edtNum.getText().toString());
                return params;
            }
        };
        queue.add(sr);
    }
}
