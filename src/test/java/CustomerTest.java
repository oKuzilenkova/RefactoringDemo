
import com.scrumtrek.simplestore.Customer;
import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.PriceCodes;
import com.scrumtrek.simplestore.Rental;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTest {

    private String dummyCustomerName = "Name";
    private String dummyMovieTitle = "MovieTitle";
    private Customer sutCustomer;

    @Mock
    private Rental stubRental;
    @Mock
    private Movie stubMovie;

    @Before
    public void setUp() {
        sutCustomer = new Customer(dummyCustomerName);

        when(stubRental.getMovie()).thenReturn(stubMovie);
    }

    //region Simple Customer tests
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
//endregion

    //region Title in Statement tests
    @Test
    public void shouldPrintMovieTitleWhenStatementWithMovie() {
        Rental stubRental = new RentalStubBuilder().withMovieTitle(dummyMovieTitle).build();

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");
        assertThat(statement[1].trim()).startsWith(dummyMovieTitle);
    }
    //endregion

    //region 1 PriceCode 1 Day tests
    @Test
    public void shouldReturnNewReleaseAmountWhen1DayNewRelease() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(PriceCodes.NewRelease).withDaysRented(1).build();

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[1].trim()).endsWith("\t3.0");
        assertThat(statement[2].trim()).endsWith(" 3.0");
    }

    @Test
    public void shouldReturnChildrensAmountWhen1DayChildrens() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(PriceCodes.Childrens).withDaysRented(1).build();

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[1].trim()).endsWith("\t1.5");
        assertThat(statement[2].trim()).endsWith(" 1.5");
    }


    @Test
    public void shouldReturnRegularAmountWhen1DayRegular() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(PriceCodes.Regular).withDaysRented(1).build();

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[1].trim()).endsWith("\t2.0");
        assertThat(statement[2].trim()).endsWith(" 2.0");
    }

   // endregion

    //region Frequient tests
    @Test
    public void shouldFrequentEqualsRentalWhen1DayNewRelease() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(PriceCodes.NewRelease).withDaysRented(1).build();

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[3].trim()).contains(" 1 ");
    }

    @Test
    public void shouldFrequentEqualsDoubleRentalWhen2DaysNewRelease() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(PriceCodes.NewRelease).withDaysRented(2).build();

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[3].trim()).contains(" 2 ");
    }

    @Test
    public void shouldFrequentEqualsRentalWhen1DayNotNewRelease() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(PriceCodes.Regular).withDaysRented(1).build();

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[3].trim()).contains(" 1 ");
    }

    //endregion

    //region Boundary Condition tests
    @Test
    public void shouldReturnNewReleaseAmountWhen2NewRelease() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(PriceCodes.NewRelease).withDaysRented(1).build();

        sutCustomer.addRental(stubRental);
        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[3].trim()).endsWith(" 6.0");
    }

    @Test
    public void shouldReturnRegularAmountWhen3DaysRegular() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(PriceCodes.Regular).withDaysRented(3).build();

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[1].trim()).endsWith("\t3.5");
        assertThat(statement[2].trim()).endsWith("3.5");
    }

    @Test
    public void shouldReturnChildrensAmountWhen5DaysChildrens() {
        Rental stubRental = new RentalStubBuilder().withPriceCode(PriceCodes.Childrens).withDaysRented(5).build();

        sutCustomer.addRental(stubRental);

        String[] statement = sutCustomer.Statement().split("\n");

        assertThat(statement[1].trim()).endsWith("\t3.0");
        assertThat(statement[2].trim()).endsWith(" 3.0");
    }

    //endregion

    //region Bonus tests
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

    //endregion

    //region Exception tests
    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenInvalidRental() {
        stubRental = mock(Rental.class);
        sutCustomer.addRental(stubRental);
        sutCustomer.Statement();
    }
    //endregion

}
