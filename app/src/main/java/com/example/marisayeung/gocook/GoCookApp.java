package com.example.marisayeung.gocook;

import android.app.Application;
import android.os.Build;

import com.evernote.client.android.EvernoteSession;

/**
 *
 * From Evernote SDK demo
 * @author rwondratschek
 * https://github.com/evernote/evernote-sdk-android/
 *
 */
public class GoCookApp extends Application {

    /*
     * Your Evernote API key. See http://dev.evernote.com/documentation/cloud/
     * Please obfuscate your code to help keep these values secret.
     */
//    private static final String CONSUMER_KEY = "Your consumer key";
//    private static final String CONSUMER_SECRET = "Your consumer secret";

    private static final String CONSUMER_KEY = "marisayeung1-8149";
    private static final String CONSUMER_SECRET = "355336995c53a540";

    /*
     * Initial development is done on Evernote's testing service, the sandbox.
     *
     * TODO: Change to PRODUCTION to use the Evernote production service
     *
     */
    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;

    @Override
    public void onCreate() {
        super.onCreate();

        String consumerKey;
        consumerKey = BuildConfig.EVERNOTE_CONSUMER_KEY;

        String consumerSecret;
        consumerSecret = BuildConfig.EVERNOTE_CONSUMER_SECRET;

        new EvernoteSession.Builder(this)
                .setEvernoteService(EVERNOTE_SERVICE)
                .setForceAuthenticationInThirdPartyApp(true)
                .build(consumerKey, consumerSecret)
                .asSingleton();

        registerActivityLifecycleCallbacks(new LoginChecker());
    }
}
