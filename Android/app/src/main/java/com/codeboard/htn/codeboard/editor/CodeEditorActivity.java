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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codeboard.htn.codeboard.R;
import com.codeboard.htn.codeboard.model.Script;
import com.codeboard.htn.codeboard.model.ScriptModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CodeEditorActivity extends AppCompatActivity {
    //currently a test url
    final static String URL = "http://159.203.4.116:8080/execute/";
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editText =  findViewById(R.id.scriptContentET);
        scriptName = findViewById(R.id.scriptNameET);

        editText.setText(script.getScript());
        scriptName.setText(script.getName());
        editText.setText(script.getScript());
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
       
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP RESPONSE","Response: " + response.toString());
                        try{
                            JSONObject jsonResp = new JSONObject(response);
                            output.setVisibility(View.VISIBLE);
                            output.setText("Output: " + jsonResp.getString("output"));
                        }catch(Exception e){
                            Log.d("JSON error:", e.getMessage());
                            output.setText("JSON ERROR");
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HTTP RESPONSE", "FAIL");
                        output.setVisibility(View.VISIBLE);
                        output.setText("HTTP FAIL:" +error.getMessage());

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parameters = new HashMap<String,String>();
                parameters.put("code",editText.getText().toString());
                parameters.put("language",((TextView) languageSpinner.getSelectedView()).getText().toString());

                return parameters;
            }

        };
        requestQueue.add(stringRequest);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)  {
            case R.id.execute_code:
                Toast.makeText(getApplicationContext(),"Sending Code",Toast.LENGTH_SHORT).show();
                makeVolleyRequest();
            case R.id.save_code:
                Toast.makeText(getApplicationContext(),"Saving Code",Toast.LENGTH_SHORT).show();
                ScriptModel.getModel().addScript(script);
                return true;
            case R.id.deleteBtn:
                ScriptModel.getModel().deleteScript(script);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
