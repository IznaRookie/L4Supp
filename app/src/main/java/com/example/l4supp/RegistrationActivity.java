package com.example.l4supp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.l4supp.database.AppDatabase;
import com.example.l4supp.database.RoomWrapper;
import com.example.l4supp.database.SellerEntity;
import com.example.l4supp.database.UserEntity;

public class RegistrationActivity extends AppCompatActivity {

    private AppDatabase database;
    private EditText etLogin, etPassword, etPasswordConfirm;
    private Button btnEnter;
    private CheckBox cbSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        database = RoomWrapper.getDatabase(this);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        btnEnter = findViewById(R.id.btnEnter);
        cbSeller = findViewById(R.id.cbSeller);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = etLogin.getText().toString();
                if (login.isEmpty()) {
                    etLogin.requestFocus();
                    etLogin.setError(getString(R.string.empty_field_error));
                    return;
                }
                String password = etPassword.getText().toString();
                if (password.isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError(getString(R.string.empty_field_error));
                    return;
                }
                // todo добавить проверку на пустоту полей и совпадение паролей
                UserEntity userEntity = new UserEntity(login, password, cbSeller.isChecked(), true);
                int insertUser = (int) database.userDao().insertWithReturn(userEntity);
                if (userEntity.isSeller()) {
                    SellerEntity sellerEntity = new SellerEntity(insertUser, "","");
                    database.sellerDao().insert(sellerEntity);
                    startActivity(new Intent(RegistrationActivity.this, SellerMainActivity.class));
                } else {
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                }
                finish();
            }
        });
    }
}
