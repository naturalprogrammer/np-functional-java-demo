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
    void findAccountByUserId() {

        // given
        var userId = 45637;
        var user = new User(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        var account = new Account(user);
        given(accountRepository.findByUser(user)).willReturn(Optional.of(account));

        // when, then
        assertThat(accountFinder.findAccountByUserId(userId)).hasValue(account);
    }
}