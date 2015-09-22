
import com.scrumtrek.simplestore.Customer;
import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.PriceCodes;
import com.scrumtrek.simplestore.Rental;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerTest {

    private String dummyName;
    private Customer sutCustomer;
    private String dummyMovieTitle;

    @Before
    public void setUp() {
        dummyName = "Name";
        sutCustomer = new Customer(dummyName);
        dummyMovieTitle = "MovieTitle";
    }

    @Test
    public void shouldCreateCustomerWithValidParams() {
        assertNotNull(sutCustomer);
        assertEquals(dummyName, sutCustomer.getName());
    }

    @Test
    public void shouldAddRentalAndReturnStatementWhenNewReleaseRental() {
        //region given
        Movie dummyMovie = new Movie(dummyMovieTitle, PriceCodes.NewRelease);
        int dummyDaysRented = 1;
        Rental dummyRental = new Rental(dummyMovie, dummyDaysRented);
        //endregion

        //region when
        sutCustomer.addRental(dummyRental);
        //endregion

        //region then
        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals("Rental record for " + dummyName, statement[0].trim());
        assertEquals(dummyMovieTitle + "\t3.0", statement[1].trim());
        assertEquals("Amount owed is 3.0", statement[2].trim());
        assertEquals("You earned 1 frequent renter points.", statement[3].trim());
        //endregion
    }

    @Test
    public void shouldAddRentalAndReturnStatementWhenChildrenRental() {
        Movie dummyMovie = new Movie(dummyMovieTitle, PriceCodes.Childrens);
        Rental dummyRental = new Rental(dummyMovie, 1);
        sutCustomer.addRental(dummyRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals("Rental record for " + dummyName, statement[0].trim());
        assertEquals(dummyMovieTitle + "\t1.5", statement[1].trim());
        assertEquals("Amount owed is 1.5", statement[2].trim());
        assertEquals("You earned 1 frequent renter points.", statement[3].trim());
    }


    @Test
    public void shouldAddRentalAndReturnStatementWhenRegularRental() {
        Movie dummyMovie = new Movie(dummyMovieTitle, PriceCodes.Regular);
        Rental dummyRental = new Rental(dummyMovie, 1);
        sutCustomer.addRental(dummyRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals("Rental record for " + dummyName, statement[0].trim());
        assertEquals(dummyMovieTitle + "\t2.0", statement[1].trim());
        assertEquals("Amount owed is 2.0", statement[2].trim());
        assertEquals("You earned 1 frequent renter points.", statement[3].trim());
    }
}
