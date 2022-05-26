package com.suzuha.baithiquanlythietbi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends AppCompatActivity {
    ImageView mainImageView;
    EditText editTitle, editBrand, editYear, editDetail;
    Button OK, Back;

    String data1, data2, data3, data4;
    int myImage;
    String linkAnh;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //ImageView
        mainImageView = findViewById(R.id.imageV);

        //EditText
        editTitle = findViewById(R.id.etTitle);
        editBrand = findViewById(R.id.etBrand);
        editYear = findViewById(R.id.etYear);
        editDetail = findViewById(R.id.etDetail);

        //Button
        OK = findViewById(R.id.btOK);
        Back = findViewById(R.id.btBACK);


//        tv_imgIndex.setText("" + 1 + "/" + images.length);
//        mainImageView.setImageResource(images[0]);
//        imgIndex.setMax(images.length - 1);

//        imgIndex.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                int position = i + 1;
//                tv_imgIndex.setText("" + position + "/" + images.length);
//                mainImageView.setImageResource(images[i]);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        OK.setOnClickListener(view -> {
            if (!editTitle.getHint().toString().isEmpty()
                    && !editBrand.getHint().toString().isEmpty()
                    && !editYear.getHint().toString().isEmpty()
                    && !editDetail.getHint().toString().isEmpty()) {
                if (editTitle.getText().toString().isEmpty()
                        && !editTitle.getHint().toString().isEmpty()) {
                    getIntent().putExtra("setTitle", editTitle.getHint().toString());
                } else getIntent().putExtra("setTitle", editTitle.getText().toString());

                if (editBrand.getText().toString().isEmpty()
                        && !editBrand.getHint().toString().isEmpty()) {
                    getIntent().putExtra("setBrand", editBrand.getHint().toString());
                } else getIntent().putExtra("setBrand", editBrand.getText().toString());

                if (editYear.getText().toString().isEmpty()
                        && !editYear.getHint().toString().isEmpty()) {
                    getIntent().putExtra("setYear", editYear.getHint().toString());
                } else getIntent().putExtra("setYear", editYear.getText().toString());

                if (editDetail.getText().toString().isEmpty()
                        && !editDetail.getHint().toString().isEmpty()) {
                    getIntent().putExtra("setDetail", editDetail.getHint().toString());
                } else getIntent().putExtra("setDetail", editDetail.getText().toString());

                getIntent().putExtra("setImage", linkAnh);
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });

        Back.setOnClickListener(view -> {
            finish();
        });
        getData();

        ((FloatingActionButton) findViewById(R.id.ChooseImg)).setOnClickListener(view -> {
            ImagePicker.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
    }

    private void getData() {
        if (getIntent().hasExtra("image")
                && getIntent().hasExtra("title")
                && getIntent().hasExtra("brand")
                && getIntent().hasExtra("year")
                && getIntent().hasExtra("detail")
                && getIntent().hasExtra("index")) {
            int dummy = getIntent().getIntExtra("index", 0) + 1;
            setTitle("Editing item No." + dummy);

            mainImageView.setImageURI(Uri.parse(getIntent().getStringExtra("url")));

            data1 = getIntent().getStringExtra("title");
            data2 = getIntent().getStringExtra("brand");
            data3 = getIntent().getStringExtra("year");
            data4 = getIntent().getStringExtra("detail");

            setData();
            getIntent().putExtra("indexEdit", getIntent().getIntExtra("index", 0));
        } else {
            setTitle("Adding");
//            mainImageView.setImageResource(images[0]);
        }
    }

    private void setData() {
        editTitle.setHint(data1);
        editBrand.setHint(data2);
        editYear.setHint(data3);
        editDetail.setHint(data4);
        mainImageView.setImageResource(myImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Uri uri = data.getData();
        linkAnh = uri.toString();
        mainImageView.setImageURI(uri);
    }
}