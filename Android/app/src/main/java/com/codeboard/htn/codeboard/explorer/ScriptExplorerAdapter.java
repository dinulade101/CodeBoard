package com.codeboard.htn.codeboard.explorer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codeboard.htn.codeboard.R;
import com.codeboard.htn.codeboard.model.Script;

import java.util.ArrayList;

public class ScriptExplorerAdapter extends RecyclerView.Adapter<ScriptExplorerAdapter.ScriptViewHolder> {

    private ArrayList<Script> scripts;
    private ScriptExplorerActivity parent;

    public ScriptExplorerAdapter(ArrayList<Script> scripts, ScriptExplorerActivity parent) {
        this.scripts = scripts;
        this.parent = parent;
    }

    public class ScriptViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView fileExtension;


        public ScriptViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            name = view.findViewById(R.id.scriptName);
            fileExtension = view.findViewById(R.id.fileExtension);
        }

        public void bindScript(Script script) {
            name.setText(script.getName());
            fileExtension.setText(script.getLanguage().getFileExtension());
        }

        @Override
        public void onClick(View view) {
            // TODO: Notify parent activity of script to show in editor.
        }
    }

    @Override
    public ScriptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View scriptView = LayoutInflater.from(parent.getContext()).inflate(R.layout.script_thumbnail, parent, false);
        return new ScriptViewHolder(scriptView);
    }

    @Override
    public void onBindViewHolder(ScriptViewHolder vh, int pos) {
        vh.bindScript(scripts.get(pos));
    }

    @Override
    public int getItemCount() {
        return scripts.size();
    }
}
