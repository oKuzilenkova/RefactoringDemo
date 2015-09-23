
import com.scrumtrek.simplestore.Customer;
import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.PriceCodes;
import com.scrumtrek.simplestore.Rental;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
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
        String statement = sutCustomer.Statement();

        assertThat(statement).contains(dummyCustomerName);
    }


    @Test
    public void shouldReturnNewReleaseAmountWhenSingleNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");
        assertThat( statement[1].trim()).startsWith(dummyMovieTitle);
        assertThat(statement[1].trim()).endsWith("\t3.0");
        assertThat(statement[2].trim()).endsWith(" 3.0");
    }

    @Test
    public void shouldReturnChildrensAmountWhenSingleChildrens() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Childrens);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat( statement[1].trim()).startsWith(dummyMovieTitle);
        assertThat(statement[1].trim()).endsWith("\t1.5");
        assertThat(statement[2].trim()).endsWith(" 1.5");
    }


    @Test
    public void shouldReturnRegularAmountWhenSingleRegular() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Regular);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat( statement[1].trim()).startsWith(dummyMovieTitle);
        assertThat(statement[1].trim()).endsWith("\t2.0");
        assertThat(statement[2].trim()).endsWith(" 2.0");
    }


    @Test
    public void shouldFrequentEqualsRentalWhenOneDaysAndNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(1);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[3].trim()).contains(" 1 ");
    }

    @Test
    public void shouldFrequentEqualsDoubleRentalWhenMoreThanOneDaysAndNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(2);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[3].trim()).contains(" 2 ");
    }

    @Test
    public void shouldFrequentEqualsRentalWhenOneDaysAndNotNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Regular);
        when(stubRental.getDaysRented()).thenReturn(1);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[3].trim()).contains(" 1 ");
    }

    @Test
    public void shouldReturnNewReleaseAmountWhenTwoNewRelease() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[3].trim()).endsWith(" 6.0");
    }

    @Test
    public void shouldReturnRegularAmountWhenThirdRegular() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Regular);
        when(stubRental.getDaysRented()).thenReturn(3);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat( statement[1].trim()).startsWith(dummyMovieTitle);
        assertThat(statement[1].trim()).endsWith("\t3.5");
        assertThat(statement[2].trim()).endsWith("3.5");
    }


    @Test
    public void shouldReturnChildrensAmountWhenFiveChildrens() {
        when(stubMovie.getPriceCode()).thenReturn(PriceCodes.Childrens);
        when(stubRental.getDaysRented()).thenReturn(5);

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat( statement[1].trim()).startsWith(dummyMovieTitle);
        assertThat(statement[1].trim()).endsWith("\t3.0");
        assertThat(statement[2].trim()).endsWith(" 3.0");
    }

    @Test
    public void shouldReturnTotalAmountWithoutBonusWhenAllRentalTypes() {
        when(stubMovie.getPriceCode())
                .thenReturn(PriceCodes.Childrens)
                .thenReturn(PriceCodes.Regular)
                .thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(1);

        sutCustomer.addRental(stubRental);
        sutCustomer.addRental(stubRental);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[4].trim()).endsWith(" 7.5");
    }

    @Test
    public void shouldReturnTotalAmountWithBonusWhenAllRentalTypes() {
        when(stubMovie.getPriceCode())
                .thenReturn(PriceCodes.Childrens)
                .thenReturn(PriceCodes.Regular)
                .thenReturn(PriceCodes.NewRelease);
        when(stubRental.getDaysRented()).thenReturn(2);

        sutCustomer.addRental(stubRental);
        sutCustomer.addRental(stubRental);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[5].trim()).contains(" 5 ");
        assertThat(statement[4].trim()).endsWith(" 13.5");
    }


    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenInvalidRental() {
        stubRental = mock(Rental.class);
        sutCustomer.addRental(stubRental);
        sutCustomer.Statement();
    }

}
