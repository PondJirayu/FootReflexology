package jirayu.pond.footreflexology.manager;

/**
 * Created by lp700 on 23/10/2559.
 */

public class StringsManager {

    private String word;

    public StringsManager() {

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordNoneNumberAndNoneWhiteSpace() {
        return word.substring(3).trim();
    }
}
