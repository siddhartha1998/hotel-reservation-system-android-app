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

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.adapters.HotelRecyclerViewAdapter;
import com.hotel.HamroKhaltiHotel.adapters.RoomRecyclerViewAdapter;
import com.hotel.HamroKhaltiHotel.models.Hotel;
import com.hotel.HamroKhaltiHotel.models.Room;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class RoomFragment extends Fragment implements RoomRecyclerViewAdapter.RoomRecyclerClickListener {

    private ArrayList<Room> roomArrayList;
    private RecyclerView recyclerView;
    private RoomRecyclerViewAdapter roomRecyclerViewAdapter;
    private RoomRecyclerViewAdapter.RoomRecyclerClickListener roomRecyclerClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_room_detail, container, false);

        recyclerView = v.findViewById(R.id.listOfRoomRecyclerView);
        roomArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        getListOfRoom();

        return v;
    }

    private void getListOfRoom(){

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Long hotelId = getArguments().getLong("hotelId",0);
        Call<List<Room>> listCall = ApiClient.getRoomServices().getRoomDetail(token,hotelId);
        listCall.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: getting data ");
                    roomArrayList = new ArrayList<>(response.body());
                    setOnClickListener();
                    roomRecyclerViewAdapter = new RoomRecyclerViewAdapter(getContext(), roomArrayList,roomRecyclerClickListener);
                    recyclerView.setAdapter(roomRecyclerViewAdapter);
                }else{
                    Log.d(TAG, "onResponse: Something is wrong "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to thee server "+t.getMessage());
            }
        });
    }

    private void setOnClickListener() {
        roomRecyclerClickListener = new RoomRecyclerViewAdapter.RoomRecyclerClickListener() {
            @Override
            public void onClick(View v, int position) {
                ReserveRoomFragment reserveRoomFragment = new ReserveRoomFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("roomId",roomArrayList.get(position).getId());
                reserveRoomFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, reserveRoomFragment).addToBackStack(null).commit();
            }
        };

    }

    @Override
    public void onClick(View v, int position) {

    }
}