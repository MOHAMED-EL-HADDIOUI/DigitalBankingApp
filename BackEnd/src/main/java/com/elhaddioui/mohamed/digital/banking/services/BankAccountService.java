package com.elhaddioui.mohamed.digital.banking.services;

import com.elhaddioui.mohamed.digital.banking.dtos.*;
import com.elhaddioui.mohamed.digital.banking.entities.Customer;
import com.elhaddioui.mohamed.digital.banking.exceptions.BalanceNotSufficientException;
import com.elhaddioui.mohamed.digital.banking.exceptions.CustomerNotFoundException;
import com.elhaddioui.mohamed.digital.banking.dtos.*;
import com.elhaddioui.mohamed.digital.banking.entities.*;
import com.elhaddioui.mohamed.digital.banking.exceptions.BankAccountNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer) throws CustomerNotFoundException;

    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) throws CustomerNotFoundException;

    List<CustomerDTO> listCustomers(int page);

    List<Customer> listCustomer();

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFound;

    void debit(String accountId, double amount, String description) throws BalanceNotSufficientException, BankAccountNotFound;

    void credit(String accountId, double amount, String description) throws BankAccountNotFound;

    void transfer(String accountIdSource, String accountIdDestination, double amount,String description) throws BankAccountNotFound, BalanceNotSufficientException;

    List<BankAccountDTO> bankAccountListOfCustomer(Long customerId);

    BankAccountsDTO getBankAccountList(int page);

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountOperationHistory(String accountId);

    AccountHistoryDTO getAccoutHistory(String accountId, int page, int size) throws BankAccountNotFound;

    CustomersDTO getCustomerByName(String keyword, int page) throws CustomerNotFoundException;

    BankAccountDTO updateBankAccount(BankAccountDTO bankAccountDTO);

    CustomerDTO getCustomerByName(String name);
}
