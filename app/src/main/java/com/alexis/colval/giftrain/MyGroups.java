package com.alexis.colval.giftrain;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alexis.colval.giftrain.DAL.Repository;
import com.alexis.colval.giftrain.Model.Group;

public class MyGroups extends AppCompatActivity {

    int profileId;
    private static final int editGroupCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);
        profileId = getIntent().getExtras().getInt("profileId");
        populate();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        populate();
    }

    public void onClickAddGroup(View view) {
        Intent intent = new Intent(this, EditGroup.class);
        Bundle extras = new Bundle();
        extras.putInt("creatorId", profileId);
        intent.putExtras(extras);
        startActivityForResult(intent, editGroupCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == editGroupCode) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(getApplicationContext(), "Database updated", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void populate(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.myGroupsLinearLayout);
        linearLayout.removeAllViews();
        for(final Group g : Repository.getInstance().getGroupsByProfileId(getApplicationContext(), profileId)){
            Button myButton = new Button(getApplicationContext());
            myButton.setText(g.getGroupName());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            myButton.setLayoutParams(lp);
            linearLayout.addView(myButton);
            View.OnClickListener groupButtonListener = new View.OnClickListener(){
                public void onClick(View button){
                    Intent intent = new Intent(getApplicationContext(), EditGroup.class);
                    Bundle b = new Bundle();
                    b.putInt("creatorId", profileId);
                    b.putParcelable("existingGroup", g);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            };
            myButton.setOnClickListener(groupButtonListener);
        }
    }

}
