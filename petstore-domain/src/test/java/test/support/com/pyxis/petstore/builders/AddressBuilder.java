package test.support.com.pyxis.petstore.builders;

import com.pyxis.petstore.domain.billing.Address;

public class AddressBuilder implements Builder<Address> {

    private String firstName;
    private String lastName;
    private String emailAddress;

    public static AddressBuilder anAddress() {
        return new AddressBuilder();
    }

    static AddressBuilder aValidAddress() {
        return anAddress().
                    withFirstName("John").
                    withLastName("Leclair").
                    withEmail("jleclair@gmail.com");
    }

    public Address build() {
        return new Address(firstName, lastName, emailAddress);
    }

    public AddressBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AddressBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AddressBuilder withEmail(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

	public AddressBuilder withName(String firstName, String lastName) {
		withFirstName(firstName);
		return withLastName(lastName);
	}
}