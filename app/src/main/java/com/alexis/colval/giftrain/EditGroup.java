package com.alexis.colval.giftrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alexis.colval.giftrain.DAL.Repository;
import com.alexis.colval.giftrain.Model.Group;

public class EditGroup extends AppCompatActivity {

    Spinner groupScope;
    EditText groupName, description, drawInterval, theme;
    Group group;
    int creatorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        // Scope spinner
        groupScope = (Spinner) findViewById(R.id.groupScope);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.group_scopes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupScope.setAdapter(adapter);
        //other fields
        groupName = (EditText) findViewById(R.id.groupName);
        description = (EditText) findViewById(R.id.groupDescription);
        drawInterval = (EditText) findViewById(R.id.drawInterval);
        theme = (EditText) findViewById(R.id.groupTheme);
        //fill if not a new group
        group = getIntent().getParcelableExtra("existingGroup");
        creatorId = getIntent().getExtras().getInt("creatorId", -1);
        if(group != null){
            groupName.setText(group.getGroupName());
            description.setText(group.getGroupDescription());
            groupScope.setSelection(group.getScope());
            theme.setText(group.getTheme());
            if(creatorId == -1){
                creatorId = group.getCreatorId();
            }
        }
    }

    public void onClickCancelEditGroup(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClickDoneEditGroup(View view) {
        String name = groupName.getText().toString();
        String descr = description.getText().toString();
        int interval = Integer.parseInt(drawInterval.getText().toString());
        int scope = groupScope.getSelectedItemPosition();
        String groupTheme = theme.getText().toString();
        if(name == null || descr == null || groupTheme == null){
            Toast.makeText(getApplicationContext(), R.string.edit_group_error, Toast.LENGTH_LONG).show();
            return;
        }
        if(group == null){
            Repository.getInstance().addGroup(getBaseContext(), name, descr, scope, creatorId, interval, groupTheme);
        }else{
            Repository.getInstance().updateGroup(getBaseContext(), group.getId(), name, descr, scope, creatorId, interval, group.getNextDraw(), groupTheme);
        }
        setResult(RESULT_OK);
        finish();
    }
}
