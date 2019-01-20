package com.citylist.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.citylist.R;
import model_data.City;

import java.util.List;

public class CityFragment extends Fragment implements  CityContract{


    private CityViewModel cityViewModel;
    private RecyclerView cityRecyclerView;
    private OnCityClickListener mListener;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private TextView txtErrorDisplay;
    private TextView txtFetchingData;
    private ProgressBar progressBar;

    public CityFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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
        txtErrorDisplay = view.findViewById(R.id.txtErrorDisplay);
        txtFetchingData = view.findViewById(R.id.txtFetchingData);
        progressBar = view.findViewById(R.id.progressBar);


        cityViewModel = new CityViewModel(this.getContext());
        cityViewModel.setCityListListener(this);
        progressBar.setVisibility(View.VISIBLE);
        txtFetchingData.setVisibility(View.VISIBLE);
        cityViewModel.initializeList();

    }

    @Override
    public void onInitializedList(List<City> cityList) {
        progressBar.setVisibility(View.GONE);
        txtFetchingData.setVisibility(View.GONE);
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cityRecyclerView.setAdapter(new CityAdapter(cityList, mListener));
    }

    @Override
    public void onFilteredList(List<City> filteredCityList) {
        if(filteredCityList!=null){
            Log.i("filtered size: ",""+filteredCityList.size());
            ((CityAdapter) cityRecyclerView.getAdapter()).addItem(filteredCityList);
            cityRecyclerView.scrollToPosition(0);
            cityRecyclerView.setVisibility(View.VISIBLE);
            txtErrorDisplay.setVisibility(View.GONE);
        }else{
            cityRecyclerView.setVisibility(View.GONE);
            txtErrorDisplay.setVisibility(View.VISIBLE);

        }
    }

    public interface OnCityClickListener {
        void onCityItemClick(City item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    cityViewModel.filterCityList(newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}
