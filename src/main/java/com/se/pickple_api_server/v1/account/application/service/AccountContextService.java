package com.se.pickple_api_server.v1.account.application.service;

import com.se.pickple_api_server.v1.account.domain.entity.Account;
import com.se.pickple_api_server.v1.account.application.error.AccountErrorCode;
import com.se.pickple_api_server.v1.account.infra.repository.AccountJpaRepository;
import com.se.pickple_api_server.v1.common.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountContextService implements UserDetailsService {

  private final AccountJpaRepository accountJpaRepository;

  @Value("${spring.security.anonymous.id}")
  private String ANONYMOUS_ID;

  @Value("${spring.security.anonymous.pw}")
  private String ANONYMOUS_PW;

  @Override
  public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
    Account account = accountJpaRepository.findById(Long.parseLong(accountId))
        .orElseThrow(() -> new BusinessException(AccountErrorCode.NO_SUCH_ACCOUNT));
    List<GrantedAuthority> grantedAuthorities = Arrays.asList(new SimpleGrantedAuthority(account.getAccountType().toString()));
    return new User(String.valueOf(account.getAccountId()), "", grantedAuthorities);
  }

  public UserDetails loadDefaultGroupAuthorities() throws UsernameNotFoundException {
    List<GrantedAuthority> grantedAuthorities = Arrays.asList(new SimpleGrantedAuthority("UNKNOWN"));
    return new User(ANONYMOUS_ID, ANONYMOUS_PW, grantedAuthorities);
  }

  public Account getContextAccount(){
    return accountJpaRepository.findById(getCurrentAccountId())
            .orElseThrow(() -> new BusinessException(AccountErrorCode.NO_SUCH_ACCOUNT));
  }

  private Long getCurrentAccountId() {
    return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
  }


  public boolean hasAuthority(String auth) {
    Set<String> authorities = AuthorityUtils
        .authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    return authorities.contains(auth);
  }


  public boolean isOwner(Account account) {
    if(SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication().getName() == null)
      throw new AccessDeniedException("비정상적인 접근");

    String id = SecurityContextHolder.getContext().getAuthentication().getName();

    if(id.equals(ANONYMOUS_ID))
      return false;
    if(!id.equals(String.valueOf(account.getAccountId())))
      return false;
    return true;
  }

  public boolean isOwner(Long accountId) {
    if(accountId == null)
      return false;
    Account account = accountJpaRepository.findById(accountId).orElseThrow(() -> new BusinessException(AccountErrorCode.NO_SUCH_ACCOUNT));
    return isOwner(account);
  }
}
