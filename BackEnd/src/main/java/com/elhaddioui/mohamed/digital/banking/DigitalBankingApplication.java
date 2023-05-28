package com.elhaddioui.mohamed.digital.banking;


import com.elhaddioui.mohamed.digital.banking.dtos.CustomerDTO;
import com.elhaddioui.mohamed.digital.banking.entities.AccountOperation;
import com.elhaddioui.mohamed.digital.banking.entities.CurrentAccount;
import com.elhaddioui.mohamed.digital.banking.entities.SavingAccount;
import com.elhaddioui.mohamed.digital.banking.enums.AccountStatus;
import com.elhaddioui.mohamed.digital.banking.enums.OperationType;
import com.elhaddioui.mohamed.digital.banking.exceptions.CustomerNotFoundException;
import com.elhaddioui.mohamed.digital.banking.repositories.AccountOperationRepository;
import com.elhaddioui.mohamed.digital.banking.repositories.BankAccountRepository;
import com.elhaddioui.mohamed.digital.banking.repositories.CustomerRepository;
import com.elhaddioui.mohamed.digital.banking.security.entities.AppRole;
import com.elhaddioui.mohamed.digital.banking.security.entities.AppUser;
import com.elhaddioui.mohamed.digital.banking.security.services.AccountService;
import com.elhaddioui.mohamed.digital.banking.services.BankAccountService;
import com.elhaddioui.mohamed.digital.banking.dtos.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;


@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService, AccountService accountService) {
        return args -> {

            accountService.addNewRole(new AppRole(null, "ADMIN"));
            accountService.addNewRole(new AppRole(null, "CUSTOMER"));

//            accountService.addNewUser(new AppUser(null, "Yassine", "1234", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "admin", "1234", new ArrayList<>()));
//            accountService.addNewUser(new AppUser(null, "Hafsa", "1234", new ArrayList<>()));
//            accountService.addNewUser(new AppUser(null, "Moussa", "1234", new ArrayList<>()));

//            accountService.addRoleToUser("Yassine", "CUSTOMER");
            accountService.addRoleToUser("admin", "ADMIN");
//            accountService.addRoleToUser("Hafsa", "CUSTOMER");
//            accountService.addRoleToUser("Moussa", "CUSTOMER");

            Stream.of("Yassine", "Hafsa", "Moussa").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name + "@hotmail.com");
                try {
                    bankAccountService.saveCustomer(customer);
                } catch (CustomerNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random() * 90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }

            });
        };

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
