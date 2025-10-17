package com.ncfxy.learnDemo.designPattern.singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a big object to test Singleton performance.
 */
public class OneBigObject {
    private List<String> strings;
    public OneBigObject(){
        strings = new ArrayList<>();
        for(Integer i = 0;i < 1000000;i++){
            strings.add(i.toString());
        }
    }
}
