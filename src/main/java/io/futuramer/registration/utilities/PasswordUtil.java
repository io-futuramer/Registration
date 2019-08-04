package io.futuramer.registration.utilities;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * This class provided utility methods to create and verify a hash of a password.
 *
 * This implementation can be used to create a company internal
 *  shared java utility library that embed your compiled version of the Argon2 library
 * to ensure that no external untrusted binary as used in your information system.
 *
 * As hash will be used for password type of information then the variant
 * named "Argon2i" of Argon2 will be used.
 *
 * The hash creation method return a hash with all information in order to
 * allow the application that need to verify the hash to be in a full stateless mode.
 */
public final class PasswordUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtil.class);

    private static final Properties options = loadParameters();

    /**
     * Compute a hash of a password.
     * Password provided is wiped from the memory at the end of this method
     *
     * @param password Password to hash
     * @param charset  Charset of the password
     * @return the hash in format "$argon2i$v=19$m=128000,t=3,
     *       p=4$sfSe5MewORVlg8cDtxOTbg$uqWx4mZvLI092oJ8ZwAjAWU0rrBSDQkOezxAuvrE5dM"
     */
    public static String hash(@NotNull char[] password, @NotNull Charset charset) {
        String hash = null;
        Argon2 argon2Hasher = null;
        try {
            // Create instance
            argon2Hasher = createInstance();

            int iterationsCount = Integer.parseInt(options.getProperty("ITERATIONS"));
            int memoryAmountToUse = Integer.parseInt(options.getProperty("MEMORY"));
            int threadToUse = Integer.parseInt(options.getProperty("PARALLELISM"));
            //Compute and return the hash
            hash = argon2Hasher.hash(iterationsCount, memoryAmountToUse, threadToUse,
                    password, charset);
        }
        catch (NumberFormatException e) {
            LOGGER.error("Error: unable to parse argon2.properties", e);
        }
        finally {
            //Clean the password from the memory
            if (argon2Hasher != null) {
                argon2Hasher.wipeArray(password);
            }
        }
        return hash;
    }

    /**
     * Verifies a password against a hash
     * Password provided is wiped from the memory at the end of this method
     *
     * @param hash     Hash to verify
     * @param password Password to which hash must be verified against
     * @param charset  Charset of the password
     * @return True if the password matches the hash, false otherwise.
     */
    public static boolean verify(@NotNull String hash, @NotNull char[] password,
                                 @NotNull Charset charset) {
        Argon2 argon2Hasher = null;
        boolean isMatching;
        try {
            // Create instance
            argon2Hasher = createInstance();
            //Apply the verification (hash computation options are included
            //in the hash itself)
            isMatching = argon2Hasher.verify(hash, password, charset);
        }
        finally {
            //Clean the password from the memory
            if (argon2Hasher != null) {
                argon2Hasher.wipeArray(password);
            }
        }
        return isMatching;
    }

    /**
     * Create and configure an Argon2 instance
     *
     * @return The Argon2 instance
     */
    private static Argon2 createInstance() {
        // Create and return the instance
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
    }

    /**
     * Load Argon2 options to use for hashing.
     *
     * @return Properties with the options
     */
    private static Properties loadParameters() {
        Properties options = new Properties();
        try (InputStream input = PasswordUtil.class.getClassLoader().getResourceAsStream("argon2.properties")) {
            options.load(input);
        }
        catch (IOException e) {
            LOGGER.error("Error! Unable to read argon2.properties", e);
        }
        return options;
    }

}
