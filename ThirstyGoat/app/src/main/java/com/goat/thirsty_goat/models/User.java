package com.goat.thirsty_goat.models;

import android.accounts.Account;

import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;

/**
 * Created by GabrielNAN on 2/21/17.
 */

public class User {
    private static User userSingleton = new User();

    public static User getInstance() {
        return userSingleton;
    }

    private AccountType mAccountType;
    private String mUserName;
    private String mEmail;
    private Credentials mCredentials;

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

    public enum AccountType {
        BASICUSER("Basic User"),
        MANAGER("Manager"),
        WORKER("Worker"),
        ADMIN("Administrator");

        private final String accountType;
        AccountType(String accountType) {
            this.accountType = accountType;
        }

        @Override
        public String toString() {
            return accountType;
        }
    }

    public void updateUserProfile(AuthenticationAPIClient client) {
        client.tokenInfo(mCredentials.getIdToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile payload) {
                        String accountType = payload.getUserMetadata().get("account_type").toString();
                        System.out.print(accountType);
                        setAccountType(getAccountTypeFromString(accountType));
                        if (payload.getUserMetadata().get("name") != null) {
                            setUserName(payload.getUserMetadata().get("name").toString());
                        }
                        if (payload.getUserMetadata().get("email") != null) {
                            setEmail(payload.getUserMetadata().get("email").toString());
                        }
//                        User.setUserName(payload.getName());
//                        User.setEmail(payload.getEmail());
                    }
                    @Override
                    public void onFailure(AuthenticationException error) {

                    }
                });
    }

    public int getAccountTypePosition() {
        AccountType[] values = AccountType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == mAccountType) {
                return i;
            }
        }
        return 0;
    }


    private static AccountType getAccountTypeFromString(String accountTypeString) {
        for (AccountType type : AccountType.values()) {
            if (type.toString().equals(accountTypeString)) {
                return type;
            }
        }
        return AccountType.BASICUSER;
    }
    /**
     * Getters and setters
     */
    public AccountType getAccountType() {
        return mAccountType;
    }
    public void setAccountType(AccountType accountType) {
        mAccountType = accountType;
    }
    public String getUserName() {
        return mUserName;
    }
    public void setUserName(String userName) {
        mUserName = userName;
    }
    public String getEmail() {
        return mEmail;
    }
    public void setEmail(String email) {
        mEmail = email;
    }

    public Credentials getCredentials() {
        return mCredentials;
    }
    public void setCredentials(Credentials credentials) {
        mCredentials = credentials;
    }

}
