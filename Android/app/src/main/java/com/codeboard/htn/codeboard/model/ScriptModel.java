package com.codeboard.htn.codeboard.model;

import java.util.ArrayList;
import java.util.Observable;

public class ScriptModel extends Observable implements ScriptFacade {

    private static final String FILENAME = "codeboard_scripts.sav";
    private static ScriptModel MODEL_INSTANCE;

    private ArrayList<Script> scripts;

    @Override
    public void addScript(Script script) {
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

    public static ScriptModel getModel() {
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
        scripts.add(new Script("Hello World","def: print('hello world))", Script.Language.PYTHON));
    }

    /**
     * Write scripts to device storage.
     */
    private void writeScriptData() {
        // TODO: Write script data to device storage.
    }
}

