package com.hotel.HamroKhaltiHotel.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.adapters.HotelRecyclerViewAdapter;
import com.hotel.HamroKhaltiHotel.models.Hotel;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class HotelFragment extends Fragment implements HotelRecyclerViewAdapter.HotelRecyclerViewClickListener {



private ArrayList<Hotel>hotelArrayList;
private RecyclerView recyclerView;
private HotelRecyclerViewAdapter hotelRecyclerViewAdapter;
private HotelRecyclerViewAdapter.HotelRecyclerViewClickListener hotelRecyclerViewClickListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_hotel, container, false);

        recyclerView = v.findViewById(R.id.listOfHotels);
        hotelArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        getListOfHotel();
        return v;

    }

    private void getListOfHotel() {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");

//        EditCustomerFragment editCustomerFragment = new EditCustomerFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("data",room);
//        editCustomerFragment.setArguments(bundle);

        Call<List<Hotel>> listCall = ApiClient.getHotelServices().getHotelDetail(token);
        listCall.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: getting data ");
                    hotelArrayList = new ArrayList<>(response.body());
                    setOnClickListener();
                    hotelRecyclerViewAdapter = new HotelRecyclerViewAdapter(getContext(), hotelArrayList, hotelRecyclerViewClickListener);
                    recyclerView.setAdapter(hotelRecyclerViewAdapter);
                }else{
                    Log.d(TAG, "onResponse: Something is wrong "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to thee server "+t.getMessage());
            }
        });


    }

    private void setOnClickListener() {
        hotelRecyclerViewClickListener = new HotelRecyclerViewAdapter.HotelRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                RoomFragment roomFragment = new RoomFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("hotelId", hotelArrayList.get(position).getId());
                roomFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, roomFragment).addToBackStack(null).commit();

            }
        };
    }

    @Override
    public void onClick(View v, int position) {

    }
}