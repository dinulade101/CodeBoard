package com.codeboard.htn.codeboard.editor;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codeboard.htn.codeboard.R;
import com.codeboard.htn.codeboard.model.Script;
import com.codeboard.htn.codeboard.model.ScriptModel;

import org.json.JSONException;
import org.json.JSONObject;

public class CodeEditorActivity extends AppCompatActivity {
    //currently a test url
    final static String URL = "https://codeboarddj.azurewebsites.net/";
    RequestQueue requestQueue;

    private EditText editText;
    private EditText scriptName;
    private TextView output;
    private Spinner languageSpinner;
    private Script script = new Script();
    private ArrayAdapter<String> languageAdapter;
    private String scriptText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_editor);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.get(Script.SCRIPT_KEY) != null) {
            script = (Script)bundle.get(Script.SCRIPT_KEY);
        }

        languageSpinner = findViewById(R.id.languageSpinner);
        languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Script.Language.getLanguageCategories());
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageAdapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Script.Language selected = Script.Language.getLanguageForValue(parent.getItemAtPosition(position).toString());
                script.setLanguage(selected != null ? selected : Script.Language.PYTHON);
                Toast.makeText(getApplicationContext(),"Script lang is: " + script.getLanguage().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editText =  findViewById(R.id.scriptContentET);
        scriptName = findViewById(R.id.scriptNameET);

        editText.setText(script.getScript());
        scriptName.setText(script.getName());
        scriptText = bundle.getString(Script.SCRIPT_KEY);
        editText.setText(scriptText != null ? scriptText : "");
        output = findViewById(R.id.outputTV);

        Typeface codeFont = Typeface.createFromAsset(getAssets(),"fonts/cmuntt.ttf");
        editText.setTypeface(codeFont);
        output.setTypeface(codeFont);

        requestQueue = Volley.newRequestQueue(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_code_editor, menu);
        return true;
    }
    public void onExecuteClick(View v){
        Log.d("help","test");
        Toast.makeText(getApplicationContext(),"Sending Code",Toast.LENGTH_LONG).show();

    }
    public void makeVolleyRequest(){
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("language", ((TextView) languageSpinner.getSelectedView()).getText());
            requestObject.put("code", editText.getText().toString());
        }catch(JSONException e){
            Log.d("JSON FAIL:", e.getMessage());
        }
        Log.d("JSON SUCCESS:",requestObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL, requestObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("HTTP RESPONSE","Response: " + response.toString());
                        String origin;
                        try{
                            origin = response.getString("origin");
                        }catch(JSONException e){
                            origin = "JSON FAIL:" + e.getMessage();
                        }
                        output.setVisibility(View.VISIBLE);
                        output.setText("HTTP succes: " + origin);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HTTP RESPONSE", "FAIL");
                        output.setVisibility(View.VISIBLE);
                        output.setText("HTTP FAIL:" +error.getMessage());

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
            ScriptModel.getModel().addScript(script);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
