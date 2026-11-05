//Ivan Sun (118267652) Recitation (08)

import java.util.LinkedList;
import java.util.ArrayList;
class Phrase extends LinkedList<Bigram>{
    /**
     * adds a bigram to the phrase
     * @param b the bigram we add to the phrase
     */
    public void enqueue(Bigram b){
        this.add(b);
    }

    /**
     * removes the first bigram from the queue
     * @return returns the removed bigram
     */
    public Bigram dequeue(){
        return this.poll();
    }

    /**
     * looks at the top bigram in the queue
     * @return returns the bigram that we looked at.
     */
    public Bigram peek(){
        return super.peek();
    }

    /**
     * Builds a phrase by creating bigrams with the String
     * @param s the String we are preparing for encryption
     * @return the String in a phrase form for encryption
     */
    public static Phrase buildPhraseFromStringforEnc(String s){
        Phrase RESULT = new Phrase();
        ArrayList<Character> S_TO_CHAR_ARRAY = new ArrayList<Character>();
        char[] CHAR_ARRAY = s.toUpperCase().toCharArray();
        for (int i = 0; i<CHAR_ARRAY.length;i++){
            char C = CHAR_ARRAY[i];
            if (C < 'A' || C > 'Z'){
                continue;
            }
            if (C == 'J'){
                C = 'I';
            }
            if (S_TO_CHAR_ARRAY.size() % 2 == 1 && S_TO_CHAR_ARRAY.get(S_TO_CHAR_ARRAY.size()-1) == C){
                S_TO_CHAR_ARRAY.add('X');
            }
            S_TO_CHAR_ARRAY.add(C);
        }
        if (S_TO_CHAR_ARRAY.size() % 2 == 1){
            S_TO_CHAR_ARRAY.add('X');
        }
        for (int i = 0; i<S_TO_CHAR_ARRAY.size();i+=2){
            Bigram ADD = new Bigram();
            ADD.setFirst(S_TO_CHAR_ARRAY.get(i));
            ADD.setSecond(S_TO_CHAR_ARRAY.get(i+1));
            RESULT.enqueue(ADD);
        }
        return RESULT;
    }

    /**
     * Converts a string from String to Phrase to be decrypted
     * @param s the Encrypted string that we are preparing
     * @return the string in phrase format.
     */
    public static Phrase buildPhraseFromStringforDec(String s){
        Phrase RESULT = new Phrase();
        String LETTERS = "";
        for (char C : s.toUpperCase().toCharArray()){
            if (C == 'J'){
                C = 'I';
            }
            if (C >= 'A' && C <= 'Z'){
                LETTERS += C;
            }
        }
        for (int i = 0; i+1<LETTERS.length(); i+=2){
            Bigram ADD = new Bigram();
            ADD.setFirst(LETTERS.charAt(i));
            ADD.setSecond(LETTERS.charAt(i+1));
            RESULT.enqueue(ADD);
        }
        return RESULT;
    }

    /**
     * Encrypts the phrase
     * @param key the key table we are using to encrypt
     * @return returns Phrase after encryption
     * @throws IllegalArgumentException if the keytable used is null
     */
    public Phrase encrypt(KeyTable key) throws IllegalArgumentException{
        if (key == null) throw new IllegalArgumentException();

        Phrase ENCRYPTED = new Phrase();
        while (!(isEmpty())){
            Bigram TEMP = dequeue();

            char FIRST = TEMP.getFirst();
            char SECOND = TEMP.getSecond();

            int F_X = key.findRow(FIRST);
            int F_Y = key.findCol(FIRST);

            int S_X = key.findRow(SECOND);
            int S_Y = key.findCol(SECOND);

            Bigram RESULT = new Bigram();

            if (F_X == S_X){
                RESULT.setFirst(key.getKeyAtLocation(F_X, (F_Y + 1) % 5));
                RESULT.setSecond(key.getKeyAtLocation(S_X, (S_Y + 1) % 5));
            }
            else if (F_Y == S_Y){
                RESULT.setFirst(key.getKeyAtLocation((F_X + 1) % 5, F_Y));
                RESULT.setSecond(key.getKeyAtLocation((S_X + 1) % 5, S_Y));
            }
            else{
                RESULT.setFirst(key.getKeyAtLocation(F_X, S_Y));
                RESULT.setSecond(key.getKeyAtLocation(S_X, F_Y));
            }
            ENCRYPTED.enqueue(RESULT);
        }
        return ENCRYPTED;
    }
    /**
     * Decrypts the phrase
     * @param key the key table we are using to decrypt
     * @return returns Phrase after decryption
     * @throws IllegalArgumentException if the keytable used is null
     */
    public Phrase decrypt(KeyTable key) throws IllegalArgumentException{
        if (key == null) throw new IllegalArgumentException();

        Phrase DECRYPTED = new Phrase();
        while (!(isEmpty())){
            Bigram TEMP = dequeue();

            char FIRST = TEMP.getFirst();
            char SECOND = TEMP.getSecond();

            int F_X = key.findRow(FIRST);
            int F_Y = key.findCol(FIRST);

            int S_X = key.findRow(SECOND);
            int S_Y = key.findCol(SECOND);

            Bigram RESULT = new Bigram();

            if (F_X == S_X){
                RESULT.setFirst(key.getKeyAtLocation(F_X, (F_Y + 4) % 5));
                RESULT.setSecond(key.getKeyAtLocation(S_X, (S_Y + 4) % 5));
            }
            else if (F_Y == S_Y){
                RESULT.setFirst(key.getKeyAtLocation((F_X + 4) % 5, F_Y));
                RESULT.setSecond(key.getKeyAtLocation((S_X + 4) % 5, S_Y));
            }
            else{
                RESULT.setFirst(key.getKeyAtLocation(F_X, S_Y));
                RESULT.setSecond(key.getKeyAtLocation(S_X, F_Y));
            }
            DECRYPTED.enqueue(RESULT);
        }
        return DECRYPTED;
    }

    /**
     * returns the phrase in string format
     * @return retures in phrase in string format
     */
    public String toString(){
        String RESULT = "";
        for (Bigram B : this){
            RESULT += B.toString();
        }
        return RESULT;
    }
}
