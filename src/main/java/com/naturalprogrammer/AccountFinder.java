package com.naturalprogrammer;

import java.util.Optional;

record User(int id) {}
record Account(User user) {}

interface UserRepository {
    Optional<User> findById(int userId);
}

interface AccountRepository {
    Optional<Account> findByUser(User user);
}

public class AccountFinder {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountFinder(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findAccountByUserId(int userId) {
        return userRepository
                .findById(userId)
                .flatMap(accountRepository::findByUser);
    }
}