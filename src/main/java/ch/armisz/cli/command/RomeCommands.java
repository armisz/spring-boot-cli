package ch.armisz.cli.command;

import ch.armisz.cli.service.RomeFilter;
import ch.armisz.cli.service.RomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class RomeCommands {

    public static final String PRODUCT_FILTER = "--product-filter";

    public static final String COMPONENT_FILTER = "--component-filter";

    @Autowired
    private RomeService romeService;

    @ShellMethod("Fetch components from repository")
    public void fetch() {
        romeService.fetch();
    }

    Availability fetchAvailability() {
        return romeService.isFetchReady()
                ? Availability.available()
                : Availability.unavailable("repositories must be configured first.");
    }

    @ShellMethod("Manage parameters")
    public void parameters(
            @ShellOption(help = "The product to filter for",
                    defaultValue = ShellOption.NULL,
                    value = PRODUCT_FILTER)
                    String productFilter,
            @ShellOption(help = "The component to filter for",
                    defaultValue = ShellOption.NULL,
                    value = COMPONENT_FILTER)
                    String componentFilter) {

        romeService.parameters(RomeFilter.builder()
                .component(componentFilter)
                .product(productFilter)
                .build());
    }

    Availability parametersAvailability() {
        return romeService.isParametersReady()
                ? Availability.available()
                : Availability.unavailable("command [fetch] must be executed first.");
    }

    @ShellMethod("Applies configuration")
    public void configure(
            @ShellOption(help = "The product to filter for",
                    defaultValue = ShellOption.NULL,
                    value = PRODUCT_FILTER)
                    String productFilter,
            @ShellOption(help = "The component to filter for",
                    defaultValue = ShellOption.NULL,
                    value = COMPONENT_FILTER)
                    String componentFilter) {

        romeService.configure(RomeFilter.builder()
                .component(componentFilter)
                .product(productFilter)
                .build());
    }

    @ShellMethod("Lists all kubectl commands necessary to deploy the new configuration")
    public void deploy(
            @ShellOption(help = "The product to filter for",
                    defaultValue = ShellOption.NULL,
                    value = PRODUCT_FILTER)
                    String productFilter,
            @ShellOption(help = "The component to filter for",
                    defaultValue = ShellOption.NULL,
                    value = COMPONENT_FILTER)
                    String componentFilter,
            @ShellOption(help = "Ignores fetch step",
                    defaultValue = "false")
                    boolean ignoreFetch,
            @ShellOption(help = "Ignores configure step",
                    defaultValue = "false")
                    boolean ignoreConfigure) {

        RomeFilter filter = RomeFilter.builder()
                .component(componentFilter)
                .product(productFilter)
                .build();
        romeService.deploy(filter, ignoreFetch, ignoreConfigure);
    }

    @ShellMethod("List images")
    public void images(
            @ShellOption(
                    help = "The product to filter for",
                    defaultValue = ShellOption.NULL,
                    value = PRODUCT_FILTER)
                    String productFilter,
            @ShellOption(
                    help = "The component to filter for",
                    defaultValue = ShellOption.NULL,
                    value = COMPONENT_FILTER)
                    String componentFilter) {

        romeService.images(RomeFilter.builder()
                .component(componentFilter)
                .product(productFilter)
                .build());
    }

    Availability imagesAvailability() {
        return romeService.isImagesReady()
                ? Availability.available()
                : Availability.unavailable("command [configure] must be executed first.");
    }

}