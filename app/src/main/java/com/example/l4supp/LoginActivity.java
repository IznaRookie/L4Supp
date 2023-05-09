package com.example.l4supp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.l4supp.database.AppDatabase;
import com.example.l4supp.database.RoomWrapper;
import com.example.l4supp.database.UserEntity;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnEnter;
    private TextView tvRegistration;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = RoomWrapper.getDatabase(this);

        UserEntity userEntity = database.userDao().getCurrentUser();
        if (userEntity != null) {
            if (userEntity.isSeller()) {
                startActivity(new Intent(LoginActivity.this, SellerMainActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            finish();
        }

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnEnter = findViewById(R.id.btnEnter);
        tvRegistration = findViewById(R.id.tvRegistration);

        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

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

                UserEntity userEntity = database.userDao().getByCredential(login, password);
                if (userEntity != null) {
                    userEntity.setLoggedIn(true);
                    database.userDao().update(userEntity);
                    if (userEntity.isSeller()) {
                        startActivity(new Intent(LoginActivity.this, SellerMainActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Такого пользователя нет", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
