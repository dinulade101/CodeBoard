package com.codeboard.htn.codeboard.model;

import java.util.ArrayList;

public interface ScriptFacade {

    /**
     * Add a new script.
     */
    void addScript(Script script);

    /**
     * Load the user's scripts.
     */
    ArrayList<Script> getScripts();

    /**
     * Remove a script.
     */
    void deleteScript(Script script);

}
