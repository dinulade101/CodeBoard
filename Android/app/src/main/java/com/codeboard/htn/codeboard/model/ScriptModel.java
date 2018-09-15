package com.codeboard.htn.codeboard.model;

import android.content.Context;

import com.codeboard.htn.codeboard.util.CodeBoard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observable;

public class ScriptModel extends Observable implements ScriptModelFacade {

    private static final String FILENAME = "codeboard_scripts.sav";
    private static ScriptModel MODEL_INSTANCE;

    private ArrayList<Script> scripts;

    @Override
    public void addScript(Script script) {
        scripts.add(script);
        writeScriptData();
    }

    @Override
    public ArrayList<Script> getScripts() {
        if (scripts == null) {
            loadScriptData();
        }
        return scripts;
    }

    @Override
    public void deleteScript(Script script) {
    }

    public static ScriptModelFacade getModel() {
        if (MODEL_INSTANCE == null) {
            MODEL_INSTANCE = new ScriptModel();
        }
        return MODEL_INSTANCE;
    }

    private ScriptModel() {
        loadScriptData();
    }

    /**
     * Read the scripts from device storage.
     */
    private void loadScriptData() {
        // TODO: Read scripts from memory.
        scripts = new ArrayList<>();

       /* uncomment the line below if emulator is throwing a FileNotFoundException
        the file from internal storage. After running once, comment the line
        out again and the emulator issue should be resolved */
        // deleteFile();

        try {

            FileInputStream fis = CodeBoard.getContext().openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<Script>>() {}.getType();

            // deserialize counters into an array list
            scripts = gson.fromJson(in, listType);

        }
        catch (FileNotFoundException e) {}
    }

    /**
     * Write scripts to device storage.
     */
    private void writeScriptData() {
        try {
            // MODE_PRIVATE == Overwrite
            FileOutputStream fos = CodeBoard.getContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            // use GSON library to serialize array of counter objects
            Gson gson = new Gson();
            gson.toJson(scripts, out);
            out.flush();

            // close file stream
            fos.close();

        }
        catch (FileNotFoundException e) {}
        catch (IOException e) {}

        // Notify observers data has changed.
        setChanged();
        notifyObservers();
    }
}

