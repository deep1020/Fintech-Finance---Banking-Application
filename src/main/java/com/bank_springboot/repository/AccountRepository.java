package com.bank_springboot.repository;

import com.bank_springboot.models.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query(value = "SELECT * FROM accounts WHERE user_id = :user_id", nativeQuery = true)
    List<Account> getUserAccountsById(@Param("user_id")int user_id);

    @Query(value = "SELECT sum(balance) FROM accounts WHERE user_id = :user_id", nativeQuery = true)
    BigDecimal getTotalBalance(@Param("user_id")int user_id);

    @Query(value = "SELECT balance FROM accounts WHERE user_id = :user_id AND account_id = :account_id", nativeQuery = true)
    double getAccountBalance(@Param("user_id") int user_id, @Param("account_id") int account_id);

    @Query(value = "SELECT balance FROM accounts WHERE account_number = :account_number", nativeQuery = true)
    Double getAccountBalancePaymentTo(@Param("account_number") String account_number);

    @Modifying
    @Query(value ="UPDATE accounts SET balance = :new_balance WHERE account_id = :account_id" , nativeQuery = true)
    @Transactional
    void changeAccountBalanceById(@Param("new_balance") double new_balance, @Param("account_id") int account_id);

    @Modifying
    @Query(value ="UPDATE accounts SET balance = :new_beneficiary_balance WHERE account_number = :account_number" , nativeQuery = true)
    @Transactional
    void changeAccountBalanceByAccountNumber(@Param("new_beneficiary_balance") double new_beneficiary_balance, @Param("account_number") String account_number);

//    @Modifying
//    @Query(value ="UPDATE accounts SET balance = :new_beneficiary_balance WHERE account_number = :account_number" , nativeQuery = true)
//    @Transactional
//    void changeAccountBalanceByAccountNumber(@Param("new_beneficiary_balance") double new_beneficiary_balance, @Param("account_number") String account_number);

    @Modifying
    @Query(value = "INSERT INTO accounts(user_id, account_number, account_name, account_type) VALUES" +
            "(:user_id, :account_number, :account_name,:account_type )", nativeQuery = true)
    @Transactional
    void createBankAccount(@Param("user_id") int user_id,
                           @Param("account_number") String account_number,
                           @Param("account_name") String account_name,
                           @Param("account_type")String account_type);

//    @Query(value = "SELECT account_number FROM accounts WHERE user_id = :user_id AND account_id = :account_id", nativeQuery = true)
//    String getAccountNumber(@Param("user_id") int user_id,@Param("account_id") int account_id);

    @Query(value = "SELECT COUNT(*) FROM accounts WHERE account_number = :account_number", nativeQuery = true)
    int countByAccountNumber(@Param("account_number") String accountNumber);

//    @Query(value = "SELECT account_id FROM accounts WHERE account_number = :account_number", nativeQuery = true)
//    String findAccountIdByAccountNumber(@Param("account_number") String accountNumber);

//    @Modifying
//    @Query(value ="UPDATE accounts SET balance = :new_beneficiary_balance WHERE account_number IN (select amount from payments where beneficiary_acc_no = :account_number)" , nativeQuery = true)
//    @Transactional
//    void changeBeneficiaryAccountBalanceById(@Param("new_beneficiary_balance") double new_beneficiary_balance, @Param("account_number") int account_number);

//    @Modifying
//    @Query(value ="UPDATE accounts As sender, accounts As beneficiary SET beneficiary.balance = beneficiary.balance + :amount WHERE sender.account_number = :account_number AND beneficiary.account_number = :account_number" , nativeQuery = true)
//    @Transactional
//    void changeBeneficiaryAccountBalanceById(@Param("new_beneficiary_balance") double new_beneficiary_balance, @Param("account_number") int account_number);

}
