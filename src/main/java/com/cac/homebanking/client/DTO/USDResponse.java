package com.cac.homebanking.client.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class USDResponse {
  @JsonProperty("moneda")
  String currency;
  @JsonProperty("nombre")
  String type;
  @JsonProperty("compra")
  Integer buyPrice;
  @JsonProperty("venta")
  Integer sellPrice;
  @JsonProperty("fechaActualizacion")
  String lastUpdate;
}