package com.rc.designpattern.pattern.behavioural.memento;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class CareTaker {

    private static Map<String, GenericMemento> mementoList;
    private static CareTaker careTaker;

    private CareTaker() {
    }

    public static CareTaker getInstance(){
        if(careTaker == null){
            careTaker = new CareTaker();
            mementoList = new HashMap<>();
        }
        return careTaker;
    }

    public void add(String key, GenericMemento state){
        mementoList.put(key, state);
    }

    public GenericMemento get(String key){
        return mementoList.get(key);
    }

//    public void add(GenericMemento state, int currentState) {
//        if (currentState == mementoList.size()) {
//            mementoList.add(state);
//            return;
//        }
//
//        if (currentState - 1 < mementoList.size()) {
//            mementoList.set(currentState, state);
//        }
//    }
//
//    public GenericMemento get(int index) {
//        if (index>0 && index < mementoList.size()) {
//            return mementoList.get(index);
//        }
//
//        return null;
//    }
//
//    public void update() {
//
//    }
}