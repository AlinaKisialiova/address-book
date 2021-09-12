package ie.akisialiova.addressbook;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AddressBookApplicationTests {

    @Test
    public void contextLoads() {
    }
}
