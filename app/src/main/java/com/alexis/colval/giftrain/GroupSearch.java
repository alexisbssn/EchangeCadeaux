package com.alexis.colval.giftrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.content.Intent;
public class GroupSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_search);
    }

    public void onClickSearch(View view) {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.searchResults);
        for(int i=0;i<25;i++){
            Button myButton = new Button(getApplicationContext());
            myButton.setText("Exchange group #" + i);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            myButton.setLayoutParams(lp);
            linearLayout.addView(myButton);
            View.OnClickListener groupButtonListener = new View.OnClickListener(){
                public void onClick(View button){
                    Intent intent = new Intent(getApplicationContext(), GroupPage.class);
                    Bundle b = new Bundle();
                    b.putString("GroupName",((Button)button).getText().toString());
                    intent.putExtras(b);
                    startActivity(intent);
                }
            };
            myButton.setOnClickListener(groupButtonListener);
        }
    }
}
