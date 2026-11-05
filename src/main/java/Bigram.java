//Ivan Sun (118267652) Recitation (08)

public class Bigram{
    private char first;
    private char second;

    public Bigram(){
    }

    /**
     * gets the first character in bigram
     * @return the first character in bigram
     */
    public char getFirst(){
        return first;
    }

    /**
     * sets the first char in the bigram
     * @param first the character we are setting the first char of bigram to.
     */
    public void setFirst(char first){
        this.first = first;
    }
    /**
     * gets the second character in bigram
     * @return the second character in bigram
     */
    public char getSecond(){
        return second;
    }
    /**
     * sets the second char in the bigram
     * @param second the character we are setting the second char of bigram to.
     */
    public void setSecond(char second){
        this.second = second;
    }

    /**
     * Prints the bigram
     * @return a string of the bigram
     */
    public String toString(){
        return "" + first + second;
    }
}