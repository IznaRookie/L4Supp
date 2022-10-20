package com.example.l4supp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.l4supp.database.AppDatabase;
import com.example.l4supp.database.RoomWrapper;
import com.example.l4supp.database.SellerEntity;
import com.example.l4supp.database.UserEntity;

import java.io.File;
import java.io.FileOutputStream;

public class SellerEditorFragment extends Fragment {

    private static final String ID_ARG = "ID_ARG";
    private EditText etName, etDescription;
    private Button btnSave, btnExit;
    private AppDatabase database;
    Button button;
    ImageView imageView;

    public SellerEditorFragment() {
        super(R.layout.fragment_seller_editor);
    }

    /*public static SellerEditorFragment newInstance(int id) {
        SellerEditorFragment fragment = new SellerEditorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID_ARG, id);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = RoomWrapper.getDatabase(requireActivity());

        etName = view.findViewById(R.id.etName);
        etDescription = view.findViewById(R.id.etDescription);
        btnSave = view.findViewById(R.id.btnSave);
        button = view.findViewById(R.id.button);
        imageView = view.findViewById(R.id.imageView2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable=imageView.getDrawable();
                Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();

                try {
                    File file = new File(getContext().getExternalCacheDir(),
                            File.separator + "theodore.jpg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                            BuildConfig.APPLICATION_ID +".provider", file);

                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/jpg");

                    startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEntity userEntity = database.userDao().getCurrentUser();
                SellerEntity sellerEntity = new SellerEntity(
                        userEntity.getId(),
                        etName.getText().toString(),
                        etDescription.getText().toString()
                );
                SellerEntity existingSeller = database.sellerDao().getByUserId(userEntity.getId());
                if (existingSeller != null) {
                    sellerEntity.setId(existingSeller.getId());
                    database.sellerDao().update(sellerEntity);
                } else {
                    database.sellerDao().insert(sellerEntity);
                }
                requireActivity().finish();
            }
        });

        UserEntity userEntity = database.userDao().getCurrentUser();

        SellerEntity existingSeller = database.sellerDao().getByUserId(userEntity.getId());
        if (existingSeller != null) {
            etName.setText(existingSeller.getName());
            etDescription.setText(existingSeller.getProduct());
        }
    }
}