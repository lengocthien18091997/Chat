package com.example.chat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonRegis;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();
        buttonRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisActivity.class);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editTextUsername.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                String err = validate(username, password);
                if (err.equals("ok")) {
                    login();
                } else {
                    Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.urlGetCommon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String passwordRespon[] = Common.jsonToArr(response);
                        if (passwordRespon.length == 0 || !passwordRespon[0].equals(password)) {
                            Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("query","SELECT password FROM `account` WHERE username = " + "'"+ username + "'");
                params.put("output","password");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private String validate(String userName, String password) {
        String err = "ok";
        if (userName.equals("") || password.equals("")) {
            err = "Hãy nhập đầy đủ thông tin";
        }
        return err;
    }

    private void mapping() {
        editTextUsername = (EditText) findViewById(R.id.etUsername);
        editTextPassword = (EditText) findViewById(R.id.etPassword);
        buttonLogin = (Button) findViewById(R.id.bLogin);
        buttonRegis = (Button) findViewById(R.id.bRegis);

    }
}