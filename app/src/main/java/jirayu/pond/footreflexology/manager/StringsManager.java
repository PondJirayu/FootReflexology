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
        return  getWord().substring(8) + "-" + getWord().substring(5, 7) + "-" + getWord().substring(0, 4);
    }

    public String getDay() {
        return getWord().substring(8);
    }

    public String getMonth() {
        return getWord().substring(5, 7);
    }

    public String getYear() {
        return getWord().substring(0, 4);
    }
}
