package com.suzuha.baithiquanlythietbi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<item> mAL;
    RecyclerView RV;
    RVadapter RVadapter;
    ActivityResultLauncher<Intent> SecondActivityResultLauncher;
    DataBase dataBase;
    ItemClickListener itemClickListener;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RV = findViewById(R.id.RV);

//        RV.setLayoutManager(new LinearLayoutManager(this));
        openSecondActivityForResult();
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);

        ((FloatingActionButton) findViewById(R.id.floatingBtAdd)).setOnClickListener(view -> {
            intent.removeExtra("index");
            SecondActivityResultLauncher.launch(intent);
        });

        itemClickListener = (position, value) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setMessage("");
            alert.setPositiveButton("Edit", (dialogInterface, i) -> {
                item A = mAL.get(position);
                intent.putExtra("index", position);
                intent.putExtra("url", A.url);
                intent.putExtra("title", A.title);
                intent.putExtra("brand", A.Brand);
                intent.putExtra("year", A.Year);
                intent.putExtra("detail", A.Detail);
                SecondActivityResultLauncher.launch(intent);
            });
            alert.setNegativeButton("Delete", (dialogInterface, i) -> {
                dataBase.deleteDevice(mAL.get(position));
                mAL.remove(position);
                RVadapter.notifyDataSetChanged();
            });
        };
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mAL = new ArrayList<item>();
        dataBase = new DataBase(this);
        mAL = dataBase.readDanhSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RV.setLayoutManager(linearLayoutManager);
        RVadapter = new RVadapter(getApplicationContext(),mAL, itemClickListener);
        RV.setAdapter(RVadapter);
        RVadapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

    }

    public void openSecondActivityForResult() {
        SecondActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        if (data.hasExtra("indexEdit")) {
                            item item = new item();
                            int position = data.getIntExtra("position", 0);
                            item.title = data.getStringExtra("setTitle");
                            item.Brand = data.getStringExtra("setBrand");
                            item.Year = data.getStringExtra("setYear");
                            item.Detail = data.getStringExtra("setDetail");
                            item.url = data.getStringExtra("setImage");
                            dataBase.updateDevice(mAL.get(position), item);
                        } else {
                            item item = new item();
                            item.url = data.getStringExtra("setImage");
                            item.title = data.getStringExtra("setTitle");
                            item.Brand = data.getStringExtra("setBrand");
                            item.Year = data.getStringExtra("setYear");
                            item.Detail = data.getStringExtra("setDetail");
                            dataBase.addDevice(item);
                        }
                    }
                });
    }
}