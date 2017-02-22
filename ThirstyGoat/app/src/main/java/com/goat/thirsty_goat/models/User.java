package com.goat.thirsty_goat.models;

import java.io.Serializable;

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
}
