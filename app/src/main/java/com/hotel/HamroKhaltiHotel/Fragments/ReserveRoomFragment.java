package com.hotel.HamroKhaltiHotel.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.adapters.RoomReservationRecyclerViewAdapter;
import com.hotel.HamroKhaltiHotel.models.RoomPicture;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ReserveRoomFragment extends Fragment {
    private Button btnReserve;
    private RecyclerView recyclerView;
    private ArrayList<RoomPicture> roomPictureArrayList;
    private RoomReservationRecyclerViewAdapter roomReservationRecyclerViewAdapter;
    Long roomId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reserve_room, container, false);

        recyclerView = v.findViewById(R.id.listOfRoomPicture);
        btnReserve = v.findViewById(R.id.btnReserve);

        roomId = getArguments().getLong("roomId",0);

        roomPictureArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        getListOfRoomPicture();

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReservationFragment reservationFragment = new ReservationFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",roomPictureArrayList.get(0).getRoom());
                reservationFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,reservationFragment).addToBackStack(null).commit();
            }
        });

        return v;
    }

    private void getListOfRoomPicture() {


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");


        Call<List<RoomPicture>> call = ApiClient.getRoomServices().getRoomProfilePicture(token,roomId);
        call.enqueue(new Callback<List<RoomPicture>>() {
            @Override
            public void onResponse(Call<List<RoomPicture>> call, Response<List<RoomPicture>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: getting room pictures");

                    roomPictureArrayList = new ArrayList<>(response.body());


                   // Log.d(TAG, "onResponse: Room Detail "+roomPictureArrayList.get(0).getRoom().getRoomNumber());

                    roomReservationRecyclerViewAdapter = new RoomReservationRecyclerViewAdapter(getContext(),roomPictureArrayList);
                    recyclerView.setAdapter(roomReservationRecyclerViewAdapter);
                }else{
                    Log.d(TAG, "onResponse: Something is wrong "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RoomPicture>> call, Throwable t) {
                Log.d(TAG, "onFailure: Failure to connect to server "+t.getMessage());
            }
        });

    }
}
