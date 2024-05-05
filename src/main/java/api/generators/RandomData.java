package api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    private static final int LENGTH = 10;

    public static String getRandomString() {
        return "test_" + RandomStringUtils.randomAlphabetic(LENGTH);
    }
}
