package ch.armisz.cli.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Filter {

    public static final Filter NONE = Filter.builder().build();

    private String product;
    private String component;

    @Override
    public String toString() {
        return String.format("(product=%s component=%s)", product, component);
    }
}
