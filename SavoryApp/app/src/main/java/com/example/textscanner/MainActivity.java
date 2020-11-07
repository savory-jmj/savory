package com.example.textscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    EditText edit;
    private JsoupRun j;
    private GoogleSheetsService g;
    Uri CimageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void chooseFile(View view){
        CropImage.activity().start(MainActivity.this);
    }



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

    public void submit(View view) throws IOException, JSONException {
        List<String> linksList = new ArrayList<>();
        linksList = j.run(edit);
        System.out.println(linksList.get(0));
        getRecipes();
        Intent intent = new Intent(getBaseContext(), ResultActivity.class);
        intent.putExtra("Link", linksList.get(0));
        //Intent intent = new Intent(this,ResultMain.class);
        startActivity(intent);
    }

    public void getRecipes() throws IOException, JSONException {
        JSONObject json = g.readJsonFromUrl();
        JSONArray recipes = (JSONArray) json.get("entry");
        String title = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$title")).get("$t");
        String url = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$url")).get("$t");
        String summary = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$summary")).get("$t");
        String info = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$info")).get("$t");
        String ingredients = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$ingredients")).get("$t");
        String directions = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$directions")).get("$t");
        System.out.println(title);
        System.out.println(url);
        System.out.println(summary);
        System.out.println(info);
        System.out.println(ingredients);
        System.out.println(directions);
    }
}