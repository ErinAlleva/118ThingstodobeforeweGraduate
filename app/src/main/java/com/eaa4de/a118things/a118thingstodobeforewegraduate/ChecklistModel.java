package com.eaa4de.a118things.a118thingstodobeforewegraduate;

import java.io.Serializable;

public class ChecklistModel implements Serializable {
    String name;
    int value;
    /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    ChecklistModel(String name, int value) {
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }
    public void setValue(int x){
        this.value = x;
    }


}