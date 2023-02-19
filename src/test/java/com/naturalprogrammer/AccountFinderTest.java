package com.naturalprogrammer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountFinderTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountFinder accountFinder;

    @Test
    void should_findAccountByUserId_when_userAndAccountExist() {

        // given
        var userId = 45637;
        var user = new User(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        var account = new Account(user);
        given(accountRepository.findByUser(user)).willReturn(Optional.of(account));

        // when, then
        assertThat(accountFinder.findAccountByUserId(userId)).hasValue(account);
    }

    @Test
    void should_notFindAccountByUserId_when_userDoesNotExist() {

        // given
        var userId = 45637;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // when, then
        assertThat(accountFinder.findAccountByUserId(userId)).isEmpty();
    }

    @Test
    void should_notFindAccountByUserId_when_accountDoesNotExist() {

        // given
        var userId = 45637;
        var user = new User(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        given(accountRepository.findByUser(user)).willReturn(Optional.empty());

        // when, then
        assertThat(accountFinder.findAccountByUserId(userId)).isEmpty();
    }

    @Test
    void should_findAccountByUserIdReturningEither_when_userAndAccountExist() {

        // given
        var userId = 45637;
        var user = new User(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        var account = new Account(user);
        given(accountRepository.findByUser(user)).willReturn(Optional.of(account));

        // when
        var either = accountFinder.findAccountByUserIdReturningEither(userId);
        assertThat(either.isRight()).isTrue();
        assertThat(either.getRight()).hasValue(account);
    }

    @Test
    void should_notFindAccountByUserIdReturningEither_when_userDoesNotExist() {

        // given
        var userId = 45637;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // when
        var either = accountFinder.findAccountByUserIdReturningEither(userId);

        // then
        assertThat(either.isLeft()).isTrue();
        assertThat(either.getLeft()).hasValue(new UserNotFound());
    }

    @Test
    void should_notFindAccountByUserIdReturningEither_when_accountDoesNotExist() {

        // given
        var userId = 45637;
        var user = new User(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        given(accountRepository.findByUser(user)).willReturn(Optional.empty());

        // when
        var either = accountFinder.findAccountByUserIdReturningEither(userId);

        // then
        assertThat(either.isLeft()).isTrue();
        assertThat(either.getLeft()).hasValue(new AccountNotFound());
    }
}