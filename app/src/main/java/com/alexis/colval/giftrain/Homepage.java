package com.alexis.colval.giftrain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alexis.colval.giftrain.DAL.Repository;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class Homepage extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{

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
        intent.putExtra("google_id",googleId);
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
}
