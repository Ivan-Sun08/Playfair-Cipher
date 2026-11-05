//Ivan Sun (118267652) Recitation (08)
import java.util.Scanner;

public class PlayfairEncryptionEngine {
    public static void main(String[] args) {
        Scanner SCANNER = new Scanner(System.in);
        System.out.print("Enter key phrase: ");
        String KEY = SCANNER.nextLine();
        KeyTable KEY_TABLE = KeyTable.buildFromString(KEY);
        System.out.println("Generation success!");
        System.out.println("Menu:\n" +
                "(CK) - Change key\n" +
                "(PK) - Print key\n" +
                "(EN) - Encrypt\n" +
                "(DE) - Decrypt\n" +
                "(Q) - Quit\n" +
                "\n" +
                "Please select an option: ");
        String X = SCANNER.nextLine();
        X = X.toUpperCase();
        while (!(X.equals("Q"))){
            if(X.equals("CK")){
                System.out.print("Enter key phrase: ");
                KEY = SCANNER.nextLine();
                KEY_TABLE = KeyTable.buildFromString(KEY);
                System.out.println("Generation success!");
            }
            else if (X.equals("PK")){
                System.out.print(KEY_TABLE.toString());
            }
            else if (X.equals("EN")){
                String result = "";
                System.out.print("Please enter a phrase to encrypt: ");
                KEY = SCANNER.nextLine();
                Phrase PHRASE = Phrase.buildPhraseFromStringforEnc(KEY);
                result += "Encrypted text is: ";
                result += PHRASE.encrypt(KEY_TABLE);
                System.out.println(result);
            }
            else if (X.equals("DE")){
                String result = "";
                System.out.print("Please enter a phrase to decrypt: ");
                Phrase Y = Phrase.buildPhraseFromStringforDec(SCANNER.nextLine());
                result += "Decrypted text is: ";
                result += Y.decrypt(KEY_TABLE);
                System.out.println(result);
            }

            System.out.println("Menu:\n" +
                    "(CK) - Change key\n" +
                    "(PK) - Print key\n" +
                    "(EN) - Encrypt\n" +
                    "(DE) - Decrypt\n" +
                    "(Q) - Quit\n" +
                    "\n" +
                    "Please select an option: ");
            X = SCANNER.nextLine();
            X = X.toUpperCase();
        }
        System.out.print("Program terminating....");
    }
}