import com.scrumtrek.simplestore.Customer;
import com.scrumtrek.simplestore.PriceCodes;
import com.scrumtrek.simplestore.Rental;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Parameterized.class)
public class CustomerParametrizedTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {PriceCodes.Childrens, 1, 1}, {PriceCodes.NewRelease, 100, 2}
        });
    }

    //input
    private PriceCodes priceCodes;
    private int daysRented;
    //output
    private int totalAmount;

    public CustomerParametrizedTest(PriceCodes priceCodes, int daysRented, int totalAmount) {
        this.priceCodes = priceCodes;
        this.daysRented = daysRented;
        this.totalAmount = totalAmount;
    }

    @Test
    public void test() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(priceCodes).withDaysRented(daysRented).build();

        Customer sutCustomer = new Customer("");
        sutCustomer.addRental(stubRental);
        String[] statement = sutCustomer.Statement().split("\n");
        assertThat(statement[3].trim()).contains(" " + totalAmount + " ");
    }
}
