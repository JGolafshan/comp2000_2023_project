public class DatabaseReaderException extends RuntimeException {
    /**
     * Constructs an DatabaseValueException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public DatabaseReaderException() {
        super();
    }

    /**
     * Constructs an DatabaseValueException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * @param s the String that contains a detailed message
     */
    public DatabaseReaderException(String s) {
        super(s);
    } 
}
