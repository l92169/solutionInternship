package org.example;

import junit.framework.TestCase;
import org.junit.Assert;

public class CurrencyRatesTest extends TestCase {

    public void testParseDate() {
        String actual1 = CurrencyRates.parseDate("2022-02-02");
        String expected = "02/02/2022";
        Assert.assertEquals(expected, actual1);
    }

    public void testIsValidCurrencyCode() {
        Boolean actual1 = CurrencyRates.isValidCurrencyCode("USD");
        Assert.assertEquals(true, actual1);

        Boolean actual2 = CurrencyRates.isValidCurrencyCode("Usd");
        Assert.assertEquals(false, actual2);

        Boolean actual3 = CurrencyRates.isValidCurrencyCode("USDD");
        Assert.assertEquals(false, actual3);
    }

    public void testIsValidDate() {
        Boolean actual1 = CurrencyRates.isValidDate("2020-02-02");
        Assert.assertEquals(true, actual1);

        Boolean actual2 = CurrencyRates.isValidDate("2020-2-02");
        Assert.assertEquals(false, actual2);

        Boolean actual3 = CurrencyRates.isValidDate("02-02-2022");
        Assert.assertEquals(false, actual3);
    }
}