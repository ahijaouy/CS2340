package com.goat.thirsty_goat.models;

/**
 * Created by GabrielNAN on 2/21/17.
 */

public class User {
    public enum AccountType {
        USER("Basic User"),
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
        this(AccountType.USER, null, null);
    }
}
