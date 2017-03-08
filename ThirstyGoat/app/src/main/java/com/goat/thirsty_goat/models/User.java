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

    /**
     * Gets the singleton instance of this User class.
     * @return singleton instance of User
     */
    public static User getInstance() {
        return userSingleton;
    }

    /**
     * Updates the current user's name and information.
     * @param client the client to try and authenticate
     */
    public static void updateUserSingleton(AuthenticationAPIClient client) {
        client.tokenInfo(App.getInstance().getUserCredentials().getIdToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile payload) {
                        String accountType = payload.getUserMetadata().get("account_type").toString();
                        System.out.print(accountType);
                        User.setAccountType(getAccountTypeFromString(accountType));
                        if (payload.getUserMetadata().get("name") != null) {
                            User.setUserName(payload.getUserMetadata().get("name").toString());
                        }
                        if (payload.getUserMetadata().get("email") != null) {
                            User.setEmail(payload.getUserMetadata().get("email").toString());
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
    public static int findAccountTypePosition() {
        AccountType[] values = AccountType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == userSingleton.mAccountType) {
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

    /**
     * An enum representing the 4 different account types possible for users.
     */
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
     * Gets this user's account type.
     * @return the user's account type
     */
    public static AccountType getAccountType() {
        return userSingleton.mAccountType;
    }

    /**
     * Sets the account type for the user.
     * @param accountType the new account type
     */
    public static void setAccountType(AccountType accountType) {
        userSingleton.mAccountType = accountType;
    }

    /**
     * Gets this user's name.
     * @return the user's name
     */
    public static String getUserName() {
        return userSingleton.mUserName;
    }

    /**
     * Sets this user's name.
     * @param userName the new name to give the user
     */
    public static void setUserName(String userName) {
        userSingleton.mUserName = userName;
    }

    /**
     * Gets this user's email.
     * @return this user's email.
     */
    public static String getEmail() {
        return userSingleton.mEmail;
    }

    /**
     * Sets this user's email.
     * @param email the new email to give the user
     */
    public static void setEmail(String email) {
        userSingleton.mEmail = email;
    }

}
