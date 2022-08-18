package utils;

import java.util.Random;

public class RandomUtil {

    private static final Random RANDOM = new Random();
    private static final int NUMBER_OF_LETTERS_ALPHABET = 26;
    private static final int MAX_LENGTH = 20;

    public static String getRandomString() {
        StringBuilder randomText = new StringBuilder();
        for (int i = 0; i < RANDOM.nextInt(MAX_LENGTH) + 1; i++) {
            if (RANDOM.nextBoolean()) {
                randomText.append((char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'a'));
            } else {
                randomText.append((char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'A'));
            }
        }
        return randomText.toString();
    }

    public static Integer getRandomRepeatingNumber(int count) {
        int num = (int) (Math.random() * 9);
        String s = "";
        for (int i = 0; i < count; i++) {
            s += String.valueOf(num);
        }
        return Integer.valueOf(s);
    }
}