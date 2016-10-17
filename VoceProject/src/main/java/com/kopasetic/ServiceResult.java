package com.kopasetic;

/**
 * Created by Mark on 10/16/2016.
 */
public class ServiceResult {
    public String wordsIn;
    public String result;
    public String show;

    public ServiceResult(){}
    public ServiceResult(String wordsIn, String result, String show) {
        this.wordsIn = wordsIn;
        this.result = result;
        this.show = show;
    }

    public String getWordsIn() {
        return wordsIn;
    }

    public void setWordsIn(String wordsIn) {
        this.wordsIn = wordsIn;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
