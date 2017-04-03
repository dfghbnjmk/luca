import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.*;

/**
 * Created by Azat Abdulvaliev on 03/04/2017.
 */
public class HelperTest {

    @Test
    public void testJacobi() {
        assertEquals(Helper.jacobiSymbol(BigInteger.valueOf(12), BigInteger.valueOf(21)), 0);
        assertEquals(Helper.jacobiSymbol(BigInteger.valueOf(12), BigInteger.valueOf(63)), 0);
        assertEquals(Helper.jacobiSymbol(BigInteger.valueOf(12), BigInteger.valueOf(5597)), -1);
        assertEquals(Helper.jacobiSymbol(BigInteger.valueOf(12), BigInteger.valueOf(97)), 1);
        assertEquals(Helper.jacobiSymbol(BigInteger.valueOf(12), BigInteger.valueOf(1543)), -1);
    }

    @Test
    public void testLuca() throws Exception {
        assertEquals(Helper.luca(BigInteger.valueOf(0), 6, 6), BigInteger.valueOf(0));
        assertEquals(Helper.luca(BigInteger.valueOf(1), 6, 6), BigInteger.valueOf(1));
        assertEquals(Helper.luca(BigInteger.valueOf(2), 6, 6), BigInteger.valueOf(6));
        assertEquals(Helper.luca(BigInteger.valueOf(3), 6, 6), BigInteger.valueOf(30));
        assertEquals(Helper.luca(BigInteger.valueOf(4), 6, 6), BigInteger.valueOf(144));
        assertEquals(Helper.luca(BigInteger.valueOf(5), 6, 6), BigInteger.valueOf(684));
        assertEquals(Helper.luca(BigInteger.valueOf(6), 6, 6), BigInteger.valueOf(3240));
        assertEquals(Helper.luca(BigInteger.valueOf(7), 6, 6), BigInteger.valueOf(15336));
        assertEquals(Helper.luca(BigInteger.valueOf(8), 6, 6), BigInteger.valueOf(72576));
        assertEquals(Helper.luca(BigInteger.valueOf(9), 6, 6), BigInteger.valueOf(343440));
        assertEquals(Helper.luca(BigInteger.valueOf(10), 6, 6), BigInteger.valueOf(1625184));
    }

    @Test
    public void testGenLuca() throws Exception {
        Helper.LucaGen gen = new Helper.LucaGen(6, 6);

        assertEquals(gen.forIndex(BigInteger.valueOf(0)), BigInteger.valueOf(0));
        assertEquals(gen.forIndex(BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(gen.forIndex(BigInteger.valueOf(2)), BigInteger.valueOf(6));
        assertEquals(gen.forIndex(BigInteger.valueOf(3)), BigInteger.valueOf(30));
        assertEquals(gen.forIndex(BigInteger.valueOf(4)), BigInteger.valueOf(144));
        assertEquals(gen.forIndex(BigInteger.valueOf(5)), BigInteger.valueOf(684));
        assertEquals(gen.forIndex(BigInteger.valueOf(6)), BigInteger.valueOf(3240));
        assertEquals(gen.forIndex(BigInteger.valueOf(7)), BigInteger.valueOf(15336));
        assertEquals(gen.forIndex(BigInteger.valueOf(8)), BigInteger.valueOf(72576));
        assertEquals(gen.forIndex(BigInteger.valueOf(9)), BigInteger.valueOf(343440));
        assertEquals(gen.forIndex(BigInteger.valueOf(10)), BigInteger.valueOf(1625184));
    }
}