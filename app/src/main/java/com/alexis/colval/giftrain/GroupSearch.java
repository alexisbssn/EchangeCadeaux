package com.alexis.colval.giftrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.content.Intent;
import android.widget.Spinner;

public class GroupSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_search);

        Spinner spinner = (Spinner) findViewById(R.id.dropDownTheme);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
