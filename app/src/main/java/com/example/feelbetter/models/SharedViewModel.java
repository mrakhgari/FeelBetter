package com.example.feelbetter.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> doneList = new MutableLiveData<>();

    public void addData(String data){
        ArrayList temp = doneList.getValue();
                temp.add(data);
        doneList.setValue(temp);
    }

    public LiveData<ArrayList<String>> getDoneList(){
        return doneList;
    }
}
