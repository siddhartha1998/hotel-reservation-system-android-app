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
import com.hotel.HamroKhaltiHotel.models.Room;
import com.hotel.HamroKhaltiHotel.models.RoomPicture;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.RoomViewHolder> {

    Context context;
    private ArrayList<Room> roomArrayList;
    RoomPicture roomPicture;
    private RoomRecyclerClickListener roomRecyclerClickListener;

    public RoomRecyclerViewAdapter(Context context, ArrayList<Room> roomArrayList, RoomRecyclerClickListener roomRecyclerClickListener) {
        this.context = context;
        this.roomArrayList = roomArrayList;
        this.roomRecyclerClickListener = roomRecyclerClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_room_details, parent, false);
        RoomRecyclerViewAdapter.RoomViewHolder roomViewHolder = new RoomRecyclerViewAdapter.RoomViewHolder(view,roomRecyclerClickListener);
        return roomViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoomViewHolder holder, int position) {

        SharedPreferences sharedPreferences = this.context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        holder.hotelName.setText(roomArrayList.get(position).getHotel().getHotelName());
        holder.roomNumber.setText(roomArrayList.get(position).getRoomNumber().toString());
        holder.roomType.setText(roomArrayList.get(position).getRoomType());

        /*Call<RoomPicture> call = ApiClient.getRoomServices().getRoomProfilePicture(token,roomArrayList.get(position).getId());
        call.enqueue(new Callback<RoomPicture>() {
            @Override
            public void onResponse(Call<RoomPicture> call, Response<RoomPicture> response) {
                if(response.isSuccessful()) {

                    // fileName = response.body().getFileName();
                    roomPicture = response.body();
                    Log.d(TAG, "onResponse: this is filename "+roomPicture.getFileName());
                    if(roomPicture.getFileName()!=null) {
                        String url = "http://192.168.43.39:8080/api/downloadPicture/downloadFile/"+roomPicture.getFileName();
                        Log.d(TAG, "onResponse: got image");

                        Picasso.get().load(url).into(holder.roomProfile);
                    }else{
                        Log.d(TAG, "onResponse: no image");
                        Picasso.get().load(R.drawable.logo).into(holder.roomProfile);
                    }

                }else{
                    Log.d(TAG, "onResponse: Something is wrong "+response.code());
                }
            }

            @Override
            public void onFailure(Call<RoomPicture> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server "+t.getMessage());
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return roomArrayList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView roomProfile;
        private TextView hotelName, roomNumber, roomType;
        private RoomRecyclerClickListener mRoomRecyclerClickListener;

        public RoomViewHolder(@NonNull  View itemView,RoomRecyclerClickListener mRoomRecyclerClickListener) {
            super(itemView);

            roomProfile = (ImageView) itemView.findViewById(R.id.roomPicture);
            hotelName = (TextView) itemView.findViewById(R.id.hotelName);
            roomNumber = (TextView) itemView.findViewById(R.id.roomNumber);
            roomType = (TextView) itemView.findViewById(R.id.roomType);
            this.mRoomRecyclerClickListener = mRoomRecyclerClickListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mRoomRecyclerClickListener.onClick(itemView, getAdapterPosition());
        }
    }

    public interface RoomRecyclerClickListener{
        void onClick(View v, int position);
    }
}
