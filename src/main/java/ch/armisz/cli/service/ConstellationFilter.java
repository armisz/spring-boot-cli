package ch.armisz.cli.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ConstellationFilter {

  public static final ConstellationFilter NONE = ConstellationFilter.builder().build();

  private String product;
  private String component;

}
