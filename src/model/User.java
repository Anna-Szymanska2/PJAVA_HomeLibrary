package model;

import java.io.Serializable;
import java.util.*;

/**
 * A User class represents user of the application.
 */
public class User implements Serializable {
    private String name;
    private String password;
    private ArrayList<Book> booksRead = new ArrayList<>();;
    private ArrayList<Book> booksToRead = new ArrayList<>();;
    private ArrayList<Book> booksRated = new ArrayList<>();
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    /**
     * Constructor that not only creates new object, but it also adds it to the list of users of library and its names
     * and passwords collection.
     * @param name
     * @param password
     * @param library
     */
    public User(String name, char[] password, Library library){
        this.name = name;
        this.password = password.toString();
        library.getNamesAndPasswords().put(name,password);
        library.getUsers().add(this);
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addToBorrowed(Book book) {
        borrowedBooks.add(book);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Book> getBooksRead() {
        return booksRead;
    }

    public ArrayList<Book> getBooksToRead() {
        return booksToRead;
    }

    public ArrayList<Book> getBooksRated() {
        return booksRated;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRead(Book book){
        this.booksRead.add(book);
    }

    public void removeFromRead(Book book){
        this.booksRead.remove(book);
    }

    public void addToRead(Book book){
        this.booksToRead.add(book);
    }

    public void removeFromToRead(Book book){
        this.booksToRead.remove(book);
    }

    /**
     * Adds rating of the book given by user to all of this book ratings
     * and adds the book to user's list of books rated.
     *
     * @param rating
     * @param book
     */
    public void addRating(int rating,Book book){

            if(booksRated.contains(book)){
                book.getRatings().replace(this.name,rating);
            }else {
                book.getRatings().put(this.name, rating);
                booksRated.add(book);
                book.calculateRating();
            }

    }

    /**
     * Removes rating of the book given by user from all of this book ratings
     * and deletes the book from user's list of books rated.
     *
     * @param book
     */
    public void removeRating(Book book){
        if(booksRated.contains(book)){
        book.getRatings().remove(this.name);
        booksRated.remove(book);
        book.calculateRating();
        }
    }

    /**
     * Returns list of books recommended for user to read next depending on the list of books user has already read.
     * Books are recommended based on which genres user has already read and which genres they read the most often
     * except for books rated lower than 5/10.
     * Books written by authors which books user has read are also added to the recommendation list.
     *
     * @param library
     * @return list of books recommended for user.
     */
    public List<Book> recommendBooks(Library library){
        Set<Book> recommendedBooks = new HashSet<>();
        Random randomGenerator = new Random();
        Map<String, Integer> genresLiked = new HashMap<>();
        for (Book book : getBooksRead()) {
            String genre = book.getGenre();
            int count = genresLiked.getOrDefault(genre, 0);
            genresLiked.put(genre, count + 1);
        }

        int sum = getBooksRead().size();
        Set keySet = genresLiked.keySet();
        ArrayList<Book> booksWithTheSameGenre = new ArrayList<>();

        for(Object genre: keySet){
            booksWithTheSameGenre.addAll(library.filtration("-",0,0,0,0, (String) genre,0,0,0,0));
            int countOfRandomBooksToChoose = (genresLiked.get(genre)*50)/sum;
            for (int i = 0; i < countOfRandomBooksToChoose; i++) {
                int index = randomGenerator.nextInt(booksWithTheSameGenre.size());
                if(booksWithTheSameGenre.get(index).getRating()<5){
                    booksWithTheSameGenre.remove(index);
                }else {
                    recommendedBooks.add(booksWithTheSameGenre.get(index));
                }
            }
            booksWithTheSameGenre.clear();
        }

        Set<String> authorsRead = new HashSet<>();
        for(Book book: getBooksRead()){
            authorsRead.add(book.getAuthor());
        }
        for(String author: authorsRead) {
            recommendedBooks.addAll(library.filtration(author,0,0,0,0,"-",0,0,0,0));
        }

        for(Book book: getBooksRead()){
            recommendedBooks.remove(book);
        }

        List<Book> booksRecommended = new ArrayList<>(recommendedBooks);
        Iterator<Book> i = booksRecommended.iterator();
        while (i.hasNext()) {
            Book book = i.next();
            if(book.getSeriesVolume()>1)
                i.remove();
        }

        return booksRecommended;
    }

    /**
     * Deletes user account.
     *
     * @param user
     */
    public void deleteAccount(User user){
        user = null;
    }


    @Override
    public String toString(){
        return name;
    }


}
