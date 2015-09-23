
import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.PriceCodes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MovieTest {

    private String dummyTitle = "Title";
    private PriceCodes dummyPriceCode1 = PriceCodes.Childrens;
    private PriceCodes dummyPriceCode2 = PriceCodes.NewRelease;

    @Test
    public void shouldCreateMovieWithValidParams() {
        //region when
        Movie sut = new Movie(dummyTitle, dummyPriceCode1);
        //endregion

        //region then
        assertNotNull(sut);
        assertEquals(dummyPriceCode1, sut.getPriceCode());
        assertEquals(dummyTitle, sut.getTitle());
        //endregion
    }

    @Test
    public void shouldSetNewPriceCode() {
        Movie sut = new Movie(dummyTitle, dummyPriceCode1);
        assertEquals(dummyPriceCode1, sut.getPriceCode());

        sut.setPriceCode(dummyPriceCode2);
        assertEquals(dummyPriceCode2, sut.getPriceCode());
    }
}

