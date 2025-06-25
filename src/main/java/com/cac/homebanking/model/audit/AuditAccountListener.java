package com.cac.homebanking.model.audit;

import com.cac.homebanking.model.Account;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditAccountListener {
  private Account oldAccount;

  @PostLoad
  public void onPostLoad(Account account) {
    this.oldAccount = new Account(account.getId(), account.getNumber(),
        account.getBalance(), account.getUserId(), account.getCurrency());
  }

  @PostUpdate
  public void onPostUpdate(Account account) {
    log.info("Old account: " + this.oldAccount);
    log.info("New account: " + account);
  }
}
