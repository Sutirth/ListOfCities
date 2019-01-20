package com.citylist.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.citylist.R;
import model_data.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private Context mContext;
    private List<City> cityList;
    private final CityFragment.OnCityClickListener mListener;

    public CityAdapter(List<City> listCity, CityFragment.OnCityClickListener listener){
        cityList= listCity;
        mListener = listener;
    }

    public void addItem(List<City> listCity){
        cityList = listCity;
    }

    public void clearList(){
        cityList.clear();
    }


    @NonNull
    @Override
    public CityAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_city_row_layout, viewGroup,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.CityViewHolder viewHolder, int i) {
            viewHolder.txtCityDetails.setText(cityList.get(i).getCityWithCountry());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


    public class CityViewHolder extends RecyclerView.ViewHolder{

        private final View mView;
        private final TextView txtCityDetails;

        public CityViewHolder(View view){
            super(view);
            mView = view;
            txtCityDetails = view.findViewById(R.id.txtCityName);

        }
    }
}
