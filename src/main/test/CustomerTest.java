import com.scrumtrek.simplestore.Customer;
import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.PriceCodes;
import com.scrumtrek.simplestore.Rental;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerTest {

    @Test
    public void shouldCreateCustomerWithValidParams() {
        //region given
        String dummyName = "Name";
        //endregion

        //region when
        Customer sut = new Customer(dummyName);
        //endregion

        //region then
        assertNotNull(sut);
        assertEquals(dummyName, sut.getName());
        //endregion
    }

    @Test
    public void shouldAddRentalWithValidParams() {
        //region given
        String dummyMovieTitle = "Title";
        PriceCodes dummyMoviePriceCode = PriceCodes.Childrens;
        Movie dummyMovie = new Movie(dummyMovieTitle, dummyMoviePriceCode);
        int dummyDaysRented = 1;
        Rental dummyRental = new Rental(dummyMovie,dummyDaysRented);

        String dummyName = "Name";
        Customer sut = new Customer(dummyName);
        //endregion

        //region when
        sut.addRental(dummyRental);
        //endregion

        //region then
        assertNotNull(sut);
        //endregion
    }
}
