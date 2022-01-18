package config;

import org.aeonbits.owner.Config;

public interface TestConfig extends Config {
    @Key("google")
    @DefaultValue("https://www.google.com")
    String google();

    @Key("company")
    @DefaultValue("aodocs")
    String company();

    @Key("companySite")
    @DefaultValue("www.aodocs.com")
    String companySite();

    @Key("demo")
    @DefaultValue("Request a demo")
    String demo();

    @Key("firstName")
    @DefaultValue("florian")
    String firstName();

    @Key("expectedInputError")
    @DefaultValue("Please complete this required field.")
    String expectedInputError();

    @Key("expectedEmailError")
    @DefaultValue("Email must be formatted correctly.")
    String expectedEmailError();

    @Key("expectedSelectError")
    @DefaultValue("Please select an option from the dropdown menu.")
    String expectedSelectError();

    @Key("expectedGlobalError")
    @DefaultValue("Please complete all required fields.")
    String expectedGlobalError();
}
