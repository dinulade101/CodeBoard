package com.codeboard.htn.codeboard.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a program.
 */
public class Script implements Serializable {

    public static final String SCRIPT_KEY = "SERIALIZED_SCRIPT_OBJ";

    private String name;
    private String script;
    private Language language;

    public Script() {
        this("","",Language.PYTHON);
    }

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

    /**
     * Override equals method to ensure equality of serialized objects.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof Script) {
            Script other = (Script)obj;

            return  this.name.equals(other.name) &&
                    this.script.equals(other.script) &&
                    this.language == other.language;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31*result + name.hashCode();
        result = 31*result + script.hashCode();
        return result;
    }

    public enum Language {

        PYTHON("Python", ".py"),
        CPP("C++", ".cpp");

        private String name;
        private String fileExtension;

        Language(String name, String fileExtension) {
            this.name = name;
            this.fileExtension = fileExtension;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getName();
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

        public static List<String> getLanguageCategories() {
            List<String> categories = new ArrayList<>();
            for (Language l : Language.values()) {
                categories.add(l.toString());
            }
            return categories;
        }
    }
}
