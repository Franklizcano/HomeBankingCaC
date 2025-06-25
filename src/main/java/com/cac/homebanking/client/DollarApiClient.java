package com.cac.homebanking.client;

import com.cac.homebanking.client.DTO.USDResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "dolarApiClient", url = "${dolar.api.url}")
public interface DollarApiClient {

  @GetMapping("/v1/dolares/oficial")
  USDResponse getOfficialUSD();
}