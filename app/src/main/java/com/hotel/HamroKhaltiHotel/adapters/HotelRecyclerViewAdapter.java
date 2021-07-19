package com.hotel.HamroKhaltiHotel.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.Hotel;
import com.hotel.HamroKhaltiHotel.models.ProfilePicture;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HotelRecyclerViewAdapter extends RecyclerView.Adapter <HotelRecyclerViewAdapter.HotelViewHolder> {

    Context context;
    private ArrayList<Hotel> hotelArrayList;
    String fileName;
    ProfilePicture profilePicture;
    private HotelRecyclerViewClickListener hotelRecyclerViewClickListener;

    public HotelRecyclerViewAdapter(Context context, ArrayList<Hotel> hotelArrayList,HotelRecyclerViewClickListener hotelRecyclerViewClickListener) {
        this.context = context;
        this.hotelArrayList = hotelArrayList;
        this.hotelRecyclerViewClickListener = hotelRecyclerViewClickListener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_hotel_details, parent, false);
        HotelRecyclerViewAdapter.HotelViewHolder hotelViewHolder = new HotelRecyclerViewAdapter.HotelViewHolder(view,hotelRecyclerViewClickListener );
        return hotelViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {

//        Long userId = repliesOnDiscussions.get(position).getUser().getId();
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        holder.hotelName.setText(hotelArrayList.get(position).getHotelName());
        holder.hotelAddress.setText(hotelArrayList.get(position).getHotelAddress());
        holder.phone.setText(hotelArrayList.get(position).getPhone());
        // Log.d(TAG, "onBindViewHolder: user id "+hotelArrayList.get(position).getUser().getUserId());

        Call<ProfilePicture> call = ApiClient.getProfileService().getHotelProfilePicture(token, hotelArrayList.get(position).getUser().getUserId());
        call.enqueue(new Callback<ProfilePicture>() {
            @Override
            public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                if (response.isSuccessful()) {

                    // fileName = response.body().getFileName();
                    profilePicture = response.body();
                    Log.d(TAG, "onResponse: this is filename " + profilePicture.getFileName());
                    if (profilePicture.getFileName() != null) {
                        String url = "http://192.168.137.57:8080/api/downloadPicture/downloadFile/" + profilePicture.getFileName();
                        Log.d(TAG, "onResponse: got image");

                        Picasso.get().load(url).into(holder.hotelProfile);
                    } else {
                        Log.d(TAG, "onResponse: no image");
                        Picasso.get().load(R.drawable.logo).into(holder.hotelProfile);
                    }

                } else {
                    Log.d(TAG, "onResponse: Something is wrong " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProfilePicture> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server " + t.getMessage());
            }
        });



    }
//        fileName = profilePicture.getFileName();
       //Log.d(TAG, "onBindViewHolder: "+fileName);
       /* if(fileName!=null) {
            String url = "http://192.168.1.196:8080/api/downloadPicture/downloadFile" + fileName;

            Picasso.get().load(url).into(holder.hotelProfile);
        }else{
            Picasso.get().load(R.drawable.logo).into(holder.hotelProfile);
        }*/
       /* Call<ProfilePicture> call = ApiClient.getProfileService().loadProfilePicture(token, userId);
        call.enqueue(new Callback<ProfilePicture>() {
            @Override
            public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                if (response.isSuccessful()) {
                    try {
                        String fileName = response.body().getFileName();

                        String url = "http://192.168.1.102:8080/api/test/downloadFile/" + fileName;

                        Picasso.get().load(url).into(holder.userProfile);


                    } catch (Exception e) {
                        Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(context, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfilePicture> call, Throwable t) {
                // loadUploadDirTV.setText("Failure: "+t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Picasso.get().load(R.drawable.avatar).into(holder.userProfile);
            }
        });

*/


    @Override
    public int getItemCount() {
        return hotelArrayList.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView hotelProfile;
        private TextView hotelName, hotelAddress, phone;
        HotelRecyclerViewClickListener mHotelRecyclerViewClickListener;

        public HotelViewHolder(@NonNull  View itemView, HotelRecyclerViewClickListener mHotelRecyclerViewClickListener) {
            super(itemView);

            hotelProfile = (ImageView) itemView.findViewById(R.id.hotelPicture);
            hotelName = (TextView) itemView.findViewById(R.id.hotelName);
            hotelAddress = (TextView) itemView.findViewById(R.id.hotelAddress);
            phone = (TextView) itemView.findViewById(R.id.hotelPhone);
            this.mHotelRecyclerViewClickListener = mHotelRecyclerViewClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mHotelRecyclerViewClickListener.onClick(itemView, getAdapterPosition());
        }
    }

    public interface HotelRecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
