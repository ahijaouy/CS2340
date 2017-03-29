package com.goat.thirsty_goat.models;

import android.util.Log;

import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;
import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.controllers.App;
import com.goat.thirsty_goat.models.users.Admin;
import com.goat.thirsty_goat.models.users.BasicUser;
import com.goat.thirsty_goat.models.users.Manager;
import com.goat.thirsty_goat.models.users.UserRole;
import com.goat.thirsty_goat.models.users.Worker;

/**
 * This class represents a generic user from which all other user types are derived.
 */
public class UserManager {
    private static final String TAG = UserManager.class.getSimpleName();
    // how can we make this not a UserManager at runtime? Want it to be a subclass
    private static UserManager INSTANCE = new UserManager();

    private static UserRole mCurrentUser;

    /**
     * Gets the singleton instance of this UserManager class.
     * @return singleton instance of UserManager
     */
    public static UserManager getInstance() {
        return INSTANCE;
    }

    private AccountType mAccountType;
    private String mUserName;
    private String mEmail;
    private Credentials mCredentials;

    /**
     * Creates new UserManager
     * @param accountType type of account for authority purposes
     * @param userName name of the user
     * @param email email of the user
     */
    public UserManager(AccountType accountType, String userName, String email) {
        mAccountType = accountType;
        mUserName = userName;
        mEmail = email;
    }

    /**
     * Creates new UserManager with default values.
     */
    public UserManager() {
        this(AccountType.BASIC_USER, null, null);
    }

    public enum AccountType {
        BASIC_USER(App.getResString(R.string.account_type_basic_user)),
        MANAGER(App.getResString(R.string.account_type_manager)),
        WORKER(App.getResString(R.string.account_type_worker)),
        ADMIN(App.getResString(R.string.account_type_admin));

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
                        setCurrentUser(getAccountTypeFromString(accountType));
                        setAccountType(getAccountTypeFromString(accountType));
                        if (payload.getUserMetadata().get("name") != null) {
                            setUserName(payload.getUserMetadata().get("name").toString());
                        }
                        if (payload.getUserMetadata().get("email") != null) {
                            setEmail(payload.getUserMetadata().get("email").toString());
                        }
//                        UserManager.setUserName(payload.getName());
//                        UserManager.setEmail(payload.getEmail());
                    }
                    @Override
                    public void onFailure(AuthenticationException error) {
                        Log.d(TAG, error.getMessage(), error);
                    }
                });
    }


    public void addWaterSourceReport(SourceType type, SourceCondition condition, Location loc) {
        Log.d(TAG, mAccountType.toString());
        mCurrentUser.addWaterSourceReport(type, condition, loc, mUserName);
    }

    public void addWaterPurityReport(PurityCondition condition, double virusPPM, double contaminantPPM, Location loc) {
        mCurrentUser.addWaterPurityReport(condition, mUserName, virusPPM, contaminantPPM, loc);
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
        Log.d(TAG, "getAccountTypeFromString didn't find a match");
        return AccountType.BASIC_USER;
    }

    private void setCurrentUser(AccountType accountType) {
        switch(accountType) {
            case BASIC_USER:
                mCurrentUser = new BasicUser();
                break;
            case WORKER:
                mCurrentUser = new Worker();
                break;
            case MANAGER:
                mCurrentUser = new Manager();
                break;
            case ADMIN:
                mCurrentUser = new Admin();
                break;
            default:
                Log.d(TAG, "You reached the default while setting the current user. This shouldn't happen.");
        }
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