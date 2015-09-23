import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.Rental;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RentalTest {

    private Movie dummyMovie;
    private int dummyDaysRented = 1;

    @Before
    public void setUp() {
        dummyMovie = mock(Movie.class);
    }

    @Test
    public void shouldCreateRentalWithValidParams() {
        Rental sut = new Rental(dummyMovie, dummyDaysRented);
        //endregion

        //region then
        assertNotNull(sut);
        assertSame(dummyMovie, sut.getMovie());
        assertEquals(dummyDaysRented, sut.getDaysRented());
        //endregion
    }
}
