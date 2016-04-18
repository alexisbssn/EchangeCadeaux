package com.alexis.colval.giftrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexis.colval.giftrain.DAL.ParticipantHelper;
import com.alexis.colval.giftrain.DAL.Repository;
import com.alexis.colval.giftrain.Model.Comment;
import com.alexis.colval.giftrain.Model.Group;
import com.alexis.colval.giftrain.Model.Profile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupPage extends AppCompatActivity {

    Group group;
    int profileId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        group = getIntent().getParcelableExtra("existingGroup");
        profileId = getIntent().getExtras().getInt("profileId");

        TextView title = (TextView)findViewById(R.id.textViewGroupTitle);
        title.setText(group.getGroupName());

        TextView tvttd = (TextView)findViewById(R.id.textViewTimeNextDraw);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(group.getNextDraw());
        tvttd.setText(String.format(getString(R.string.group_page_time_until_next_draw), date));

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBarTimeNextDraw);
        progressBar.setMax(group.getDrawInterval());
        progressBar.setProgress(group.getDrawInterval() - group.getNextDraw().compareTo(new Date()));

        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBarUserRating);
        ratingBar.setStepSize(0.5f);
        ratingBar.setRating((float)group.getRating());

        TextView description = (TextView)findViewById(R.id.textViewGroupDescription);
        description.setText(group.getGroupDescription());

    }

    private boolean isClickedSeeParticipants = false;
    private boolean isClickedSeeComments = false;

    public void onClickSeeParticipants(View view) {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layoutMultiDisplayGroup);
        linearLayout.removeAllViews();
        if(isClickedSeeParticipants) {
            isClickedSeeParticipants = false;
            isClickedSeeComments = false;
        }else{
            isClickedSeeParticipants = true;
            android.content.Context appContext = getApplicationContext();
            for(Profile p : Repository.getInstance().getParticipantsByGroupId(appContext, group.getId())) {
                TextView tv = new TextView(appContext);
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                tv.setText(p.getFirstName() + " " + p.getLastName());
                linearLayout.addView(tv);
            }
        }
        //Toast.makeText(getApplicationContext(), "There are no participants yet", Toast.LENGTH_LONG).show();
    }

    public void onClickSeeComments(View view) {
        /*LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layoutMultiDisplayGroup);
        linearLayout.removeAllViews();
        if(isClickedSeeComments) {
            isClickedSeeComments = false;
            isClickedSeeParticipants = false;
        }else{
            isClickedSeeComments = true;
            android.content.Context appContext = getApplicationContext();
            for(Comment c : group.getComments()) {
                TextView tv = new TextView(appContext);
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                tv.setText(c.getContent());
                linearLayout.addView(tv);
            }
        }*/
        Toast.makeText(getApplicationContext(), "There are no comments yet", Toast.LENGTH_LONG).show();
    }

    public void onClickParticipate(View view) {
        Repository.getInstance().addParticipant(getApplicationContext(), profileId, group.getId());
        Toast.makeText(getApplicationContext(), "You now participate in this group", Toast.LENGTH_LONG).show();
    }
}
