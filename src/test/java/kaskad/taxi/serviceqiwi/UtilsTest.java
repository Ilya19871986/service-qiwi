package kaskad.taxi.serviceqiwi;

import org.junit.jupiter.api.Test;
import java.security.NoSuchAlgorithmException;

import static com.taxikaskad.Utils.MainUtils.Md5;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {

    @Test
    void Md5Test() throws NoSuchAlgorithmException {
        String hash = "35454B055CC325EA1AF2126E27707052";
        String s = "ILoveJava";
        assertEquals(Md5(s), hash);
    }

    @Test
    void QiwiResponseTest() {

    }

}
