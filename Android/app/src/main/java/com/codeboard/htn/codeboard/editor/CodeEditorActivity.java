package com.codeboard.htn.codeboard.editor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codeboard.htn.codeboard.R;
import com.codeboard.htn.codeboard.image.SnippetCaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class CodeEditorActivity extends AppCompatActivity {
    //currently a test url
    final static String URL = "https://httpbin.org/get";
    RequestQueue requestQueue;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        editText =  findViewById(R.id.codeEditor);
        editText.setText(bundle.getString(SnippetCaptureActivity.SCRIPT_KEY));

        requestQueue = Volley.newRequestQueue(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_code_editor, menu);
        return true;
    }
    public void onExecuteClick(View v){
        Log.d("help","teest");
        Toast.makeText(getApplicationContext(),"Sending Code",Toast.LENGTH_LONG).show();

    }
    public void makeVolleyRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("HTTP RESPONSE","Response: " + response.toString());
                        String origin;
                        try{
                            origin = response.getString("origin");
                        }catch(JSONException e){
                            origin = "JSON FAIL:" + e.getMessage();
                        }

                        editText.setText("HTTP succes: " + origin);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HTTP RESPONSE", "FAIL");
                        editText.setText("HTTP FAIL:" +error.getMessage());

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.execute_code) {
            Toast.makeText(getApplicationContext(),"Sending Code",Toast.LENGTH_SHORT).show();
            makeVolleyRequest();
            return true;
        }
        if (id == R.id.save_code) {
            Toast.makeText(getApplicationContext(),"Saving Code",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
