//Ivan Sun (118267652) Recitation (08)

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for the Playfair Cipher program.
 *
 * CSE214-01 and CSE214-02 - Data Structures
 * Fall 2026
 *
 * Last Updated: 10/09/2026
 *
 * This class tests key table generation, bigram and phrase operations,
 * encryption, decryption, and key changes.
 *
 * This file contains five required test cases:
 *
 * 1. testKeyTableGeneration()
 *    - Tests Playfair key table generation. IMPLEMENTED as an example.
 *
 * 2. testBigramAndPhraseOperations()
 *    - Tests Bigram creation and Phrase queue operations. IMPLEMENTED as an example.
 *
 * 3. testEncryption()
 *    - Tests Playfair encryption. TO BE COMPLETED.
 *
 * 4. testDecryption()
 *    - Tests Playfair decryption. TO BE COMPLETED.
 *
 * 5. testKeyChangesAndDefaultKey()
 *    - Tests different keys and the default key. TO BE COMPLETED.
 */
public class JUnitTestPlayfairCipher {

    private static final int SIZE = 5;

    /**
     * Generates a two-dimensional character array from the given strings.
     *
     * Each string represents one row of the character array. The method
     * verifies that the number of strings and the length of each string
     * are equal to SIZE.
     *
     * The three dots (...) in String... rows represent Java's varargs
     * syntax, which allows the method to accept a variable number of
     * String arguments. For example, generateCharArray("ABCDE", "FGHIK",
     * "LMNOP", "QRSTU", "VWXYZ") passes five String arguments. Inside
     * the method, rows is treated as a String array.
     *
     * @param rows 
     *        the strings used to create the rows of the character array
     * @return 
     *        a SIZE x SIZE character array containing the given characters
     * @throws org.opentest4j.AssertionFailedError 
     *        if the number of strings or the length of any string is not equal to SIZE      
     */
    private char[][] generateCharArray(String... rows) {
        assertEquals(SIZE, rows.length, "The table must have SIZE rows.");

        char[][] table = new char[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            assertEquals(SIZE, rows[row].length(), "Each row must contain SIZE characters.");
            table[row] = rows[row].toCharArray();
        }
        return table;
    }

    /**
     * Test 1:
     * Tests that a KeyTable is generated correctly.
     *
     * The key phrase "PLAYFAIR EXAMPLE" should produce the
     * expected Playfair 5x5 key table. This test also verifies
     * duplicate removal, exclusion of J, character lookup,
     * and invalid input.
     */
    @Test
    public void testKeyTableGeneration() {
        KeyTable key = KeyTable.buildFromString("PLAYFAIR EXAMPLE");
        assertNotNull(key);

        char[][] expected = generateCharArray("PLAYF", "IREXM", "BCDGH", "KNOQS", "TUVWZ");
        char[][] table = key.getKeyTable();

        assertEquals(SIZE, table.length);

        StringBuilder letters = new StringBuilder();

        for (int row = 0; row < SIZE; row++) {
            assertEquals(SIZE, table[row].length);
            assertArrayEquals(expected[row], table[row]);

            for (int col = 0; col < SIZE; col++) {
                char ch = table[row][col];
                assertFalse(ch == 'J', "J must not appear in the key table.");
                assertEquals(-1, letters.indexOf(String.valueOf(ch)), "Duplicate letter: " + ch);
                letters.append(ch);
            }
        }

        assertEquals(SIZE * SIZE, letters.length());
        assertEquals(0, key.findRow('P'));
        assertEquals(0, key.findCol('P'));
        assertEquals(1, key.findRow('I'));
        assertEquals(0, key.findCol('I'));
        assertEquals(SIZE - 1, key.findRow('Z'));
        assertEquals(SIZE - 1, key.findCol('Z'));

        assertThrows(IllegalArgumentException.class, () -> key.findRow('J'));
        assertThrows(IllegalArgumentException.class, () -> key.findCol('J'));
        assertThrows(IllegalArgumentException.class, () -> key.findRow('?'));
        assertThrows(IllegalArgumentException.class, () -> key.findCol('?'));
        assertThrows(IllegalArgumentException.class, () -> KeyTable.buildFromString(null));
    }

    /**
     * Test 2:
     * Tests Bigram creation and Phrase queue operations.
     *
     * This test verifies uppercase conversion, ignored spaces and
     * punctuation, J-to-I replacement, repeated-letter splitting,
     * odd-length padding, and FIFO queue behavior.
     */
    @Test
    public void testBigramAndPhraseOperations() {
        Phrase phrase = Phrase.buildPhraseFromStringforEnc("Hello, Jolly World!");

        assertNotNull(phrase);
        assertEquals("HELXLOIOLXLYWORLDX", phrase.toString());
        assertEquals(9, phrase.size());

        assertEquals("HE", phrase.peek().toString());
        assertEquals("HE", phrase.dequeue().toString());
        assertEquals("LX", phrase.dequeue().toString());
        assertEquals("LO", phrase.dequeue().toString());
        assertEquals("IO", phrase.dequeue().toString());
        assertEquals("LX", phrase.dequeue().toString());
        assertEquals("LY", phrase.dequeue().toString());
        assertEquals("WO", phrase.dequeue().toString());
        assertEquals("RL", phrase.dequeue().toString());
        assertEquals("DX", phrase.dequeue().toString());
        assertTrue(phrase.isEmpty());
        assertEquals(0, phrase.size());

        // Optional: independently checks punctuation handling.
        Phrase punctuation = Phrase.buildPhraseFromStringforEnc("A-B!");
        assertEquals("AB", punctuation.toString());

        // Optional: independently checks J-to-I replacement.
        Phrase letterJ = Phrase.buildPhraseFromStringforEnc("J-A");
        assertEquals("IA", letterJ.toString());

        // Optional: independently checks repeated-letter splitting.
        Phrase repeated = Phrase.buildPhraseFromStringforEnc("BALLOON");
        assertEquals("BALXLOON", repeated.toString());

        // Optional: independently checks odd-length padding.
        Phrase odd = Phrase.buildPhraseFromStringforEnc("CAT");
        assertEquals("CATX", odd.toString());

        // Optional: separately checks FIFO queue operations.
        Phrase queue = Phrase.buildPhraseFromStringforEnc("ABCD");
        assertEquals("AB", queue.peek().toString());
        assertEquals("AB", queue.dequeue().toString());
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
        assertEquals("CD", queue.dequeue().toString());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    /**
     * Test 3:
     * Tests Playfair encryption using the example from the assignment.
     *
     */
    @Test
    public void testEncryption() {
        KeyTable key = KeyTable.buildFromString("PLAYFAIR EXAMPLE");

        Phrase ENCRYPTED = Phrase.buildPhraseFromStringforEnc("HELLO WORLD");
        assertEquals("DMYRANVQCRGE", ENCRYPTED.encrypt(key).toString());

        Phrase ROW = Phrase.buildPhraseFromStringforEnc("EX");
        assertEquals("XM", ROW.encrypt(key).toString());

        Phrase ROW_ENCRYPTED = Phrase.buildPhraseFromStringforEnc("FP");
        assertEquals("PL", ROW_ENCRYPTED.encrypt(key).toString());

        Phrase COLUMN = Phrase.buildPhraseFromStringforEnc("DE");
        assertEquals("OD", COLUMN.encrypt(key).toString());

        Phrase COLUMN_ENCRYPTED = Phrase.buildPhraseFromStringforEnc("TP");
        assertEquals("PI", COLUMN_ENCRYPTED.encrypt(key).toString());

        Phrase CORNERS = Phrase.buildPhraseFromStringforEnc("HI");
        assertEquals("BM", CORNERS.encrypt(key).toString());

        Phrase ORIGINAL = Phrase.buildPhraseFromStringforEnc("HELLO WORLD");
        Phrase RESULT = ORIGINAL.encrypt(key);
        assertEquals(6, RESULT.size());

        Phrase nullKey = Phrase.buildPhraseFromStringforEnc("HELLO");
        assertThrows(IllegalArgumentException.class, () -> nullKey.encrypt(null));
    }

    /**
     * Test 4:
     * Tests Playfair decryption using the example from the assignment.
     *
     */
    @Test
    public void testDecryption() {
        KeyTable key = KeyTable.buildFromString("PLAYFAIR EXAMPLE");

        Phrase DECRYPTED = Phrase.buildPhraseFromStringforEnc("DMYRANVQCRGE");
        assertEquals("HELXLOWORLDX", DECRYPTED.decrypt(key).toString());

        Phrase ROW = Phrase.buildPhraseFromStringforEnc("XM");
        assertEquals("EX", ROW.decrypt(key).toString());

        Phrase ROW_DECRYPTED = Phrase.buildPhraseFromStringforEnc("PL");
        assertEquals("FP", ROW_DECRYPTED.decrypt(key).toString());

        Phrase COLUMN = Phrase.buildPhraseFromStringforEnc("OD");
        assertEquals("DE", COLUMN.decrypt(key).toString());

        Phrase COLUMN_DECRYPTED = Phrase.buildPhraseFromStringforEnc("PI");
        assertEquals("TP", COLUMN_DECRYPTED.decrypt(key).toString());

        Phrase CORNERS = Phrase.buildPhraseFromStringforEnc("BM");
        assertEquals("HI", CORNERS.decrypt(key).toString());

        Phrase PLAIN = Phrase.buildPhraseFromStringforEnc("Hello World");
        Phrase ROUND_TRIP = PLAIN.encrypt(key).decrypt(key);
        assertEquals("HELXLOWORLDX", ROUND_TRIP.toString());

        Phrase nullKey = Phrase.buildPhraseFromStringforEnc("DMYR");
        assertThrows(IllegalArgumentException.class, () -> nullKey.decrypt(null));
    }

    /**
     * Test 5:
     * Tests changing the key and default key behavior.
     *
     */
    @Test
    public void testKeyChangesAndDefaultKey() {
        KeyTable KEY = KeyTable.buildFromString("HELLO WORLD");
        char[][] EXPECTED = generateCharArray("HELOW", "RDABC", "FGIKM", "NPQST", "UVXYZ");
        char[][] TABLE = KEY.getKeyTable();

        for (int row = 0; row < SIZE; row++) {
            assertArrayEquals(EXPECTED[row], TABLE[row]);
        }

        KeyTable PLAYFAIR_KEY = KeyTable.buildFromString("PLAYFAIR EXAMPLE");
        Phrase FIRST = Phrase.buildPhraseFromStringforEnc("HELLO WORLD");
        Phrase SECOND = Phrase.buildPhraseFromStringforEnc("HELLO WORLD");
        String FIRST_RESULT = FIRST.encrypt(PLAYFAIR_KEY).toString();
        String SECOND_RESULT = SECOND.encrypt(KEY).toString();

        assertEquals("DMYRANVQCRGE", FIRST_RESULT);
        assertEquals("ELALOWHWAHAV", SECOND_RESULT);
        assertNotEquals(FIRST_RESULT, SECOND_RESULT);

        KeyTable DEFAULT_KEY = KeyTable.buildFromString("");
        char[][] DEFAULT_EXPECTED = generateCharArray("ABCDE", "FGHIK", "LMNOP", "QRSTU", "VWXYZ");
        char[][] DEFAULT_TABLE = DEFAULT_KEY.getKeyTable();

        for (int row = 0; row < SIZE; row++) {
            assertArrayEquals(DEFAULT_EXPECTED[row], DEFAULT_TABLE[row]);
        }
        assertThrows(IllegalArgumentException.class, () -> DEFAULT_KEY.findRow('J'));
    }
}