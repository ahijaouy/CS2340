package com.goat.thirsty_goat.models;

import android.accounts.Account;

import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;

/**
 * This class represents a user, which can submit a report on water availability and view
 * available water sources.
 */
public class User {
    private static User userSingleton = new User();

    /**
     * Gets the singleton instance of this User class.
     * @return singleton instance of User
     */
    public static User getInstance() {
        return userSingleton;
    }

    private AccountType mAccountType;
    private String mUserName;
    private String mEmail;
    private Credentials mCredentials;

    /**
     * Creates new User
     * @param accountType type of account for authority purposes
     * @param userName name of the user
     * @param email email of the user
     */
    public User(AccountType accountType, String userName, String email) {
        mAccountType = accountType;
        mUserName = userName;
        mEmail = email;
    }

    /**
     * Creates new User with default values.
     */
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

    /**
     * Updates the user profile based on the information stored with Auth0.
     * @param client the client to query Auth0 for information about
     */
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
    /**
     * Allows an account type to be found by the value of it position in the AccountType enum.
     * @return the integer corresponding to the account type
     */
    public int getAccountTypePosition() {
        AccountType[] values = AccountType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == mAccountType) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Allows the account type to be found from a given account type string.
     * @param accountTypeString the string representation of a given account type
     * @return
     */
    private static AccountType getAccountTypeFromString(String accountTypeString) {
        for (AccountType type : AccountType.values()) {
            if (type.toString().equals(accountTypeString)) {
                return type;
            }
        }
        return AccountType.BASICUSER;
    }


    // Getters and setters
    /**
     * Gets this user's account type.
     * @return the user's account type
     */
    public AccountType getAccountType() {
        return mAccountType;
    }

    /**
     * Sets the account type for the user.
     * @param accountType the new account type
     */
    public void setAccountType(AccountType accountType) {
        mAccountType = accountType;
    }

    /**
     * Gets this user's name.
     * @return the user's name
     */
    public String getUserName() {
        return mUserName;
    }

    /**
     * Sets this user's name.
     * @param userName the new name to give the user
     */
    public void setUserName(String userName) {
        mUserName = userName;
    }

    /**
     * Gets this user's email.
     * @return this user's email.
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Sets this user's email.
     * @param email the new email to give the user
     */
    public void setEmail(String email) {
        mEmail = email;
    }

    /**
     * Gets the credentials of this user.
     * @return the credentials of this user
     */
    public Credentials getCredentials() {
        return mCredentials;
    }

    /**
     * Sets the credentials of this user.
     * @param credentials the credentials to give this user
     */
    public void setCredentials(Credentials credentials) {
        mCredentials = credentials;
    }
}