package ch.armisz.cli.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RomeFilter {

  public static final RomeFilter NONE = RomeFilter.builder().build();

  private String product;
  private String component;

}