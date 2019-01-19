package com.citylist.ui;

import model_data.City;

import java.util.List;

public interface CityContract {
    void onInitializedList(List<City> cityList);
    void onFilteredList(List<City> filteredCityList);
}
