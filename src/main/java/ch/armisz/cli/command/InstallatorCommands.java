package ch.armisz.cli.command;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class InstallatorCommands {

  public static final String PRODUCT_FILTER = "--product-filter";

  public static final String COMPONENT_FILTER = "--component-filter";

  @ShellMethod("Fetch components from repository")
  public void fetch() {
  }

  Availability fetchAvailability() {
    return Boolean.FALSE
        ? Availability.unavailable("repositories must be configured first.")
        : Availability.available();
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
  }

  Availability parametersAvailability() {
    return Boolean.TRUE
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
  }

  Availability imagesAvailability() {
    return Boolean.TRUE
        ? Availability.available()
        : Availability.unavailable("command [configure] must be executed first.");
  }

}