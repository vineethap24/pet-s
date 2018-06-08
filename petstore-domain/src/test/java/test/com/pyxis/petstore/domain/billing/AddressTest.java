package test.com.pyxis.petstore.domain.billing;

import com.pyxis.petstore.domain.billing.Address;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static test.support.com.pyxis.petstore.builders.AddressBuilder.anAddress;

public class AddressTest {

    @Test public void
    addressesMatchWhenAllPropertiesMatch() {
        Address address = anAddress().
                withFirstName("John").
                withLastName("Doe").
                withEmail("jdoe@gmail.com").build();
        Address shouldMatch = anAddress().
                withFirstName("John").
                withLastName("Doe").
                withEmail("jdoe@gmail.com").build();
        Address shouldNotMatch = anAddress().
                withFirstName("Jane").
                withLastName("Doe").
                withEmail("jdoe@gmail.com").build();
        assertThat("address", address, equalTo(shouldMatch));
        assertThat("hash code", address.hashCode(), equalTo(shouldMatch.hashCode()));
        assertThat("address ", address, not(equalTo(shouldNotMatch)));
    }
}
