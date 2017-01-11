package jirayu.pond.footreflexology.manager;

/**
 * Created by lp700 on 23/10/2559.
 */

public class StringsManager {

    /************
     * Variables
     ************/

    private String word;

    /************
     * Functions
     ************/

    public StringsManager() {

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordNoneNumberAndNoneWhiteSpace() {
        return getWord().substring(3).trim();
    }

    public String getChangeBirthDate() {
        return  word.substring(8) + "-" + word.substring(5, 7) + "-" + word.substring(0, 4);
    }
}
