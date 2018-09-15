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

import com.codeboard.htn.codeboard.R;
import com.codeboard.htn.codeboard.image.SnippetCaptureActivity;

public class CodeEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        EditText editText =  findViewById(R.id.codeEditor);
        editText.setText(bundle.getString(SnippetCaptureActivity.SCRIPT_KEY));



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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.execute_code) {
            Toast.makeText(getApplicationContext(),"Sending Code",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.save_code) {
            Toast.makeText(getApplicationContext(),"Saving Code",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
