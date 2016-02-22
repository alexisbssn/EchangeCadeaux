package com.alexis.colval.giftrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class GroupPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        Bundle b = getIntent().getExtras();
        String groupName = b.getString("GroupName");

        TextView title = (TextView)findViewById(R.id.textViewGroupTitle);
        title.setText(groupName);

        int ttd = 8;
        TextView tvttd = (TextView)findViewById(R.id.textViewTimeNextDraw);
        tvttd.setText(String.format(getString(R.string.group_page_time_until_next_draw), ttd));

        int drawInterval = 30;
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBarTimeNextDraw);
        progressBar.setMax(drawInterval);
        progressBar.setProgress(drawInterval - ttd);

        int price = 20;
        TextView giftPrice = (TextView)findViewById(R.id.textViewGiftPrice);
        giftPrice.setText(String.format(getString(R.string.group_page_gift_price), price));

        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBarUserRating);
        ratingBar.setStepSize(0.5f);
        ratingBar.setRating(3.5f);

        TextView description = (TextView)findViewById(R.id.textViewGroupDescription);
        description.setText(R.string.lorem_ipsum);

    }

    private boolean isClickedSeeParticipants = false;
    public void onClickSeeParticipants(View view) {

    }

    private boolean isClickedSeeComments = false;
    public void onClickSeeComments(View view) {

    }
}
