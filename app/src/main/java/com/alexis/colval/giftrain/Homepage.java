package com.alexis.colval.giftrain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void OnClickLogin(View v){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void onClickProfile(View v){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void onClickWishlist(View v){
        Intent intent = new Intent(this, Wishlist.class);
        startActivity(intent);
    }

    public void onClickGroupSearch(View v){
        Intent intent = new Intent(this, GroupSearch.class);
        startActivity(intent);
    }
}
