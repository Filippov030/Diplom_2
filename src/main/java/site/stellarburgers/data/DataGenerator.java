package site.stellarburgers.data;

import com.github.javafaker.Faker;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static String getRandomName() {
        return faker.name().firstName();
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getValidPassword() {
        return faker.internet().password(6, 12, true, true, true);
    }

    public static String getInvalidPassword() {
        return faker.internet().password(1, 5, true, true, true);

    }
}
