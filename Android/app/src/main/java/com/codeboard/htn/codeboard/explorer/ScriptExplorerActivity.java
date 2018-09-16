package com.codeboard.htn.codeboard.explorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codeboard.htn.codeboard.R;
import com.codeboard.htn.codeboard.editor.CodeEditorActivity;
import com.codeboard.htn.codeboard.image.SnippetCaptureActivity;
import com.codeboard.htn.codeboard.model.Script;
import com.codeboard.htn.codeboard.model.ScriptModel;
import com.codeboard.htn.codeboard.util.CodeBoard;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ScriptExplorerActivity extends AppCompatActivity implements Observer {

    private ScriptExplorerAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_explorer);

        recyclerView = findViewById(R.id.scriptRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ScriptExplorerAdapter(ScriptModel.getModel().getScripts(), this);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareForSnippet();
            }
        });
    }

    /**
     * Jump to snippet capture activity.
     */
    public void prepareForSnippet() {
        startActivity(new Intent(this, SnippetCaptureActivity.class));
    }

    /**
     * Jump to editor activity.
     */
    public void displaySnippet(Script selectedScript) {
        Intent intent = new Intent(this, CodeEditorActivity.class);
        intent.putExtra(Script.SCRIPT_KEY, selectedScript);
        startActivity(intent);
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

    @Override
    public void update(Observable o, Object arg) {
        adapter.notifyDataSetChanged();
    }
}