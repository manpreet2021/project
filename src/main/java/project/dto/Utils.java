package project.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final int ITERATIONS = 10000;
    private final int KEY_LENGTH = 256;
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCfghijklmnopqrstuvwxyzDEFGHIJKLMNOPQRSTUVWXYZabcde";
    private final int LARGE = 30;
    private final int MEDIUM = 10;
    private final int SMALL = 6;

    public String generateUserId(int length) {
        return generateRandomString(length);

    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }


    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }


    public String generateLargeLengthId() {
        return generateRandomString(LARGE);
    }

    public String generateMediumLengthId() {
        return generateRandomString(MEDIUM);
    }

    public String generateSmallLengthId() {
        return generateRandomString(SMALL);
    }
}
