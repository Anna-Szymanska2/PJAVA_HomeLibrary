package model;

import java.util.*;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A library class is the main entity in our application.
 * It stores all the books and users and is used to perform various actions regarding them.
 */
public class Library implements Serializable {
    private ArrayList<Book> books;
   // ArrayList<model.Book> borrowedBooks = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    /**
     * Storing all users passwords used for logging.
     */
    private HashMap<String,char[]> namesAndPasswords = new HashMap<>();
    private Administrator admin;
    private User currentlyLoggedUser = null;

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public HashMap<String, char[]> getNamesAndPasswords() {
        return namesAndPasswords;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    /*public ArrayList<model.Book> getBorrowedBooks() {
        return borrowedBooks;
    }*/

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getCurrentlyLoggedUser() {
        return currentlyLoggedUser;
    }

    public void setCurrentlyLoggedUser(User currentlyLoggedUser) {
        this.currentlyLoggedUser = currentlyLoggedUser;
    }

    public Library(ArrayList<Book> books){
        this.books = books;
    }
    public Library(){
    }

    /**
     * Method used to add new user to the library.
     *
     * @param user
     */
    public void addUser(User user){
        users.add(user);
    }

    /**
     * Method used to remove user from the library.
     *
     * @param user
     */
    public void removeUser(User user){
        users.remove(user);
        user.deleteAccount(user);
    }
    /**
     * Method used to add new book to the library.
     *
     * @param book
     */
    public void addBook(Book book){
        this.books.add(book);
    }
    public ArrayList<Book> getBooks() {
        return books;
    }
    /**
     * Method used to remove book from the library.
     *
     * @param book
     */
    public void removeBook(Book book){
        this.books.remove(book);
    }

   // public void addBorrowedBook(model.Book borrowedBook){borrowedBooks.add(borrowedBook);}
    /**
     * Method used to remove book that has been borrowed from the library.
     *
     * @param borrowedBook
     */
    public void removeBorrowedBook(Book borrowedBook){this.books.remove(borrowedBook);}

    /**
     * Changes size and name of ArrayList of books which is a result of filtering,
     * so it can be used in the next filter.
     *
     * @param booksToFilter
     * @param booksAfterFiltration
     * @return
     */
    public ArrayList<Book> reassignBooksToFilter(ArrayList<Book> booksToFilter,ArrayList<Book> booksAfterFiltration){
        while(booksToFilter.size()>booksAfterFiltration.size()){
            booksToFilter.remove(booksToFilter.size()-1);
        }
        Collections.copy(booksToFilter,booksAfterFiltration);
        return booksToFilter;
    }

    /**
     * Returns ArrayList of books with are chosen by filtering all books in the library
     * by given parameters.
     * Those parameters are: author's name, minimum and maximum page count, the earliest and the latest year a book could
     * have been published, genre of the book, minimum and maximum amount of volumes in the series and minimum and maximum rating
     * a book has been given.
     * It's possible to apply many filtering parameters at once.
     *
     * @param author
     * @param pages
     * @param pages2
     * @param publishYear
     * @param publishYear2
     * @param genre
     * @param volumes
     * @param volumes2
     * @param rating
     * @param rating2
     * @return
     */
    public ArrayList<Book> filtration(String author, int pages, int pages2, int publishYear, int publishYear2, String genre, int volumes, int volumes2, int rating, int rating2) {
        ArrayList<Book> booksToFilter = new ArrayList<>(getBooks());
        ArrayList<Book> booksAfterFiltration = new ArrayList<>();
        if(!author.equals("-")){
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> book.getAuthor().equals(author))
                    .collect(Collectors.toList());
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
        if(pages2 != 0 || pages !=0){
            for(Book bookAfter : booksToFilter){
                if(bookAfter.getPages()>pages2) {
                    booksAfterFiltration.remove(bookAfter);
                }
                if(bookAfter.getPages()<pages){
                     booksAfterFiltration.remove(bookAfter);
                }
            }
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }

        if(publishYear != 0 || publishYear2 !=0){
            for(Book bookAfter: booksToFilter){
                if(bookAfter.getPublishYear()>publishYear2) {
                    booksAfterFiltration.remove(bookAfter);
                }
                if(bookAfter.getPublishYear()<publishYear){
                    booksAfterFiltration.remove(bookAfter);
                }
            }
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
        if(!genre.equals("-")){
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> book.getGenre().equals(genre))
                    .collect(Collectors.toList());
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
        if(volumes2 != 0 || volumes != 0){
            HashSet<String> namesOfFittingSeries = new HashSet<>();
            HashSet<String> namesOfUnFittingSeries = new HashSet<>();
            ArrayList<Book> booksAfterFiltration2 = new ArrayList<>();
            if(volumes == 0 || volumes == 1){
                booksAfterFiltration2 = (ArrayList<Book>) booksToFilter.stream()
                        .filter(book -> book.getSeries() == null)
                        .collect(Collectors.toList());
                volumes = 1;
            }
            for(Book bookAfter: booksToFilter){
                if(bookAfter.getSeries() != null) {
                    if (bookAfter.getSeriesVolume() > volumes2)
                        namesOfUnFittingSeries.add(bookAfter.getSeries());
                    if(!namesOfUnFittingSeries.contains(bookAfter.getSeries())) {
                        if (bookAfter.getSeriesVolume() > volumes)
                            namesOfFittingSeries.add(bookAfter.getSeries());
                    }
                }
            }
            namesOfFittingSeries.removeIf(next -> namesOfUnFittingSeries.contains(next));
            System.out.println(namesOfFittingSeries);
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> namesOfFittingSeries.contains(book.getSeries()))
                    .collect(Collectors.toList());
            booksAfterFiltration.addAll(booksAfterFiltration2);
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
        if(rating != 0 || rating2 != 0){
            for(Book bookAfter: booksToFilter){
                if(bookAfter.getRating()>rating2) {
                    booksAfterFiltration.remove(bookAfter);
                }
                if(bookAfter.getRating()<rating){
                    booksAfterFiltration.remove(bookAfter);
                }
            }
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
        return booksAfterFiltration;
    }
}
