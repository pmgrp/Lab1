package com.sorbellini.s214631.lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class showProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //if there is data saved populate fields
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String saved_name = preferences.getString("name", null);
        String saved_email = preferences.getString("email", null);
        String saved_bio = preferences.getString("bio", null);
        String saved_imgPath = preferences.getString("imgPath", null);
        if (saved_imgPath != null){
            ImageView imageView = (ImageView) findViewById(R.id.ivImage);
            Bitmap imageBitmap = imagePicker.loadImageFromStorage(saved_imgPath);
            if(imageView!=null)
                imageView.setImageBitmap(imageBitmap);
        }
        TextView textView;
        textView = (TextView) findViewById(R.id.name);
        if(textView!=null)
            textView.setText(saved_name);
        textView = (TextView) findViewById(R.id.email);
        if(textView!=null)
            textView.setText(saved_email);
        textView = (TextView) findViewById(R.id.bio);
        if(textView!=null)
            textView.setText(saved_bio);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, editProfile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
