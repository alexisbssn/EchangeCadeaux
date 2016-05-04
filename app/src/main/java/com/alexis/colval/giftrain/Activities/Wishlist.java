package com.alexis.colval.giftrain.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alexis.colval.giftrain.DAL.Repository;
import com.alexis.colval.giftrain.Model.Group;
import com.alexis.colval.giftrain.Model.Wish;
import com.alexis.colval.giftrain.R;

public class Wishlist extends AppCompatActivity {

    int profileId;
    private static final int EDIT_WISH_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        profileId = getIntent().getExtras().getInt("profileId");
        populate();
    }

    public void onClickAddWish(View view) {
        Intent intent = new Intent(this, EditWish.class);
        Bundle extras = new Bundle();
        extras.putInt("ownerId", profileId);
        intent.putExtras(extras);
        startActivityForResult(intent, EDIT_WISH_CODE);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        populate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_WISH_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(getApplicationContext(), "Database updated", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void populate(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.wishlistLinearLayout);
        linearLayout.removeAllViews();
        for(final Wish w : Repository.getInstance().getWishesByProfileId(getApplicationContext(), profileId)){
            Button myButton = new Button(getApplicationContext());
            myButton.setText(w.getWishTitle());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            myButton.setLayoutParams(lp);
            linearLayout.addView(myButton);
            View.OnClickListener groupButtonListener = new View.OnClickListener(){
                public void onClick(View button){
                    Intent intent = new Intent(getApplicationContext(), EditWish.class);
                    Bundle b = new Bundle();
                    b.putInt("ownerId", profileId);
                    b.putParcelable("existingWish", w);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            };
            myButton.setOnClickListener(groupButtonListener);
        }
    }
}
