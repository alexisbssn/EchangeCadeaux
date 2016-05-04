package com.alexis.colval.giftrain.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alexis.colval.giftrain.DAL.Repository;
import com.alexis.colval.giftrain.Model.Wish;
import com.alexis.colval.giftrain.R;

public class EditWish extends Activity {

    EditText wishTitle, wishTheme, wishPrice;
    int ownerId;
    Wish wish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wish);

        wishTitle = (EditText) findViewById(R.id.wishTitle);
        wishTheme = (EditText) findViewById(R.id.wishTheme);
        wishPrice = (EditText) findViewById(R.id.wishPrice);

        //fill if not a new wish
        wish = getIntent().getParcelableExtra("existingWish");
        ownerId = getIntent().getExtras().getInt("ownerId", -1);
        if(wish != null){
            wishTitle.setText(wish.getWishTitle());
            wishTheme.setText(wish.getWishTheme());
            wishPrice.setText(String.valueOf(wish.getWishPrice()));
            if(ownerId == -1){
                ownerId = wish.getOwnerId();
            }
        }
    }

    public void onClickCancelEditWish(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClickDoneEditWish(View view) {
        String title = wishTitle.getText().toString();
        String theme = wishTheme.getText().toString();
        String priceString = wishPrice.getText().toString();
        if(title == null || theme == null || priceString == null){
            Toast.makeText(getApplicationContext(), R.string.edit_wish_error, Toast.LENGTH_LONG).show();
            return;
        }
        int price = Integer.parseInt(priceString);
        if(wish == null){
            Repository.getInstance().addWish(getBaseContext(), title, theme, price, ownerId);
        }else{
            Repository.getInstance().updateWish(getBaseContext(), wish.getId(), title, theme, price, ownerId);
        }
        setResult(RESULT_OK);
        finish();
    }
}
