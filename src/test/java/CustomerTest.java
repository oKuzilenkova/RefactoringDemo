
import com.scrumtrek.simplestore.Customer;
import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.PriceCodes;
import com.scrumtrek.simplestore.Rental;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerTest {

    private String dummyCustomerName;
    private Customer sutCustomer;
    private String dummyMovieTitle;
    private Rental stubRental;
    private Movie stubMovie;

    @Before
    public void setUp() {
        dummyCustomerName = "Name";
        sutCustomer = new Customer(dummyCustomerName);
        dummyMovieTitle = "MovieTitle";

        stubMovie = mock(Movie.class);
        when(stubMovie.getTitle()).thenReturn(dummyMovieTitle);

        stubRental = mock(Rental.class);
        when(stubRental.getMovie()).thenReturn(stubMovie);
    }

    @Test
    public void shouldCreateCustomerWithValidParams() {
        assertNotNull(sutCustomer);
        assertEquals(dummyCustomerName, sutCustomer.getName());
    }

    @Test
    public void shouldPrintCustomerNameWhenStatement() {
        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals("Rental record for " + dummyCustomerName, statement[0].trim());
    }


    @Test
    public void shouldReturnNewReleaseAmountWhenNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");
        assertEquals(dummyMovieTitle + "\t3.0", statement[1].trim());
        assertEquals("Amount owed is 3.0", statement[2].trim());
    }

    @Test
    public void shouldReturnChildrensAmountWhenSingleChildrens() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Childrens);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals(dummyMovieTitle + "\t1.5", statement[1].trim());
        assertEquals("Amount owed is 1.5", statement[2].trim());
    }


    @Test
    public void shouldReturnRegularAmountWhenSingleRegular() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Regular);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals(dummyMovieTitle + "\t2.0", statement[1].trim());
        assertEquals("Amount owed is 2.0", statement[2].trim());
    }


    @Test
    public void shouldFrequentEqualsRentalWhenOneDaysAndNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(1);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals("You earned 1 frequent renter points.", statement[3].trim());
    }

    @Test
    public void shouldFrequentEqualsDoubleRentalWhenMoreThanOneDaysAndNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(2);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals("You earned 2 frequent renter points.", statement[3].trim());
    }

    @Test
    public void shouldFrequentEqualsRentalWhenOneDaysAndNotNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Regular);
        when(stubRental.getDaysRented()).thenReturn(1);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals("You earned 1 frequent renter points.", statement[3].trim());
    }

    @Test
    public void shouldReturnNewReleaseAmountWhenTwoNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");
        assertEquals("Amount owed is 6.0", statement[3].trim());
    }

    @Test
    public void shouldReturnRegularAmountWhenThirdRegular() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Regular);
        when(stubRental.getDaysRented()).thenReturn(3);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals(dummyMovieTitle + "\t3.5", statement[1].trim());
        assertEquals("Amount owed is 3.5", statement[2].trim());
    }


    @Test
    public void shouldReturnChildrensAmountWhenFiveChildrens() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Childrens);
        when(stubRental.getDaysRented()).thenReturn(5);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals(dummyMovieTitle + "\t3.0", statement[1].trim());
        assertEquals("Amount owed is 3.0", statement[2].trim());
    }

    @Test
    public void shouldReturnTotalAmountWhenAllRentalTypes() {
        when(stubMovie.getPriceCode())
                .thenReturn(PriceCodes.Childrens)
                .thenReturn(PriceCodes.Regular)
                .thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);
        sutCustomer.addRental(stubRental);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertEquals("Amount owed is 7.5", statement[4].trim());
    }

}
