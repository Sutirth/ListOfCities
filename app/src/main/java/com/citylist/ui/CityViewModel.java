package com.citylist.ui;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import model_data.City;
import model_data.Index;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CityViewModel implements CityContract {

    private CityRepo cityRepo;
    private CityContract cityListListener;

    private ArrayMap<Character, Index> dataIndex;
    private Stack<Index> indexStack;

    public CityViewModel(Context context){

        cityRepo = new CityRepo(context);
        indexStack = new Stack<>();
    }

    public void initializeList(){
        cityRepo.fetchListFromJsonFile(this,"cities.json");
    }

    public void setCityListListener(CityContract cityListListener){
        this.cityListListener = cityListListener;
    }

    public void filterCityList(String query){
        onFilteredList(filterListQuery(query));
    }

    @Override
    public void onInitializedList(List<City> cityList) {
        createIndexForCityData((ArrayList<City>) cityList);
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


    public List<City> filterListQuery(String textQuery){
        textQuery = textQuery.toLowerCase();

        if(textQuery.length()==0 || textQuery == null){
            return cityRepo.getCitiesList();
        }

        if(textQuery.length() < indexStack.size()){
            while (textQuery.length() < indexStack.size()){
                indexStack.pop();
            }
        }

        if (textQuery.length() == 1) {
            if (dataIndex.containsKey(textQuery.charAt(0))) {
                indexStack.push(dataIndex.get(textQuery.charAt(0)));
            } else {
                return null;
            }
        } else {
            Index i = findQuery(textQuery);
            if (i != null) {
                indexStack.push(i);
            } else {
                return null;
            }
        }

        if (indexStack.peek().startIndex == indexStack.peek().endIndex) {
            ArrayList<City> list = new ArrayList<City>();
            list.add(cityRepo.getCitiesList().get(indexStack.peek().startIndex));
            return list;
        } else {
            Index index = findQuery(textQuery);
            return cityRepo.getFilteredCityList(index.startIndex, index.endIndex);
        }

    }




    public void createIndexForCityData(ArrayList<City> cityList){
        dataIndex = new ArrayMap<>();
        char lastCharacter = cityList.get(0).getName().charAt(0);
        int tempIndex =0;

        for(int i =0 ; i < cityList.size() ; i++){
            if(Character.toLowerCase(cityList.get(i).getName().charAt(0))!= Character.toLowerCase(lastCharacter)){
                Index index = new Index();
                index.startIndex = tempIndex;
                index.endIndex = i - 1;
                dataIndex.put(Character.toLowerCase(lastCharacter), index);
                tempIndex = i;
                lastCharacter = cityList.get(i).getName().charAt(0);
            }
        }


        if(!dataIndex.containsKey(Character.toLowerCase(lastCharacter))){
            Index index = new Index();
            index.startIndex = tempIndex;
            index.endIndex = cityList.size() - 1;
            dataIndex.put(Character.toLowerCase(lastCharacter), index);
        }

    }

   public Index findQuery(String query){

        boolean isFound = false;
        Index index = null;
        query= query.toLowerCase();

        for(int i = indexStack.peek().startIndex; i<= indexStack.peek().endIndex ; i++ ){
            if(!isFound && cityRepo.getCitiesList().get(i).getCityWithCountry().toLowerCase().startsWith(query)){
                index = new Index();
                index.startIndex = i;
                isFound = true;
            }

            if(isFound && cityRepo.getCitiesList().get(i).getCityWithCountry().toLowerCase().startsWith(query)){
                index.endIndex = i-1;
                break;
            }


            if (index != null && i == (indexStack.peek().endIndex) && index.endIndex == 0) {
                index.endIndex = i;
            }

        }

        return index;
    }

}
