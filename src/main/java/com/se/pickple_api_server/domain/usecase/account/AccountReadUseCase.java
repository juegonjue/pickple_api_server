package com.se.pickple_api_server.domain.usecase.account;

import com.se.pickple_api_server.domain.entity.account.Account;
import com.se.pickple_api_server.domain.usecase.UseCase;
import com.se.pickple_api_server.repository.account.AccountJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class AccountReadUseCase {

    private final AccountJpaRepository accountJpaRepository;

    public Account read(Long accountId){
        Optional<Account> member = accountJpaRepository.findById(accountId);
        return member.get();
    }

}