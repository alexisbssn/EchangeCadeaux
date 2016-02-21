package com.alexis.colval.giftrain;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickRegister(View v){
        if(findViewById(R.id.registerLayout).getVisibility() == View.INVISIBLE){
            showRegisterFields();
        }else{
            register();
        }
    }

    public void showRegisterFields(){
        View registerButton = findViewById(R.id.buttonRegister);

        registerButton.setTranslationX(registerButton.getWidth() / 2);
        RelativeLayout.LayoutParams buttonParams = (RelativeLayout.LayoutParams)registerButton.getLayoutParams();
        buttonParams.removeRule(RelativeLayout.BELOW);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        registerButton.setLayoutParams(buttonParams);

        View cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setTranslationX(-(cancelButton.getWidth() / 2));

        findViewById(R.id.registerLayout).setVisibility(View.VISIBLE);
    }

    public void register(){
        String email, pwd, fName, lName;
        Date bDate;

        email = ((EditText)findViewById(R.id.username)).getText().toString();
        pwd = ((EditText)findViewById(R.id.password)).getText().toString();
        fName = ((EditText)findViewById(R.id.firstName)).getText().toString();
        lName = ((EditText)findViewById(R.id.lastName)).getText().toString();

        if(email.isEmpty() || pwd.isEmpty() || fName.isEmpty() || lName.isEmpty()){
            addError();
            //return;
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT);
        toast.show();
        login();
    }

    private void addError(){
        //mark red where the error is located
        //use @string/register_error
    }

    public void onClickCancelRegister(View v){
        //reset what showRegisterFields() has done
        View registerButton = findViewById(R.id.buttonRegister);
        /*int buttonHeight = registerButton.getHeight();
        int bottom = findViewById(R.id.password).getBottom();
        registerButton.setTop(bottom);
        registerButton.setBottom(bottom + buttonHeight);*/
        registerButton.setTranslationX(0);
        RelativeLayout.LayoutParams buttonParams = (RelativeLayout.LayoutParams)registerButton.getLayoutParams();
        buttonParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonParams.addRule(RelativeLayout.BELOW, R.id.password);
        registerButton.setLayoutParams(buttonParams);

        findViewById(R.id.registerLayout).setVisibility(View.INVISIBLE);
    }
    public void onClickLogin(View v){
        login();
    }
    public void onClickGuest(View v){
        login();
    }

    private void login(){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
}
