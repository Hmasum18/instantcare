package com.memoryleak.instantcare.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.memoryleak.instantcare.dagger.anotation.MainActivityScope;

import javax.inject.Inject;

/**
 * @author  Hasan Masum
 */
@MainActivityScope
public class GoogleSignInHelper{

    private static final String TAG = "GoogleSignInHelper";
    private Context context;

    private final GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;


    // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative/63654043#63654043
    private final ActivityResultLauncher<Intent> googleSignInActivity;

    // google sign in listeners ========================================================
    private OnGoogleSignInSuccessListener onGoogleSignInSuccessListener;

    public interface OnGoogleSignInSuccessListener{
        void onGoogleSignInSuccess(GoogleSignInAccount googleSignInAccount);
        void onGoogleSignInFailure(Exception e);
    }

    public void setOnGoogleSignInSuccessListener(OnGoogleSignInSuccessListener onGoogleSignInSuccessListener) {
        this.onGoogleSignInSuccessListener = onGoogleSignInSuccessListener;
    }

    // Constructor ==================================================================================
    /**
     *
     * @param context is the application context
     * @param activityResultCaller can be a activity or fragment as the both implements {@link ActivityResultCaller}
     */
    @Inject
    public GoogleSignInHelper(Context context, ActivityResultCaller activityResultCaller) {
        this.context = context;

        googleSignInActivity = activityResultCaller.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                activityResult -> {
                    if (activityResult.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = activityResult.getData();
                        // your operation....

                        // parse google sign in info now
                        // we will get a new GoogleSignInAccount Object on success
                        parseData(intent);
                    }
                });

        ///google sign in step one
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // .requestIdToken(BuildConfig.GOOGLE_SIGN_IN_SERVER_CLIENT_ID)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context,gso);

        ///chk if the user is already signed in with google account or not
        //if signed in then we will find an instance from SharedPreferences of GoogleSignIn
        //otherwise null
        //if signed in then the user go to get start fragment to feed fragment
        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);
    }

    // public apis ===========================================================
    /**
     * launch the google sign in dialog
     */
    public void startGoogleSignInActivity(){
        googleSignInActivity.launch(mGoogleSignInClient.getSignInIntent());
    }

    public void signOut(){
        mGoogleSignInClient.signOut();
    }

    public GoogleSignInAccount getGoogleSignInAccount(){
        return mGoogleSignInAccount;
    }

    // private apis ===========================================================

    /**
     * This method should be called from onActivityResultMethod()
     * when response is received from google sign in intent with data.
     *
     * Here we parse the id token from google sign in intent and
     * send the id token to the listener with {@link OnGoogleSignInSuccessListener#onGoogleSignInSuccess(GoogleSignInAccount)}
     * method on successful parse.
     *
     * @param data is the data sent from google sing in intent
     */
    private void parseData(Intent data){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // mGoogleSignInAccount = task.getResult(ApiException.class);

            //as signed in account info is already stored in a sharedPref we get the info from that
            //store the user data is local cache
            mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);

            if(mGoogleSignInAccount == null)
                throw new NullPointerException("google sign in client is null");

            if(onGoogleSignInSuccessListener!=null)
                onGoogleSignInSuccessListener.onGoogleSignInSuccess(mGoogleSignInAccount);
        }catch (Exception e) {
            //e.printStackTrace();
            if(onGoogleSignInSuccessListener!=null)
                onGoogleSignInSuccessListener.onGoogleSignInFailure(e);
            Log.d(TAG, "onActivityResult():Google sign in failed");
        }
    }
}
