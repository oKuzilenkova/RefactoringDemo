import com.scrumtrek.simplestore.Movie;
import com.scrumtrek.simplestore.PriceCodes;
import com.scrumtrek.simplestore.Rental;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RentalStubBuilder {
    private int days = 1;
    private String movieTitle = "MovieTitle";
    private PriceCodes priceCode = PriceCodes.Regular;

    public RentalStubBuilder withDaysRented(int i) {
        this.days = i;
        return this;
    }

    public RentalStubBuilder withPriceCode(PriceCodes code) {
        this.priceCode = code;
        return this;
    }

    public RentalStubBuilder withMovieTitle(String title) {
        this.movieTitle = title;
        return this;
    }

    public Rental build(){
        Rental rental = mock(Rental.class);
        Movie movie = mock(Movie.class);

        when(rental.getMovie()).thenReturn(movie);

        when(rental.getDaysRented()).thenReturn(days);
        when(rental.getMovie().getTitle()).thenReturn(movieTitle);
        when(rental.getMovie().getPriceCode()).thenReturn(priceCode);
        return rental;
    }
}
