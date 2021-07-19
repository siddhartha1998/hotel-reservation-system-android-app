package com.hotel.HamroKhaltiHotel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.RoomPicture;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RoomReservationRecyclerViewAdapter extends RecyclerView.Adapter<RoomReservationRecyclerViewAdapter.RoomReservationViewHolder> {

    private Context context;
    private ArrayList<RoomPicture> roomPictureArrayList;


    public RoomReservationRecyclerViewAdapter(Context context, ArrayList<RoomPicture> roomPictureArrayList) {
        this.context = context;
        this.roomPictureArrayList = roomPictureArrayList;
    }

    @NonNull
    @Override
    public RoomReservationRecyclerViewAdapter.RoomReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_room_picture, parent, false);
        RoomReservationRecyclerViewAdapter.RoomReservationViewHolder roomReservationViewHolder = new RoomReservationRecyclerViewAdapter.RoomReservationViewHolder(view);
        return roomReservationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomReservationRecyclerViewAdapter.RoomReservationViewHolder holder, int position) {

        //holder.textFileName.setText(roomPictureArrayList.get(position).getFileName());
        //Log.d(TAG, "onBindViewHolder: FileName "+roomPictureArrayList.get(position).getFileName());
        if(roomPictureArrayList.get(position).getFileName()!=null) {
            Log.d(TAG, "onBindViewHolder: FileName "+roomPictureArrayList.get(position).getFileName());
            String url = "http://192.168.137.57:8080/api/downloadPicture/downloadRoomFile/"+roomPictureArrayList.get(position).getFileName();

            Picasso.get().load(url).into(holder.roomImage);
        }else{
            Picasso.get().load(R.drawable.logo).into(holder.roomImage);
        }
    }

    @Override
    public int getItemCount() {
        return roomPictureArrayList.size();
    }

    public class RoomReservationViewHolder extends RecyclerView.ViewHolder {
        private ImageView roomImage;
        private TextView textFileName;
        public RoomReservationViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.roomPicture);
            textFileName = itemView.findViewById(R.id.textFileName);
        }
    }
}
