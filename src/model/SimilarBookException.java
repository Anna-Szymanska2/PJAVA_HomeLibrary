package model;

/**
 * SimilarBookException is a class that extends Exception, it is thrown when someone tries to add too similar book
 * to one already existing in library.
 */
public class SimilarBookException extends Exception{
    SimilarBookException(String message){
        super(message);
    }
}
