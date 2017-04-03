import java.math.BigInteger;

/**
 * Created by USER on 19.03.2017.
 */
public class Helper {

    public static BigInteger bigIntSqRootFloor(BigInteger x)
            throws IllegalArgumentException {
        if (x.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Negative argument.");
        }
        // square roots of 0 and 1 are trivial and
        // y == 0 will cause a divide-by-zero exception
        if (x.equals(BigInteger.ZERO) || x.equals(BigInteger.ONE)) {
            return x;
        } // end if
        BigInteger two = BigInteger.valueOf(2L);
        BigInteger y;
        // starting with y = x / 2 avoids magnitude issues with x squared
        for (y = x.divide(two);
             y.compareTo(x.divide(y)) > 0;
             y = ((x.divide(y)).add(y)).divide(two))
            ;
        return y;
    }

    static BigInteger[] decomposition(BigInteger n) {
        BigInteger[] res = new BigInteger[1000];
        for (int i = 1; i < res.length; i++) {
            res[i] = BigInteger.ZERO;
        }
        res[0] = BigInteger.ONE;
        int divisor = 2;
        int point = 0;
        while (n.compareTo(BigInteger.valueOf(2)) >= 0) {
            while (!n.mod(BigInteger.valueOf(divisor)).equals(BigInteger.ZERO) && bigIntSqRootFloor(n).compareTo(BigInteger.valueOf(divisor)) >= 0) {
                divisor++;
            }
            if (bigIntSqRootFloor(n).compareTo(BigInteger.valueOf(divisor)) >= 0) {
                res[point] = BigInteger.valueOf(divisor);
                point++;
                n = n.divide(BigInteger.valueOf(divisor));
                divisor = 2;
            } else {
                res[point] = n;
                break;
            }
        }
        return res;
    }

    static BigInteger gcd(BigInteger p, BigInteger q) {
        if (q.equals(BigInteger.ZERO)) {
            return p;
        }
        return gcd(q, p.mod(q));
    }

    static int jacobiSymbol(BigInteger D, BigInteger N) {
        int flag = 1;
        int res = 1;
        BigInteger[] decomposition = decomposition(D);
        if (D.equals(BigInteger.ONE)) {
            return 1;
        }
        for (int i = 0; i < decomposition.length && decomposition[i].compareTo(BigInteger.ZERO) > 0; i++) {
            if (decomposition[i].equals(BigInteger.ONE.negate())) {
                if (N.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                    res *= -1;
                }
            } else if (decomposition[i].equals(BigInteger.valueOf(2))) {
                if (N.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(3)) || N.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5))) {
                    res *= -1;
                }
            } else if (decomposition[i].compareTo(N) < 0) {
                if (N.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)) && decomposition[i].mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                    flag = -1;
                }
                res *= flag * jacobiSymbol(N.mod(decomposition[i]), decomposition[i]);
            }
        }
        return res;
    }

    static int factorial(int n) {
        int rez = 1;
        for (int i = 1; i <= n; i++) {
            rez *= i;
        }
        return rez;
    }

    static int first = 0;
    static int last = 1;
    public static BigInteger lucaNumbers[] = {BigInteger.ZERO, BigInteger.ONE};

    static BigInteger luca(BigInteger n, int Q, int P) {
        BigInteger temp;
        BigInteger res = BigInteger.ONE;
        if (n.compareTo(BigInteger.valueOf(last + 1)) > 0) {
            for (int i = last + 1; n.compareTo(BigInteger.valueOf(i)) > 0; i++) {
                temp = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
                lucaNumbers[0] = lucaNumbers[1];
                lucaNumbers[1] = temp;
                first++;
                last++;
            }
            res = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
            lucaNumbers[0] = lucaNumbers[1];
            lucaNumbers[1] = res;
            first++;
            last++;
        } else if (n.equals(BigInteger.valueOf(last + 1))) {
            res = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
            lucaNumbers[0] = lucaNumbers[1];
            lucaNumbers[1] = res;
            first++;
            last++;
        } else {
            lucaNumbers[0] = BigInteger.ZERO;
            lucaNumbers[1] = BigInteger.ONE;
            first = 0;
            last = 1;
            for (int i = 2; n.compareTo(BigInteger.valueOf(i)) >= 0; i++) {
                temp = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
                lucaNumbers[0] = lucaNumbers[1];
                lucaNumbers[1] = temp;
                first++;
                last++;
            }
            res = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
            lucaNumbers[0] = lucaNumbers[1];
            lucaNumbers[1] = res;
            first++;
            last++;
        }

        return res;
    }

    //static BigInteger[] lucaNumbers = new BigInteger[1000];

    static int cnk(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }
}
