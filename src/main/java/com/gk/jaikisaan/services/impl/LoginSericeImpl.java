package com.gk.jaikisaan.services.impl;

import com.gk.jaikisaan.converters.AccountDetailsConverter;
import com.gk.jaikisaan.models.account.AccountDetails;
import com.gk.jaikisaan.models.responses.ChangePasswordStatus;
import com.gk.jaikisaan.models.responses.CreateAccountResponse;
import com.gk.jaikisaan.models.responses.DeletedAccountResponse;
import com.gk.jaikisaan.models.responses.UpdateAccountResponse;
import com.gk.jaikisaan.services.LoginService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class LoginSericeImpl implements LoginService {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginSericeImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private AccountDetailsConverter accountDetailsConverter;
    @Override
    public ResponseEntity<CreateAccountResponse> createAccount(AccountDetails accountDetail) {
        CreateAccountResponse createAccountResponse=new CreateAccountResponse();
        try {
            LOGGER.info("checking with existing accounts");
            // Getting list of usernames
            Query query = new Query();
            LOGGER.info(" checking whether username existed or not");
            query.addCriteria(Criteria.where("username").is(accountDetail.getUsername()));
            LOGGER.info(" Query to check username existance ::  "+query);
            boolean existOrNot=(mongoTemplate.find(query,AccountDetails.class).stream().count()) > 0;
            System.out.println("exists or not "+existOrNot);

            if (existOrNot) {
                LOGGER.info("account already exists");
                createAccountResponse.setUsername(accountDetail.getUsername());
                createAccountResponse.setAccountType(accountDetail.getAccountType());
                //createAccountResponse.setVerified(true);
                createAccountResponse.setCreatedStatus(false);
                createAccountResponse.setErrorMessage("Account already exists with this username, Please visit login page and try with another username");
                return new ResponseEntity<>(createAccountResponse, HttpStatus.OK);
            }
            else {
                //creating account
                mongoTemplate.save(accountDetail);
                createAccountResponse.setUsername(accountDetail.getUsername());
                createAccountResponse.setAccountType(accountDetail.getAccountType());

                createAccountResponse.setCreatedStatus(true);
                createAccountResponse.setErrorMessage(null);
                LOGGER.info("account created");
                return new ResponseEntity<>(createAccountResponse, HttpStatus.CREATED);
            }
        }
        catch (Exception e) {
            createAccountResponse.setUsername(accountDetail.getUsername());
            createAccountResponse.setAccountType(accountDetail.getAccountType());

            createAccountResponse.setCreatedStatus(false);
            createAccountResponse.setErrorMessage(e.toString());
            LOGGER.info("unexpected tecnical error while creating the account");
            return new ResponseEntity<>(createAccountResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<List<Map<String,String>>> accountlist(String accountType){

        try {
            Query query=new Query();
            if(accountType.equalsIgnoreCase("admin")){
                query.addCriteria(Criteria.where("accountType").is("admin"));
                query.fields().include("username").include("accountType").exclude("_id");
                LOGGER.info("Query to display usernames and type based on projection(for admins) :: "+query);
            }
            else if(accountType.equalsIgnoreCase("vendor")){
                query.addCriteria(Criteria.where("accountType").is("vendor"));
                query.fields().include("username").include("accountType").exclude("_id");
                LOGGER.info("Query to display usernames and type based on projection(for vendor) :: "+query);
            }
            else if(accountType.equalsIgnoreCase("farmer")){
                query.addCriteria(Criteria.where("accountType").is("farmer"));
                query.fields().include("username").include("accountType").exclude("_id");
                LOGGER.info("Query to display usernames and type based on projection(for farmers) :: "+query);
            }
            else {
                //query.addCriteria(Criteria.where("accountType").is("farmer"));
                query.fields().include("username").include("accountType").exclude("_id");
                LOGGER.info("Query to display usernames and type based on projection(for farmers) :: "+query);
            }
            System.out.println("Query "+query);
            List<AccountDetails> dbResponse=mongoTemplate.find(query,AccountDetails.class);
            List<Map<String,String>> userNamesList=new ArrayList<Map<String,String>>();
            dbResponse.forEach(k->{
                Map<String ,String> user=new HashMap<>();
                user.put("username",k.getUsername());
                user.put("accountType",k.getAccountType());
                userNamesList.add(user);
            });
            LOGGER.info(" accountlist fetched ");

            return new ResponseEntity<>(userNamesList,HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.info(" un expected technical error while fecthing list ");
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, DeletedAccountResponse>> deleteAccount(String userName){
        DeletedAccountResponse deletedAccountResponse=new DeletedAccountResponse();
        Map<String, DeletedAccountResponse> responseMap=new HashMap<>();
        try{
          Query query=new Query();
          query.addCriteria(Criteria.where("username").is(userName));
          AccountDetails _accountDetails=new AccountDetails();
            LOGGER.info(" checking whether user existed or not");

          boolean existsOrNot=(mongoTemplate.find(query,AccountDetails.class).stream().count()) >0;
            if(existsOrNot) {
                DeleteResult deleteResult = mongoTemplate.remove(query, AccountDetails.class);
                System.out.println("out "+deleteResult.getDeletedCount()+"out2 "+deleteResult.toString());
                if(deleteResult.getDeletedCount()>0) {
                    deletedAccountResponse.setUsername(userName);
                    deletedAccountResponse.setIsdeleted(true);
                    deletedAccountResponse.setErrormessage(null);
                    responseMap.put("deletedresponse",deletedAccountResponse);
                    return new ResponseEntity<>(responseMap, HttpStatus.OK);
                }
                else {
                    LOGGER.info("delete operation not happened properly ");
                    deletedAccountResponse.setUsername(userName);
                    deletedAccountResponse.setIsdeleted(false);
                    deletedAccountResponse.setErrormessage("Unable to delete " + userName + ".  Please try again. ");
                    responseMap.put("deletedresponse",deletedAccountResponse);
                    return new ResponseEntity<>(responseMap, HttpStatus.OK);
                }
            }
          //mongoTemplate.findAndRemove(query,AccountDetails.class);}
            else{
                LOGGER.info(" user is not existed in the organization ");
                deletedAccountResponse.setUsername(userName);
                deletedAccountResponse.setIsdeleted(false);
                deletedAccountResponse.setErrormessage(" " + userName + " account not exists.Please check and try again ");
                responseMap.put("deletedresponse",deletedAccountResponse);
                return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
            }

      }
      catch(Exception e){
          LOGGER.info(" un expected techincal error while deleting the account ");

          deletedAccountResponse.setUsername(userName);
          deletedAccountResponse.setIsdeleted(false);
          deletedAccountResponse.setErrormessage(e.getMessage());
          responseMap.put("deletedresponse",deletedAccountResponse);
          return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, UpdateAccountResponse>> updateAccount(AccountDetails newaccountDetail) {
        UpdateAccountResponse updatedAccountResponse=new UpdateAccountResponse();
        Map<String,UpdateAccountResponse> responseMap = new HashMap<>();
        ResponseEntity<Map<String, UpdateAccountResponse>> response=null;
        try {
                Query findQuery=new Query();
                findQuery.addCriteria(Criteria.where("username").is(newaccountDetail.getUsername()));
            LOGGER.info(" Query to find the document with username :: "+findQuery);

            AccountDetails dbs_AccountDetails=mongoTemplate.findOne(findQuery,AccountDetails.class);

                if(dbs_AccountDetails != null){
                    accountDetailsConverter.updateAccountDetailDBResponseConverter(dbs_AccountDetails,newaccountDetail);
                    mongoTemplate.save(dbs_AccountDetails);
                    updatedAccountResponse.setUserName(newaccountDetail.getUsername());
                    updatedAccountResponse.setUpdateStatus(true);
                    updatedAccountResponse.setErrorMessage(null);

                    responseMap.put("updateaccountresponse",updatedAccountResponse);
                    LOGGER.info(" account updated ");

                    response=new ResponseEntity<>(responseMap,HttpStatus.OK);
                }
                else{
                    updatedAccountResponse.setUserName(newaccountDetail.getUsername());
                    updatedAccountResponse.setUpdateStatus(false);
                    updatedAccountResponse.setErrorMessage(newaccountDetail.getUsername()+" not existed in the organization");

                    responseMap.put("updateaccountresponse",updatedAccountResponse);
                    LOGGER.info(" username not existed in the organization ");

                    response=new ResponseEntity<>(responseMap,HttpStatus.NOT_FOUND);
                }

         } catch (Exception e) {
            updatedAccountResponse.setUserName(newaccountDetail.getUsername());
            updatedAccountResponse.setUpdateStatus(false);
            updatedAccountResponse.setErrorMessage(e.getMessage());

            responseMap.put("updateaccountresponse",updatedAccountResponse);
            LOGGER.info(" un expected techincal error while updating the account ");

            response=new ResponseEntity<>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  response;
    }

    @Override
    public ResponseEntity<ChangePasswordStatus> changePassword(String username, String password){
        ChangePasswordStatus changePwdStatus=new ChangePasswordStatus();
        try {
            Query findFieldQuery = new Query();
            findFieldQuery.addCriteria(Criteria.where("username").is(username));
            Update update = new Update();
            update.set("password", password);
            UpdateResult updateResult = mongoTemplate.updateFirst(findFieldQuery, update, AccountDetails.class);
            if (updateResult.getModifiedCount() >= 1) {
                changePwdStatus.setChanged(true);
                changePwdStatus.setUserName(username);
                changePwdStatus.setErrorMsg(null);
                return new ResponseEntity<>(changePwdStatus, HttpStatus.OK);
            } else if (updateResult.getModifiedCount() == 0) {
                changePwdStatus.setChanged(false);
                changePwdStatus.setUserName(username);
                changePwdStatus.setErrorMsg(" User name is not exists ,Please enter correct details");
                return new ResponseEntity<>(changePwdStatus, HttpStatus.NOT_FOUND);
            } else {
                changePwdStatus.setChanged(false);
                changePwdStatus.setUserName(username);
                changePwdStatus.setErrorMsg("password not changed ,Please enter correct details");
                return new ResponseEntity<>(changePwdStatus, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            }
        catch (Exception e){
            changePwdStatus.setChanged(false);
            changePwdStatus.setUserName(username);
            changePwdStatus.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(changePwdStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String,AccountDetails>> accountDetail(String username){
        ResponseEntity<Map<String,AccountDetails>> response=null;
        Map<String,AccountDetails> responseMap=new HashMap<>();
        try{
            Query findQuery=new Query();
            findQuery.addCriteria(Criteria.where("username").is(username));
            LOGGER.info(" Query to find the account with username :: "+findQuery);
            AccountDetails dbs_AccountDetails=mongoTemplate.findOne(findQuery,AccountDetails.class);
            if(dbs_AccountDetails!=null){
                AccountDetails _accountDetails=accountDetailsConverter.accountDetailResponseConverter(dbs_AccountDetails);
                responseMap.put("accountdetails",_accountDetails);
                response=new ResponseEntity<>(responseMap,HttpStatus.OK);
            }
            else{
                responseMap.put("accountdetails",null);
                response=new ResponseEntity<>(responseMap,HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            responseMap.put("accountdetails",null);
            response=new ResponseEntity<>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    /*
    @Override
    public ResponseEntity<String> verifyAllAccount(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        AccountDetails accountDetails = mongoTemplate.findOne(query, AccountDetails.class);
        if(accountDetails == null)
            return new ResponseEntity<>("Account not present", HttpStatus.NOT_FOUND);
        else if ((accountDetails!=null) && (accountDetails.getAccountType().equalsIgnoreCase("admin")) && (accountDetails.isAccountVerified()) ) {
            Query searchQuery = new Query();
            searchQuery.addCriteria(Criteria.where("isAccountVerified").is(false).orOperator(Criteria.where("accountType").is("farmer"),Criteria.where("accountType").is("vendor")));
            Update updateQuery = new Update();
            updateQuery.set("isAccountVerified", true);
             System.out.println("search query "+searchQuery+"\n update query ::"+updateQuery);
            UpdateResult result = mongoTemplate.updateMulti(searchQuery, updateQuery, AccountDetails.class);
            if (result.getMatchedCount() > 0)
                return new ResponseEntity<>("All "+result.getMatchedCount()+" got accounts verified", HttpStatus.OK);
            else if (result.getMatchedCount() == 0)
                return new ResponseEntity<>("all Accounts got verified already . nothing pending", HttpStatus.OK);
            else
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
         }
        else {
            return new ResponseEntity<>("Unauthorised access to perform action", HttpStatus.UNAUTHORIZED);
        }
    }
*/
}
