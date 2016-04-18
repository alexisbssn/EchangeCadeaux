package com.alexis.colval.giftrain;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexis.colval.giftrain.DAL.DatabaseContracts;
import com.alexis.colval.giftrain.DAL.DatabaseHelper;
import com.alexis.colval.giftrain.DAL.ProfileHelper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfilePage extends AppCompatActivity {

    //temporary no-db variables
    //String firstName="Alexis",lastName="Buisson",email="alexis.buisson@hotmail.com",region="Montérégie";
    //Date birthDate = new Date(1996 + 1900, 3, 27);

    private String googleId;
    private ProfileHelper mProfileHelper;

    private TextView mTitle;
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mEmail;
    private TextView mRegion;
    private TextView mBirthDate;

    private KeyListener mFirstNameKeyListener;
    private KeyListener mLastNameKeyListener;
    private KeyListener mEmailKeyListener;
    private KeyListener mRegionKeyListener;
    private KeyListener mBirthDateKeyListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        googleId = getIntent().getExtras().getString("google_id");
        mTitle = (TextView)findViewById(R.id.ProfileTitle);
        mFirstName = (TextView)findViewById(R.id.firstName);
        mLastName = (TextView)findViewById(R.id.lastName);
        mEmail = (TextView)findViewById(R.id.email);
        mRegion = (TextView)findViewById(R.id.region);
        mBirthDate = (TextView)findViewById(R.id.birthDate);

        registerKeyListeners();
        setEditable(false);
        populate();
    }

    private void registerKeyListeners(){
        mFirstNameKeyListener = mFirstName.getKeyListener();
        mLastNameKeyListener = mLastName.getKeyListener();
        mEmailKeyListener = mEmail.getKeyListener();
        mRegionKeyListener = mRegion.getKeyListener();
        mBirthDateKeyListener = mBirthDate.getKeyListener();
    }

    private void populate(){
        String[] projection = {
                DatabaseContracts.ProfileEntry.COLUMN_NAME_FNAME,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_LNAME,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_BDATE,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_EMAIL,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_REGION,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_PHOTO_URL
        };

        String selection = DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID + "=?";
        String[] selectionArgs = { googleId };

        Cursor c = DatabaseHelper.getInstance(getApplicationContext()).query(
                DatabaseContracts.ProfileEntry.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );
        c.moveToNext();
        String fName = c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_FNAME));
        String lName = c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_LNAME));
        String bDate = c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_BDATE));
        String email = c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_EMAIL));
        String region = c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_REGION));
        final String photoUrl = c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_PHOTO_URL));
        if(photoUrl != null) { //Retrieving image from network, needs a thread
            ImageView iv = (ImageView)findViewById(R.id.profilePicture);
            BitmapWorkerTask task = new BitmapWorkerTask(iv, photoUrl);
            task.execute();
        }

        mTitle.setText(fName + "'s Profile");
        mFirstName.setText(fName);
        mLastName.setText(lName);
        mEmail.setText(email);
        mRegion.setText(region);
        mBirthDate.setText(bDate);
    }

    private void saveData() {
        String strFilter = DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID + "=?";
        ContentValues args = new ContentValues();

        args.put(DatabaseContracts.ProfileEntry.COLUMN_NAME_FNAME, mFirstName.getText().toString());
        args.put(DatabaseContracts.ProfileEntry.COLUMN_NAME_LNAME, mLastName.getText().toString());
        args.put(DatabaseContracts.ProfileEntry.COLUMN_NAME_BDATE, mBirthDate.getText().toString());
        args.put(DatabaseContracts.ProfileEntry.COLUMN_NAME_EMAIL, mEmail.getText().toString());
        args.put(DatabaseContracts.ProfileEntry.COLUMN_NAME_REGION, mRegion.getText().toString());

        DatabaseHelper.getInstance(getApplicationContext()).update(DatabaseContracts.ProfileEntry.TABLE_NAME, args, strFilter, new String[]{googleId});
    }

    private void setEditable(boolean editable){

        mFirstName.setFocusable(editable);
        mFirstName.setClickable(editable);
        mFirstName.setFocusableInTouchMode(editable);
        mLastName.setFocusable(editable);
        mLastName.setClickable(editable);
        mLastName.setFocusableInTouchMode(editable);
        mEmail.setFocusable(editable);
        mEmail.setClickable(editable);
        mEmail.setFocusableInTouchMode(editable);
        mRegion.setFocusable(editable);
        mRegion.setClickable(editable);
        mRegion.setFocusableInTouchMode(editable);
        mBirthDate.setFocusable(editable);
        mBirthDate.setClickable(editable);
        mBirthDate.setFocusableInTouchMode(editable);

        Button edit_cancel = (Button)findViewById(R.id.buttonEdit);
        Button finished_back = (Button)findViewById(R.id.buttonDone);

        if(editable){
            mFirstName.setKeyListener(mFirstNameKeyListener);
            mLastName.setKeyListener(mLastNameKeyListener);
            mEmail.setKeyListener(mEmailKeyListener);
            mRegion.setKeyListener(mRegionKeyListener);
            mBirthDate.setKeyListener(mBirthDateKeyListener);
            edit_cancel.setText(R.string.button_cancel);
            finished_back.setText(R.string.button_finished);
        }else {
            mFirstName.setKeyListener(null);
            mLastName.setKeyListener(null);
            mEmail.setKeyListener(null);
            mRegion.setKeyListener(null);
            mBirthDate.setKeyListener(null);
            edit_cancel.setText(R.string.button_edit);
            finished_back.setText(R.string.button_back);
        }
    }

    public void editButton(View view) {
        boolean currentlyLocked = (mFirstName.getKeyListener() == null); //if not editable, true, and vice versa
        setEditable(currentlyLocked); //swap between editable and not
        populate();
    }

    public void doneButton(View view) {
        boolean currentlyLocked = (mFirstName.getKeyListener() == null); //if not editable, true, and vice versa
        if(!currentlyLocked){
            saveData();
            setEditable(currentlyLocked); //false
        }else {
            finish(); //get out of the activity
        }
    }

    // Function doInBackground from http://stackoverflow.com/questions/11831188/how-to-get-bitmap-from-a-url-in-android
    // Rest of the class from http://developer.android.com/training/displaying-bitmaps/process-bitmap.html
    private class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;
        private String urlString;

        public BitmapWorkerTask(ImageView imageView, String imageUrl) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
            urlString = imageUrl;
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
