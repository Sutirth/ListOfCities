package com.citylist.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.citylist.R;
import model_data.City;

import java.util.List;

public class CityFragment extends Fragment implements  CityContract{


    private CityViewModel cityViewModel;
    private RecyclerView cityRecyclerView;
    private OnCityClickListener mListener;
    public CityFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_layout, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCityClickListener) {
            mListener = (OnCityClickListener) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        cityRecyclerView = view.findViewById(R.id.cityRecyclerView);

        cityViewModel = new CityViewModel(this.getContext());
        cityViewModel.setCityListListener(this);
        cityViewModel.initializeList();

    }

    @Override
    public void onInitializedList(List<City> cityList) {
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cityRecyclerView.setAdapter(new CityAdapter(cityList, mListener));
    }

    @Override
    public void onFilteredList(List<City> filteredCityList) {

    }

    public interface OnCityClickListener {
        void onCityItemClick(City item);
    }
}
