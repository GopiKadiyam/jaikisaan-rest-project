package com.gk.jaikisaan.controllers;

import com.gk.jaikisaan.converters.AccountDetailsConverter;
import com.gk.jaikisaan.models.account.AccountDetails;
import com.gk.jaikisaan.models.responses.ChangePasswordStatus;
import com.gk.jaikisaan.models.responses.CreateAccountResponse;
import com.gk.jaikisaan.models.responses.DeletedAccountResponse;
import com.gk.jaikisaan.models.responses.UpdateAccountResponse;
import com.gk.jaikisaan.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/accounts")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private final LoginService loginService;
    @Autowired
    private AccountDetailsConverter accountDetailsConverter;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    /*
    This api used to create an account in organization. Stored against usernamne which is phone number
    input parameter  :: @accountDetails  --> AccountDetails Object
                        mandatory variables ==> username,password,AccountType and adminkey(only for admins)
    output           ::
                           {
                            "username": "xxxx",
                            "AccountType": "vendor/farmer/admin",
                             "createdStatus": true/false,
                             "errorMessage": "xxxxx"
                            }
    */
    @PostMapping("/createaccount")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody AccountDetails accountDetails) {
        LOGGER.info(" create account service call ");
        ResponseEntity<CreateAccountResponse> accountCreateStatus=null;
        if (accountDetails.getAccountType().equalsIgnoreCase("admin") || accountDetails.getAccountType().equalsIgnoreCase("farmer") || accountDetails.getAccountType().equalsIgnoreCase("vendor")) {
            AccountDetails _accountDetail = accountDetailsConverter.accountDetailRequestConverter(accountDetails);
             accountCreateStatus = loginService.createAccount(_accountDetail);

            return accountCreateStatus;
        } else{
            CreateAccountResponse createAccountResponse=new CreateAccountResponse();
            createAccountResponse.setUsername(accountDetails.getUsername());
            createAccountResponse.setAccountType(accountDetails.getAccountType());
            createAccountResponse.setCreatedStatus(false);
            createAccountResponse.setErrorMessage("Please choose proper user type");
            return new ResponseEntity<>(createAccountResponse,HttpStatus.BAD_REQUEST);

        }
    }
    /*
        This api used to delete account from organization.
        input parameter  :: @username   --> username of account taken from path variable. Mandatory
        output           ::
                {
                "deletedresponse": {
                    "username": "xxx",
                    "isdeleted": true/false,
                    "errormessage": "xxxxx"
                                    }
                }
    */
    @DeleteMapping(value = {"/deleteaccount/{username}"})
    public ResponseEntity<Map<String, DeletedAccountResponse>> deleteAccount(@PathVariable("username") String username) {
        if (username != null) {
            ResponseEntity<Map<String, DeletedAccountResponse>> accountCreateStatus = loginService.deleteAccount(username);
            return accountCreateStatus;
        } else {
            DeletedAccountResponse deletedAccountResponse = new DeletedAccountResponse();
            deletedAccountResponse.setUsername(username);
            deletedAccountResponse.setIsdeleted(false);
            deletedAccountResponse.setErrormessage("username is null");
            Map<String, DeletedAccountResponse> responseMap = new HashMap<>();
            responseMap.put("deletedresponse", deletedAccountResponse);
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
    /*
    This api used to update whole account details in organization. Stored against username
    input parameter  :: @accountDetails  --> AccountDetails Object
    output           ::
         {
            "updateaccountresponse": {
                                "userName": "xxxx",
                                "updateStatus": true/false,
                                "errorMessage": "xxxx"
                                    }
         }
    */
    @PutMapping("/updateaccount")
    public ResponseEntity<Map<String, UpdateAccountResponse>> updateAccount(@RequestBody AccountDetails accountDetails) {
        Map<String,UpdateAccountResponse> responseMap = new HashMap<>();
        UpdateAccountResponse updatedAccountResponse=new UpdateAccountResponse();

        ResponseEntity<Map<String, UpdateAccountResponse>> response=null;

            if (accountDetails.getUsername() != null){
                response = loginService.updateAccount(accountDetails);}
            else{
                updatedAccountResponse.setUpdateStatus(false);
                updatedAccountResponse.setErrorMessage( "username is null");

                responseMap.put("updateaccountresponse",updatedAccountResponse);
                LOGGER.info(" username is null ");

                response=new ResponseEntity<>(responseMap,HttpStatus.BAD_REQUEST);
            }
            return response;
        }

    /*
     This api used to delete account from organization.
    input parameter  :: Object(username,password)      --> both were mandatory
          output           ::
            {
                "userName": "xxxxx",
                "errorMsg": "xxxx",
                "changed": true/false
            }
   */
    @PutMapping("/changepassword")
    public ResponseEntity<ChangePasswordStatus> changePassword(@RequestBody AccountDetails accountDetails) {
        ResponseEntity<ChangePasswordStatus> updateAccountStatus = null;

        if (accountDetails.getUsername() != null) {
            updateAccountStatus=loginService.changePassword(accountDetails.getUsername(),accountDetails.getPassword());
            return updateAccountStatus;
        } else {
            ChangePasswordStatus changePwdStatus=new ChangePasswordStatus();
            changePwdStatus.setChanged(false);
            changePwdStatus.setErrorMsg("User name is null  ,Please enter correct details");
            return new ResponseEntity<>(changePwdStatus, HttpStatus.BAD_REQUEST);
        }

    }
    /*
          This api used to list down  all accounts/respective account type accounts account from organization.
          input parameter  :: @AccountType      -->
                                    it's optional Request param,
                                    without this parameter will give all accounts
                                    when it's present will give respective acounttype accounts
          output           ::
            list of users

           */
    @GetMapping("/users")
    public ResponseEntity<List<Map<String,String>>> getAccountList(@RequestParam(value="accountType") Optional<String> web_AccountType){

        if(web_AccountType.isPresent()){
            String AccountType=web_AccountType.get();
            if (AccountType.equalsIgnoreCase("admin") || AccountType.equalsIgnoreCase("farmer") || AccountType.equalsIgnoreCase("vendor") || AccountType.equalsIgnoreCase("all"))
                return loginService.accountlist(AccountType);
            else
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        else
            return loginService.accountlist("all");

    }

    /*
        This api used to get account details for the given username from organization.
        input parameter  (NOT NULL):: @username      --> it's path variable, without this parameter nothing will be displayed
        output           :: account details of AccountDetails class
         */
    @GetMapping("/users/{username}")
    public ResponseEntity<Map<String,AccountDetails>> getAccountDetails(@PathVariable(value="username") String username){
        ResponseEntity<Map<String,AccountDetails>> response=null;
        Map<String,AccountDetails> responseMap=new HashMap<>();
        if(username != null){
            response=loginService.accountDetail(username);
        }
        else{
            responseMap.put("accountdetails",null);
            response=new ResponseEntity<>(responseMap,HttpStatus.BAD_REQUEST);
        }
        return response;
    }


    /*
     This api used to verify all pending accounts account from organization.
     input parameter  :: @username   --> username of account only for admins
     output           :: verified Status

    @PutMapping("/verifyallaccounts")
    public ResponseEntity<String> verifyAllAccount(@RequestParam String username){
        ResponseEntity<String> verifyAllAccountStatus=loginService.verifyAllAccount(username);
    return verifyAllAccountStatus;
    }
*/


}

