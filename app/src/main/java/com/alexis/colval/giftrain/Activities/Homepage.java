package com.alexis.colval.giftrain.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.alexis.colval.giftrain.DAL.Repository;
import com.alexis.colval.giftrain.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{

    private static final int PICK_CONTACT = 1;
    private String googleId;
    private int profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        googleId = getIntent().getExtras().getString("google_id");
        if(googleId == null){
            OnClickLogout(null);
        }
        profileId = Repository.getInstance().getProfileByGoogleId(getApplicationContext(), googleId);
    }

    public void OnClickLogout(View v){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        if(mGoogleApiClient.isConnected()) { //this does not fire...
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // bye
                        }
                    });
        }
        finish();
    }

    public void onClickProfile(View v){
        Intent intent = new Intent(this, ProfilePage.class);
        intent.putExtra("google_id", googleId);
        startActivity(intent);
    }

    public void onClickWishlist(View v){
        Intent intent = new Intent(this, Wishlist.class);
        intent.putExtra("google_id",googleId);
        startActivity(intent);
    }

    public void onClickGroupSearch(View v){
        Intent intent = new Intent(this, GroupSearch.class);
        intent.putExtra("profileId", profileId);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // boo
    }

    public void onClickMyGroups(View view) {
        Intent intent = new Intent(this, MyGroups.class);
        intent.putExtra("profileId", profileId);
        startActivity(intent);
    }

    public void onClickInvite(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode == PICK_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                cursor.close();

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, getString(R.string.sms_default), null, null);
                Toast.makeText(getApplicationContext(), "SMS invitation sent", Toast.LENGTH_LONG).show();
            }
        }

    }
}
