package com.sorbellini.s214631.lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class editProfile extends AppCompatActivity {

    private static final int PICK_IMAGE_ID = 234;
    private String imagePath=null;
    private Uri tempImageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //if there is any data saved fill fields
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String saved_name = preferences.getString("name", null);
        String saved_email = preferences.getString("email", null);
        String saved_bio = preferences.getString("bio", null);
        imagePath = preferences.getString("imgPath", null);
        ImageView imageView = (ImageView) findViewById(R.id.ivImage);
        Bitmap bitmap = imagePicker.loadImageFromStorage(imagePath);
        imageView.setImageBitmap(bitmap);
        //if an image has been shot but not saved get it
        if(savedInstanceState != null){
            tempImageUri = savedInstanceState.getParcelable("TempUri");
        }
        if(tempImageUri != null){
            bitmap = imagePicker.getImageResized(this, tempImageUri);
            if(imageView!=null)
                imageView.setImageBitmap(bitmap);
        }
        EditText editText;
        editText = (EditText) findViewById(R.id.edit_name);
        if(editText!=null)
            editText.setText(saved_name);
        editText = (EditText) findViewById(R.id.edit_email);
        if(editText!=null)
            editText.setText(saved_email);
        editText = (EditText) findViewById(R.id.edit_bio);
        if(editText!=null)
            editText.setText(saved_bio);
    }

    //called when send button is pressed
    public void saveData(View view) {
        //save data to shared preferences
        Intent intent = new Intent(this, showProfile.class);
        EditText editText = (EditText) findViewById(R.id.edit_name);
        String name = editText.getText().toString();
        editText = (EditText) findViewById(R.id.edit_email);
        String email = editText.getText().toString();
        editText = (EditText) findViewById(R.id.edit_bio);
        String bio = editText.getText().toString();
        ImageView imageView = (ImageView) findViewById(R.id.ivImage);
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        imagePath = imagePicker.saveToInternalStorage(bitmap,this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("imgPath",imagePath);
        editor.putString("name", name);
        editor.putString("email",email);
        editor.putString("bio", bio);
        editor.commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /*called when Select Photo button is pressed
     *shows a dialog box with choices
     */
    public void selectPhoto(View view){
        Intent chooseImageIntent = imagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = (ImageView) findViewById(R.id.ivImage);
        switch(requestCode) {
            case PICK_IMAGE_ID:
                tempImageUri = imagePicker.getUriFromResult(this, resultCode, data);
                if(tempImageUri != null) {
                    Bitmap image = imagePicker.getImageResized(this, tempImageUri);
                    imageView.setImageBitmap(image);
                }
                else{
                    imageView.setImageResource(R.drawable.ic_empty_photo);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle onSave){
        super.onSaveInstanceState(onSave);
        onSave.putParcelable("TempUri", tempImageUri);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
