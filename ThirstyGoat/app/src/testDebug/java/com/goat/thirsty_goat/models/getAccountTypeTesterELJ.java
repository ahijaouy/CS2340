//package com.goat.thirsty_goat.models;
//import com.goat.thirsty_goat.models.users.Worker; //do this for each
//
//import com.goat.thirsty_goat.R;
//import com.goat.thirsty_goat.controllers.App;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static com.goat.thirsty_goat.models.UserManager.getInstance;
//import static org.junit.Assert.*;
//
///**
// * Created by laddjones on 4/10/17.
// */
//public class getAccountTypeTesterELJ {
//
//    public UserManager lmfao = new UserManager();
//
//    @Before
//    public void setup() {
//        lmfao = UserManager.getInstance();
//    }
//    @Test
//    public void getAccountTypePosition() throws Exception {
//        System.out.println("got to start");
//
//        System.out.println("made it past lmfao");
//        lmfao.setAccountType(UserManager.AccountType.BASIC_USER);
//
//        System.out.println(lmfao.getAccountType().toString());
//        assertEquals(UserManager.getInstance().getAccountTypeFromString("Basic"), UserManager.AccountType.BASIC_USER);
//    }
//
//}