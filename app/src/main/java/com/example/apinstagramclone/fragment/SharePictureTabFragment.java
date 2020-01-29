package com.example.apinstagramclone.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.apinstagramclone.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTabFragment extends Fragment implements View.OnClickListener {

    private ImageView img_ShareImage;
    private EditText et_Imagetext;
    private Button btn_ShareImage;
    private final int STORAGE_REQUEST_CODE = 1000;
    private final int MEDIA_RESULT_CODE = 2000;
    private Bitmap recivedImageBitmap;

    public SharePictureTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
        img_ShareImage = view.findViewById(R.id.img_ShareImage);
        et_Imagetext = view.findViewById(R.id.et_Imagetext);
        btn_ShareImage = view.findViewById(R.id.btnShareImage);

        btn_ShareImage.setOnClickListener(this);

        img_ShareImage.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_ShareImage:
                if (Build.VERSION.SDK_INT >= 23 &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
                } else {
                    getChosenImage();
                }
                break;
            case R.id.btnShareImage:
                if (recivedImageBitmap == null || et_Imagetext.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "You must select an image.\n OR\n You must specify description", Toast.LENGTH_SHORT).show();
                    return;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                recivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] bytes =  byteArrayOutputStream.toByteArray();
                ParseFile parseFile = new ParseFile("pic.png",bytes);
                ParseObject parseObject = new ParseObject("Photo");
                parseObject.put("picture",parseFile);
                parseObject.put("image_desc",et_Imagetext.getText().toString());
                parseObject.put("username",ParseUser.getCurrentUser().getUsername());
                final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Loading");
                mProgressDialog.show();
                mProgressDialog.setCancelable(false);
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        mProgressDialog.dismiss();
                        if (e!= null){
                            Toast.makeText(getContext(), "Unknown error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getContext(), "Done!!!", Toast.LENGTH_SHORT).show();

                    }
                });
                break;
        }

    }

    private void getChosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, MEDIA_RESULT_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MEDIA_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                recivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                img_ShareImage.setImageBitmap(recivedImageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE && (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            getChosenImage();
        }
    }
}
