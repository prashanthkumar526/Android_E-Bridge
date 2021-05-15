package com.tanmaya.yaayde;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentLogin extends AppCompatActivity implements View.OnClickListener{
    EditText suserEdit,spasswordEdit;
    Button sloginButton,sregisterButton;

    ProgressDialog progressDialog;

    String userString,passString,resuserString,respassString;
    JSONParser parser=new JSONParser();
    JSONArray response;
    public static String student_loginurl="http://192.168.0.103:8082/ebridge/studentlogin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        suserEdit=findViewById(R.id.susername);
        spasswordEdit=findViewById(R.id.spassword);

        sloginButton=findViewById(R.id.login);
        sregisterButton=findViewById(R.id.register);
        sloginButton.setOnClickListener(this);
        sregisterButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.login) {
            userString = suserEdit.getText().toString();
            passString = spasswordEdit.getText().toString();

            ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (userString.length() > 0 && passString.length() > 0) {
                    new Login().execute();
                } else {
                    Toast.makeText(StudentLogin.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(StudentLogin.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class Login extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(StudentLogin.this);
            progressDialog.setMessage("Please wait....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                List<NameValuePair> args=new ArrayList<>();
                args.add(new BasicNameValuePair("username",userString));

                JSONObject object=parser.makeHttpRequest(student_loginurl,"POST",args);

                response=object.getJSONArray("Login");
                for (int i=0;i<response.length();i++)
                {
                    JSONObject c=response.getJSONObject(i);

                    resuserString=c.getString("username");
                    respassString=c.getString("password");

                }

            }catch (Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (userString.equals(resuserString)&&passString.equals(respassString))
            {
                Intent in=new Intent(StudentLogin.this,StudentHomeActivity.class);
                startActivity(in);
            }
            else
            {
                Toast.makeText(StudentLogin.this,"Invalid username or password!",Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();

        }
    }
}