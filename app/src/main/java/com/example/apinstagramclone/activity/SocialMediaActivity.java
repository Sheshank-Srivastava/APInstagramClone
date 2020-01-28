package com.example.apinstagramclone.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.apinstagramclone.R;
import com.example.apinstagramclone.adapter.TabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class SocialMediaActivity extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tablayout;
    TabAdapter tabAdapter;
    private Bitmap recivedImageBitmap;

    private static final int STORAGE_REQUEST_CODE = 3000;
    private static final int MEDIA_REQUEST_CODE = 3001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        setTitle("Social Media App!!!");

        viewPager = findViewById(R.id.viewPager);
        tablayout = findViewById(R.id.myTablayout);
        tabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tabAdapter);
        tablayout.setupWithViewPager(viewPager, false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_postImage:
                if (android.os.Build.VERSION.SDK_INT >= 23 &&
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
                } else {

                    captureImage();
                }
                return true;
            case R.id.menu_logout:
                ParseUser.getCurrentUser().logOut();
                startActivity(new Intent(SocialMediaActivity.this, LoginActivity.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==STORAGE_REQUEST_CODE &&(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED))
            captureImage();
    }

    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, MEDIA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MEDIA_REQUEST_CODE && resultCode == RESULT_OK && data !=null){
            try {
               Uri capturedImage = data.getData();
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                       capturedImage);
                ByteArrayOutputStream byteArrayOutputStream =  new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();

                ParseFile parseFile = new ParseFile("img.png",bytes);
                ParseObject parseObject = new ParseObject("Photo");
                parseObject.put("picture",parseFile);
                parseObject.put("username",ParseUser.getCurrentUser().getUsername());
                final ProgressDialog mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Loading");
                mProgressDialog.show();
                mProgressDialog.setCancelable(false);
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        mProgressDialog.dismiss();
                        if (e!= null){
                            Toast.makeText(SocialMediaActivity.this, "Unknown error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(SocialMediaActivity.this, "Done!!!", Toast.LENGTH_SHORT).show();

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
