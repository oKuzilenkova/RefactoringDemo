package java;

import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.PriceCodes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MovieTest {

    @Test
    public void shouldCreateMovieWithValidParams() {
        //region given
        String dummyTitle = "Title";
        PriceCodes dummyPriceCode = PriceCodes.Childrens;
        //endregion

        //region when
        Movie sut = new Movie(dummyTitle, dummyPriceCode);
        //endregion

        //region then
        assertNotNull(sut);
        assertEquals(dummyPriceCode, sut.getPriceCode());
        assertEquals(dummyTitle, sut.getTitle());
        //endregion
    }
}

