package com.alexis.colval.giftrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.jar.Attributes;

public class Profile extends AppCompatActivity {

    //temporary no-db variables
    String firstName="Alexis",lastName="Buisson",email="alexis.buisson@hotmail.com",region="Montérégie";
    Date birthDate = new Date(1996 + 1900, 3, 27);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView title = (TextView)findViewById(R.id.ProfileTitle);
        title.setText(firstName + "'s Profile");
        populate(firstName,lastName,email,region,birthDate.toString());
    }
    private void populate(String fN, String lN, String email, String region, String bd){
        TextView tv = (TextView)findViewById(R.id.firstName);
        tv.setText(fN);
        tv = (TextView)findViewById(R.id.lastName);
        tv.setText(lN);
        tv = (TextView)findViewById(R.id.email);
        tv.setText(email);
        tv = (TextView)findViewById(R.id.region);
        tv.setText(region);
        tv = (TextView)findViewById(R.id.birthDate);
        tv.setText(bd.toString());
    }
}
