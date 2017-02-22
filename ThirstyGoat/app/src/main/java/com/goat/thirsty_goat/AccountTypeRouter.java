package com.goat.thirsty_goat;

import android.content.res.Resources;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.UserProfile;
//import com.auth0.android.authentication.

/**
 * Created by Oberon on 2/21/17.
 */

public class AccountTypeRouter {
    public static void route() {
        AuthenticationAPIClient client = new AuthenticationAPIClient(
                new Auth0("7waRWcyG5OhF05TSWZfQrCVwXQOTWFpq", "myoberon.auth0.com"));


        client.tokenInfo(App.getInstance().getUserCredentials().getIdToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile payload){

                        String account_type = payload.getUserMetadata().get("account_type").toString();
                        System.out.println(account_type);
                    }

                    @Override
                    public void onFailure(AuthenticationException error){
                    }
                });
    }
}
