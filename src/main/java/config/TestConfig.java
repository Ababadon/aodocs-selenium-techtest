package config;

import org.aeonbits.owner.Config;

@Config.Sources({"file:./TestConfig.properties"})
public interface TestConfig extends Config {
    @DefaultValue("https://www.google.com")
    String google();

    @DefaultValue("aodocs")
    String company();

    @DefaultValue("www.aodocs.com")
    String companySite();

    @DefaultValue("Request a demo")
    String demo();

    @DefaultValue("florian")
    String firstName();

    @DefaultValue("Please complete this required field.")
    String expectedInputError();

    @DefaultValue("Email must be formatted correctly.")
    String expectedEmailError();

    @DefaultValue("Please select an option from the dropdown menu.")
    String expectedSelectError();

    @DefaultValue("Please complete all required fields.")
    String expectedGlobalError();
}
