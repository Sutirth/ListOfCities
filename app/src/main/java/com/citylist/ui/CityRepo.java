package com.citylist.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.*;
import model_data.City;
import util.JsonFileReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CityRepo {

    private Context mContext;
    private List<City> citiesList;
    private Gson gson;

    public CityRepo(Context context){
        this.mContext = context;
        gson = new Gson();
    }

    public void fetchListFromJsonFile(final CityContract listener,final String fileName){
        if(citiesList!=null){
            listener.onInitializedList(citiesList);
        }else{
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    String loadData = JsonFileReader.parseJsonFileFromAssets(mContext, fileName);

                    prepareJson(loadData);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Log.i("city size"," "+citiesList.size());
                    listener.onInitializedList(citiesList);

                }
            }.execute();
        }
    }

    private void prepareJson(String loadData) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(loadData);
        JsonArray jsonCityArray = element.getAsJsonArray();


        citiesList =  new ArrayList<>(jsonCityArray.size());

        for (JsonElement cityElement : jsonCityArray){
            JsonObject cityObject = cityElement.getAsJsonObject();
            City city = gson.fromJson(cityObject, City.class);
            citiesList.add(city);
        }

        sortList(citiesList);
    }


    private void sortList(List<City> citiesList){
        Collections.sort(citiesList, new Comparator<City>() {
            @Override
            public int compare(City city1, City city2) {
                int compare = city1.getName().toLowerCase().compareTo(city2.getName().toLowerCase());

                if(compare == 0){
                    compare = city1.getCountry().compareTo(city2.getCountry());
                }
                return compare;
            }
        });
    }


    public List<City> getFilteredCityList(int startIndex, int endIndex){
        return citiesList.subList(startIndex, endIndex+1);
    }

    public List<City> getCitiesList(){
        return citiesList;
    }

}



