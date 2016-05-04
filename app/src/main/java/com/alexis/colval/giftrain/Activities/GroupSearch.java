package com.alexis.colval.giftrain.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.Intent;
import android.widget.Spinner;

import com.alexis.colval.giftrain.DAL.Repository;
import com.alexis.colval.giftrain.Model.Group;
import com.alexis.colval.giftrain.R;

import java.util.List;

public class GroupSearch extends AppCompatActivity {

    private AutoCompleteTextView searchbar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_search);

        spinner = (Spinner) findViewById(R.id.dropDownTheme);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerActivity());
        searchbar = (AutoCompleteTextView)findViewById(R.id.searchBar);
        String[] array = getResources().getStringArray(R.array.search);
        searchbar.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, array));
    }

    public void onClickSearch(View view) {
        search();
    }

    private void search(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.searchResults);
        linearLayout.removeAllViews();
        String theme;
        if(spinner.getSelectedItemPosition() != 0){
            theme = spinner.getSelectedItem().toString();
        }else{
            if(searchbar.getText() == null || searchbar.getText().toString().equals(""))
                return;
            theme = "";
        }
        List<Group> groups = Repository.getInstance().findGroupsByName(getApplicationContext(), searchbar.getText().toString(), theme);
        for(final Group g : groups){
            Button myButton = new Button(getApplicationContext());
            myButton.setText(g.getGroupName());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            myButton.setLayoutParams(lp);
            linearLayout.addView(myButton);
            View.OnClickListener groupButtonListener = new View.OnClickListener(){
                public void onClick(View button){
                    Intent intent = new Intent(getApplicationContext(), GroupPage.class);
                    Bundle b = new Bundle();
                    b.putParcelable("existingGroup", g);
                    intent.putExtras(b);
                    intent.putExtra("profileId", getIntent().getExtras().getInt("profileId"));
                    startActivity(intent);
                }
            };
            myButton.setOnClickListener(groupButtonListener);
        }
    }

    private class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public SpinnerActivity() {
        }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            search();
        }

        public void onNothingSelected(AdapterView<?> parent) {
            search();
        }
    }
}
