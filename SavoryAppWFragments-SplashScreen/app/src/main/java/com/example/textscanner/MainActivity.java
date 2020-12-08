package com.example.textscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;

import com.example.textscanner.ModifiedwebScraper.src.GoogleSheetsService;
import com.theartofdev.edmodo.cropper.CropImage;

import com.example.textscanner.ModifiedwebScraper.src.JsoupRun;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    EditText edit;
    CardView card;
    CardView card1;

    private JsoupRun j;
    private GoogleSheetsService g;
    Uri CimageUri;

    
    //OnCreate checks the phone for permission to access the Camera and reveals UI assets.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_main);
        //find imageview
        imageView = findViewById(R.id.imageId);
        //find textview
        edit = findViewById(R.id.editTextId);

        button = findViewById(R.id.button);
        //check app level permission is granted for Camera
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //grant the permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);

        }
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        j = new JsoupRun();
        g = new GoogleSheetsService();
    }

    
    //Allows users to select where to pick reciept image from
    public void chooseFile(View view){
        card = findViewById(R.id.cardLayer);
        card1 = findViewById(R.id.cardHolder);

        CropImage.activity().start(MainActivity.this);
        card.setVisibility(View.GONE);
         card1.setVisibility(View.VISIBLE);

    }


 //OnActivity Result() opens crop activity before using the firebase ML kit to convert the image to a bitmap and extract text.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                CimageUri = result.getUri();


                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), CimageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                imageView.setImageBitmap(bitmap);

                FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);

                FirebaseVision firebaseVision = FirebaseVision.getInstance();

                FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();

                final Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);

                //extracted text is sent to an edittext object
                task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        final String s = firebaseVisionText.getText();
                        edit.setText(s);
                    }
                });

                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    //sends content of edittext to ListActivity
    public void submit(View view) throws IOException, JSONException {
        Intent intent = new Intent(getBaseContext(), ListActivity.class);
        intent.putExtra("Link",edit.getText().toString());
        startActivity(intent);
    }


}
