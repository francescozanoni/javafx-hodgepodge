package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract IBAN codes from text.
 */
public class IbanExtractor {

    private static final String IBAN_PATTERN = "[A-Z]{2} ?[0-9]{2} ?[A-Z0-9 ]{4,}";
    private static final int IBAN_MAX_LENGTH = 34;

    /**
     * Extract IBAN codes from a single text source.
     *
     * @param text text from which IBAN codes are to be extracted
     * @return unique IBAN codes
     */
    public static String[] run(String text) {

        // @see https://www.vogella.com/tutorials/JavaRegularExpressions/article.html
        Pattern pattern = Pattern.compile(IBAN_PATTERN);

        ArrayList<String> ibanCodes = new ArrayList<>();

        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String iban = getCleanIban(matcher.group());
            if (!ibanCodes.contains(iban)) {
                ibanCodes.add(iban);
            }
        }

        ibanCodes.sort(String::compareTo);

        // Below statement cannot be used, as it triggers this exception:
        //   Exception in thread "main" java.lang.ClassCastException: class java.lang.Object cannot be cast to class java.lang.String
        // return (String[]) ibanCodes.toArray();

        String[] ibanCodesArray = new String[ibanCodes.size()];
        for (int i = 0; i < ibanCodes.size(); i++) {
            ibanCodesArray[i] = ibanCodes.get(i);
        }

        return ibanCodesArray;

    }

    /**
     * Extract IBAN codes from several text sources.
     *
     * @param texts texts from which IBAN codes are to be extracted
     * @return unique IBAN codes
     */
    public static String[] run(String[] texts) {

        ArrayList<String> ibanCodes = new ArrayList<>();

        for (String text : texts) {
            ibanCodes.addAll(Arrays.asList(run(text)));
        }

        ibanCodes.sort(String::compareTo);

        // Below statement cannot be used, as it triggers this exception:
        //   Exception in thread "main" java.lang.ClassCastException: class java.lang.Object cannot be cast to class java.lang.String
        // return (String[]) ibanCodes.toArray();

        String[] ibanCodesArray = new String[ibanCodes.size()];
        for (int i = 0; i < ibanCodes.size(); i++) {
            ibanCodesArray[i] = ibanCodes.get(i);
        }

        return ibanCodesArray;

    }

    /**
     * Clean an IBAN string to standard form.
     *
     * @param iban raw IBAN code e.g. "DK50 0040 0440 1162 43 "
     * @return standardized IBAN code e.g. "DK5000400440116243"
     * TODO return non-empty strings only if IBAN matches its country pattern
     */
    private static String getCleanIban(String iban) {

        iban = iban.replaceAll("[^0-9A-Z]+", "");

        if (iban.length() > IBAN_MAX_LENGTH) {
            iban = iban.substring(0, IBAN_MAX_LENGTH);
        }

        return iban;
    }

}