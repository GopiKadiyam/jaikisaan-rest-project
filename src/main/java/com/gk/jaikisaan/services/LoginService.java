package com.gk.jaikisaan.services;

import com.gk.jaikisaan.models.account.AccountDetails;
import com.gk.jaikisaan.models.responses.ChangePasswordStatus;
import com.gk.jaikisaan.models.responses.CreateAccountResponse;
import com.gk.jaikisaan.models.responses.DeletedAccountResponse;
import com.gk.jaikisaan.models.responses.UpdateAccountResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LoginService {

    ResponseEntity<CreateAccountResponse> createAccount(AccountDetails accountDetails);
    ResponseEntity<Map<String, DeletedAccountResponse>> deleteAccount(String userName);
    ResponseEntity<Map<String, UpdateAccountResponse>> updateAccount(AccountDetails accountDetails);
    ResponseEntity<ChangePasswordStatus> changePassword(String username, String password);
    //ResponseEntity<String> verifyAllAccount(String username);
    ResponseEntity<List<Map<String,String>>> accountlist(String userType);
    ResponseEntity<Map<String,AccountDetails>> accountDetail(String username);
}
