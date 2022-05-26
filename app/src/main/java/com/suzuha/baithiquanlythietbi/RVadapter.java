package com.suzuha.baithiquanlythietbi;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVadapter extends RecyclerView.Adapter<RVadapter.MyViewHolder> {
    ArrayList<item> mAL;
    Context context;
    DataBase dataBase;

    public RVadapter(Context ct, ArrayList<item> Arraylist) {
        context = ct;
        mAL = Arraylist;
    }

    @NonNull
    @Override
    public RVadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVadapter.MyViewHolder holder, int position) {
        item item = mAL.get(position);
        holder.Title.setText(item.title);
        holder.myImage.setImageURI(Uri.parse(item.url));
        holder.deviceSwitch.setChecked(false);
        holder.deviceSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (holder.deviceSwitch.isChecked()) {
                holder.Status.setText("Bật");
                holder.Status.setTextColor(Color.parseColor("#16C809"));
            } else {
                holder.Status.setText("Tắt");
                holder.Status.setTextColor(Color.parseColor("#FF0000"));
            }
        });

        holder.mainLayout.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.mainLayout.getContext());
            builder
                    .setTitle("Lưu ý")
                    .setMessage("Xóa thiết bị này?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                        dataBase.deleteDevice(mAL.get(position));
                        mAL.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("Không", null)
                    .show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mAL.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Status;
        ImageView myImage;
        Switch deviceSwitch;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.tv_myText1);
            Status = itemView.findViewById(R.id.tv_Status);
            myImage = itemView.findViewById(R.id.myImageView);
            deviceSwitch = itemView.findViewById(R.id.deviceSwitch);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
