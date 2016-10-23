package jirayu.pond.footreflexology.manager;

/**
 * Created by lp700 on 23/10/2559.
 */

public class WordsManager {

    private String word;

    public WordsManager() {

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getNewWord() {
        String[] newWord = word.split(".");
        return newWord[1];
    }
}
