package com.goat.thirsty_goat.models;

import android.accounts.Account;

import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.UserProfile;
import com.goat.thirsty_goat.application.App;

/**
 * Created by GabrielNAN on 2/21/17.
 */

public class User {
    private static User userSingleton = new User();

    public static User getInstance() {
        return userSingleton;
    }

    public static void updateUserSingleton(AuthenticationAPIClient client) {
        final UserProfile[] userProfile = new UserProfile[1];
        client.tokenInfo(App.getInstance().getUserCredentials().getIdToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile payload) {
                        userProfile[0] = payload;
                        String accountType = payload.getUserMetadata().get("account_type").toString();
                        User.setAccountType(getAccountTypeFromString(accountType));
                        User.setUserName(payload.getName());
                        User.setEmail(payload.getEmail());
                    }
                    @Override
                    public void onFailure(AuthenticationException error) {

                    }
                });
    }
    public static int findAccountTypePosition() {
        AccountType[] values = AccountType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == userSingleton.mAccountType) {
                return i;
            }
        }
        return 0;
    }

    private static AccountType getAccountTypeFromString(String accountTypeString) {
        switch(accountTypeString) {
            case "Basic User":
                return AccountType.BASICUSER;
            case "Manager":
                return AccountType.MANAGER;
            case "Worker":
                return AccountType.WORKER;
            default:
                return null;
        }
    }

    public enum AccountType {
        BASICUSER("Basic User"),
        MANAGER("Manager"),
        WORKER("Worker");

        private final String accountType;
        AccountType(String accountType) {
            this.accountType = accountType;
        }

        @Override
        public String toString() {
            return accountType;
        }
    }

    private AccountType mAccountType;
    private String mUserName;
    private String mEmail;

    /**
     * Make a new User
     * @param accountType
     * @param userName
     * @param email
     */
    public User(AccountType accountType, String userName, String email) {
        mAccountType = accountType;
        mUserName = userName;
        mEmail = email;
    }

    public User() {
        this(AccountType.BASICUSER, null, null);
    }

    /**
     * Getters and setters
     */
    public static AccountType getAccountType() {
        return userSingleton.mAccountType;
    }
    public static void setAccountType(AccountType accountType) {
        userSingleton.mAccountType = accountType;
    }
    public static String getUserName() {
        return userSingleton.mUserName;
    }
    public static void setUserName(String userName) {
        userSingleton.mUserName = userName;
    }
    public static String getEmail() {
        return userSingleton.mEmail;
    }
    public static void setEmail(String email) {
        userSingleton.mEmail = email;
    }

}
