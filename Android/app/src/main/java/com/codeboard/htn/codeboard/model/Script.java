package com.codeboard.htn.codeboard.model;

import java.io.Serializable;

/**
 * Represents a program.
 */
public class Script implements Serializable {

    private String name;
    private String script;
    private Language language;

    public Script(String name, String script, Language language) {
        this.name = name;

        this.script = script;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public enum Language {

        PYTHON("Python", ".py"),
        JAVA("Java", ".java");

        private String name;
        private String fileExtension;

        Language(String name, String fileExtension) {
            this.name = name;
            this.fileExtension = fileExtension;
        }

        public String getName() {
            return name;
        }

        public String getFileExtension() {
            return fileExtension;
        }

        public static Language getLanguageForValue(String name) {
            for (Language l : Language.values()) {
                if (l.equals(name)) {
                    return l;
                }
            }
            return null;
        }
    }
}
