package com.citylist.ui;

import android.content.Context;
import model_data.City;

import java.util.List;

public class CityViewModel implements CityContract {

    private CityRepo cityRepo;
    private CityContract cityListListener;

    public CityViewModel(Context context){
        cityRepo = new CityRepo(context);
    }

    public void initializeList(){
        cityRepo.fetchListFromJsonFile(this,"cities.json");
    }

    public void setCityListListener(CityContract cityListListener){
        this.cityListListener = cityListListener;
    }

    @Override
    public void onInitializedList(List<City> cityList) {
        if(cityListListener!=null){
            cityListListener.onInitializedList(cityList);
        }
    }

    @Override
    public void onFilteredList(List<City> filteredCityList) {
        if(cityListListener != null){
            cityListListener.onFilteredList(filteredCityList);
        }
    }



}
