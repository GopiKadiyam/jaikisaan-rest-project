package com.gk.jaikisaan.converters;

import com.gk.jaikisaan.models.account.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountDetailsConverter {




    public AccountDetails accountDetailRequestConverter(AccountDetails accountDetails) {

        AccountDetails _accountDetail=new AccountDetails();



        //mapping username
        _accountDetail.setUsername(accountDetails.getUsername());
        //mapping password
        _accountDetail.setPassword(accountDetails.getPassword());
        //mapping user type
        _accountDetail.setAccountType(accountDetails.getAccountType());

        if(accountDetails.getAccountType().equalsIgnoreCase("admin"))
            _accountDetail.setAdminkey(accountDetails.getAdminkey());

  if(accountDetails.getUserDetails()==null){
      _accountDetail.setUserDetails(null);
  }
  else
  {
      UserDetails _userDetails = new UserDetails();

      //mapping name
      Name _name = new Name();
      _name.setFirstName(accountDetails.getUserDetails().getName().getFirstName());
      _name.setLastName(accountDetails.getUserDetails().getName().getLastName());
      _userDetails.setName(_name);
      if(accountDetails.getAccountType().equalsIgnoreCase("admin")){

      }
      else if(accountDetails.getAccountType().equalsIgnoreCase("farmer")){
          //mapping crop names
          List<Crop> _cropNamesList = new ArrayList<Crop>();
          accountDetails.getUserDetails().getCropnames().forEach(crop -> {
              Crop _crop = new Crop();
              _crop.setCropName(crop.getCropName());
              _cropNamesList.add(_crop);
          });
          _userDetails.setCropnames(_cropNamesList);
      }
      else if(accountDetails.getAccountType().equalsIgnoreCase("vendor")){
          //mapping crop names
          List<Vendor> _vendorNamesList = new ArrayList<Vendor>();
          accountDetails.getUserDetails().getVendorTypeNames().forEach(vendor -> {
              Vendor _vendor = new Vendor();
              _vendor.setVendorName(vendor.getVendorName());
              _vendorNamesList.add(_vendor);
          });
          _userDetails.setVendorTypeNames(_vendorNamesList);
      }

      //mapping address
      Address _address = new Address();
      _address.setVillage(accountDetails.getUserDetails().getAddress().getVillage());
      _address.setMandal(accountDetails.getUserDetails().getAddress().getMandal());
      _address.setDistrict(accountDetails.getUserDetails().getAddress().getDistrict());
      _address.setState(accountDetails.getUserDetails().getAddress().getState());
      _address.setCountry(accountDetails.getUserDetails().getAddress().getCountry());
      _address.setPincode(accountDetails.getUserDetails().getAddress().getPincode());
      _userDetails.setAddress(_address);
      //mapping emmail
      _userDetails.setEmail(accountDetails.getUserDetails().getEmail());
      //mapping primary contact number
      _userDetails.setPrimaryPhonenumber(accountDetails.getUserDetails().getPrimaryPhonenumber());

      //mapping secondary contact number
      _userDetails.setSecondaryPhonenumber(accountDetails.getUserDetails().getSecondaryPhonenumber());

      //mapping userdetails
      _accountDetail.setUserDetails(_userDetails);

  }
         return _accountDetail;
    }

    public AccountDetails accountDetailResponseConverter(AccountDetails accountDetails) {

        AccountDetails _accountDetail=new AccountDetails();

        //mapping username
        _accountDetail.setId(accountDetails.getId());
        //mapping username
        _accountDetail.setUsername(accountDetails.getUsername());
        //mapping password
        _accountDetail.setPassword(accountDetails.getPassword());
        //mapping user type
        _accountDetail.setAccountType(accountDetails.getAccountType());

        if(accountDetails.getAccountType().equalsIgnoreCase("admin"))
            _accountDetail.setAdminkey(accountDetails.getAdminkey());

        if(accountDetails.getUserDetails()==null){
            _accountDetail.setUserDetails(null);
        }
        else
        {
            UserDetails _userDetails = new UserDetails();

            //mapping name
            Name _name = new Name();
            _name.setFirstName(accountDetails.getUserDetails().getName().getFirstName());
            _name.setLastName(accountDetails.getUserDetails().getName().getLastName());
            _userDetails.setName(_name);
            if(accountDetails.getAccountType().equalsIgnoreCase("admin")){

            }
            else if(accountDetails.getAccountType().equalsIgnoreCase("farmer")){
                //mapping crop names
                List<Crop> _cropNamesList = new ArrayList<Crop>();
                accountDetails.getUserDetails().getCropnames().forEach(crop -> {
                    Crop _crop = new Crop();
                    _crop.setCropName(crop.getCropName());
                    _cropNamesList.add(_crop);
                });
                _userDetails.setCropnames(_cropNamesList);
            }
            else if(accountDetails.getAccountType().equalsIgnoreCase("vendor")){
                //mapping crop names
                List<Vendor> _vendorNamesList = new ArrayList<Vendor>();
                accountDetails.getUserDetails().getVendorTypeNames().forEach(vendor -> {
                    Vendor _vendor = new Vendor();
                    _vendor.setVendorName(vendor.getVendorName());
                    _vendorNamesList.add(_vendor);
                });
                _userDetails.setVendorTypeNames(_vendorNamesList);
            }

            //mapping address
            Address _address = new Address();
            _address.setVillage(accountDetails.getUserDetails().getAddress().getVillage());
            _address.setMandal(accountDetails.getUserDetails().getAddress().getMandal());
            _address.setDistrict(accountDetails.getUserDetails().getAddress().getDistrict());
            _address.setState(accountDetails.getUserDetails().getAddress().getState());
            _address.setCountry(accountDetails.getUserDetails().getAddress().getCountry());
            _address.setPincode(accountDetails.getUserDetails().getAddress().getPincode());
            _userDetails.setAddress(_address);
            //mapping emmail
            _userDetails.setEmail(accountDetails.getUserDetails().getEmail());
            //mapping primary contact number
            _userDetails.setPrimaryPhonenumber(accountDetails.getUserDetails().getPrimaryPhonenumber());

            //mapping secondary contact number
            _userDetails.setSecondaryPhonenumber(accountDetails.getUserDetails().getSecondaryPhonenumber());

            //mapping userdetails
            _accountDetail.setUserDetails(_userDetails);

        }
        return _accountDetail;
    }

    public AccountDetails updateAccountDetailDBResponseConverter(AccountDetails dbs_AccountDetails,AccountDetails newAccountDetail) {


        //mapping username
        dbs_AccountDetails.setUsername(newAccountDetail.getUsername());
        //mapping password
        dbs_AccountDetails.setPassword(newAccountDetail.getPassword());
        //mapping user type
        dbs_AccountDetails.setAccountType(newAccountDetail.getAccountType());

        if(dbs_AccountDetails.getAccountType().equalsIgnoreCase("admin"))
            dbs_AccountDetails.setAdminkey(newAccountDetail.getAdminkey());


        UserDetails _userDetails = new UserDetails();

        //mapping name
        Name _name = new Name();
        _name.setFirstName(newAccountDetail.getUserDetails().getName().getFirstName());
        _name.setLastName(newAccountDetail.getUserDetails().getName().getLastName());
        _userDetails.setName(_name);
        if(newAccountDetail.getAccountType().equalsIgnoreCase("admin")){

        }
        else if(newAccountDetail.getAccountType().equalsIgnoreCase("farmer")){
            //mapping crop names
            List<Crop> _cropNamesList = new ArrayList<Crop>();
            newAccountDetail.getUserDetails().getCropnames().forEach(crop -> {
                Crop _crop = new Crop();
                _crop.setCropName(crop.getCropName());
                _cropNamesList.add(_crop);
            });
            _userDetails.setCropnames(_cropNamesList);
        }
        else if(newAccountDetail.getAccountType().equalsIgnoreCase("vendor")){
            //mapping crop names
            List<Vendor> _vendorNamesList = new ArrayList<Vendor>();
            newAccountDetail.getUserDetails().getVendorTypeNames().forEach(vendor -> {
                Vendor _vendor = new Vendor();
                _vendor.setVendorName(vendor.getVendorName());
                _vendorNamesList.add(_vendor);
            });
            _userDetails.setVendorTypeNames(_vendorNamesList);
        }

        //mapping address
        Address _address = new Address();
        _address.setVillage(newAccountDetail.getUserDetails().getAddress().getVillage());
        _address.setMandal(newAccountDetail.getUserDetails().getAddress().getMandal());
        _address.setDistrict(newAccountDetail.getUserDetails().getAddress().getDistrict());
        _address.setState(newAccountDetail.getUserDetails().getAddress().getState());
        _address.setCountry(newAccountDetail.getUserDetails().getAddress().getCountry());
        _address.setPincode(newAccountDetail.getUserDetails().getAddress().getPincode());
        _userDetails.setAddress(_address);
        //mapping emmail
        _userDetails.setEmail(newAccountDetail.getUserDetails().getEmail());
        //mapping primary contact number
        _userDetails.setPrimaryPhonenumber(newAccountDetail.getUserDetails().getPrimaryPhonenumber());

        //mapping secondary contact number
        _userDetails.setSecondaryPhonenumber(newAccountDetail.getUserDetails().getSecondaryPhonenumber());

        //mapping userdetails
        dbs_AccountDetails.setUserDetails(_userDetails);


        return dbs_AccountDetails;
    }


}
