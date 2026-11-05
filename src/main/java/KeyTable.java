//Ivan Sun (118267652) Recitation (08)
import java.util.ArrayList;

public class KeyTable {
    private char[][] key = new char[5][5];

    public KeyTable(){
    }

    /**
     * Builds a KeyTable using an input String
     * @param keyphrase String that the keyTable is built from
     * @return returns a Keytable with a cipher bulti with the parameter
     * @throws IllegalArgumentException when the parameter is null
     */
    public static KeyTable buildFromString(String keyphrase) throws IllegalArgumentException{
        if (keyphrase == null) throw new IllegalArgumentException();

        ArrayList<Character> LETTERS = new ArrayList<Character>();
        for (char C : keyphrase.toUpperCase().toCharArray()){
            if (C == 'J'){
                C = 'I';
            }
            if (C >= 'A' && C <= 'Z' && !(LETTERS.contains(C))){
                LETTERS.add(C);
            }
        }
        for (char C = 'A'; C<='Z'; C++){
            if (C != 'J' && !(LETTERS.contains(C))){
                LETTERS.add(C);
            }
        }

        KeyTable CYPHER = new KeyTable();
        int INDEX = 0;
        for (int i = 0; i<CYPHER.key.length; i++){
            for (int j = 0; j<CYPHER.key[0].length; j++){
                CYPHER.key[i][j] = LETTERS.get(INDEX);
                INDEX++;
            }
        }
        return CYPHER;
    }

    /**
     * returns the Keytable
     * @return the keytable
     */
    public char[][] getKeyTable(){
        return key;
    }

    /**
     * searches and returns the row that the parameter is in
     * @param c the character we are looking for
     * @return the row that character C is in
     * @throws IllegalArgumentException when C is not found in the keytable
     */
    public int findRow(char c) throws IllegalArgumentException{
        for (int i = 0; i< key.length; i++){
            for(int j = 0; j< key[0].length; j++){
                if (key[i][j] == c){
                    return i;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * searches and returns the column that the parameter is in
     * @param c the character we are looking for
     * @return the column that character c is in
     * @throws IllegalArgumentException when C is not found in the KeyTable
     */
    public int findCol(char c) throws IllegalArgumentException{
        for (int i = 0; i< key.length; i++){
            for(int j = 0; j< key[0].length; j++){
            if (key[i][j] == c){
                return j;
            }
        }
    }
        throw new IllegalArgumentException();
    }

    /**
     * Returns the character at cordinate location (x,y)
     * @param x the row that we are looking for that key at
     * @param y the column that we are looking for that key at
     * @return returns the chracter at the desired location
     */
    public char getKeyAtLocation(int x, int y){
        return key[x][y];
    }

    /**
     * returns the keytable in string format
     * @return prints the keytable
     */
    public String toString(){
        String RESULT = "";
        for (int i = 0; i<key.length; i++){
            for (int j = 0; j<key[0].length; j++){
                RESULT += key[i][j];
                if (j < key[0].length - 1){
                    RESULT += " ";
                }
            }
            RESULT += "\n";
        }
        return RESULT;
    }
}
