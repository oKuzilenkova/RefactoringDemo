import com.scrumtrek.simplestore.Customer;
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


}
