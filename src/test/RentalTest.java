import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.PriceCodes;
import com.scrumtrek.simplestore.Rental;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class RentalTest {

    @Test
    public void shouldCreateRentalWithValidParams() {
        //region given
        String dummyMovieTitle = "Title";
        PriceCodes dummyMoviePriceCode = PriceCodes.Childrens;
        Movie dummyMovie = new Movie(dummyMovieTitle, dummyMoviePriceCode);
        int dummyDaysRented = 1;
        //endregion

        //region when
        Rental sut = new Rental(dummyMovie, dummyDaysRented);
        //endregion

        //region then
        assertNotNull(sut);
        assertSame(dummyMovie, sut.getMovie());
        assertEquals(dummyDaysRented, sut.getDaysRented());
        //endregion
    }
}
