package com.suzuha.baithiquanlythietbi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAL = new ArrayList<>();
        RV = findViewById(R.id.RV);


        RVadapter = new RVadapter(this, mAL);
        RV.setAdapter(RVadapter);
        RV.setLayoutManager(new LinearLayoutManager(this));
        openSecondActivityForResult();
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);

        ((FloatingActionButton) findViewById(R.id.floatingBtAdd)).setOnClickListener(view -> {
            intent.removeExtra("index");
            SecondActivityResultLauncher.launch(intent);
        });

        RV.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, RV,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        item A = mAL.get(position);
                        intent.putExtra("index", position);
                        intent.putExtra("url", A.url);
                        intent.putExtra("title", A.title);
                        intent.putExtra("brand", A.Brand);
                        intent.putExtra("year", A.Year);
                        intent.putExtra("detail", A.Detail);
                        SecondActivityResultLauncher.launch(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }));

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