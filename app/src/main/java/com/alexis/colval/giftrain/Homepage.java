package com.alexis.colval.giftrain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void OnClickLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void onClickProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void onClickWishlist(){
        Intent intent = new Intent(this, Wishlist.class);
        startActivity(intent);
    }

    public void onClickGroupSearch(){
        Intent intent = new Intent(this, GroupSearch.class);
        startActivity(intent);
    }
}
