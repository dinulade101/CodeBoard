package com.codeboard.htn.codeboard.explorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codeboard.htn.codeboard.R;
import com.codeboard.htn.codeboard.image.SnippetCaptureActivity;

public class ScriptExplorerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_explorer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.script_root_container);
        for(int i=0; i<200; i++){
            View view = getLayoutInflater().inflate(R.layout.title_row, null);
            ((TextView) view.findViewById(R.id.script_title)).setText("MyScript " + i);
            linearLayout.addView(view);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareForSnippet();
            }
        });
    }

    private void prepareForSnippet() {
        startActivity(new Intent(this, SnippetCaptureActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_script_explorer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Toast.makeText(getApplicationContext(),"Sending Code",Toast.LENGTH_SHORT);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return false;
        }


        return super.onOptionsItemSelected(item);
    }
}