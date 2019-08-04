package io.futuramer.registration.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class containing loaded dictionary of commonly used passwords
 */
public class DictionaryUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryUtil.class);

    private static final Set<String> dictionary = loadDictionary();

    private static Set<String> loadDictionary() {
        Set<String> dictionary = new HashSet<>();
        try (
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(DictionaryUtil.class.getClassLoader().getResourceAsStream("dictionary.txt"))))) {
            dictionary = buffer.lines().collect(Collectors.toSet());
        }
        catch (IOException e) {
            LOGGER.error("Error: unable to load passwords dictionary", e);
        }
        return dictionary;
    }

    public static boolean contains(String password) {
        return dictionary.contains(password);
    }

}
