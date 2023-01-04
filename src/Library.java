import java.util.*;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable {
    ArrayList<Book> books;
   // ArrayList<Book> borrowedBooks = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    HashMap<String,char[]> namesAndPasswords = new HashMap<>();
    Administrator admin;

    private User currentlyLoggedUser = null;

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    /*public ArrayList<Book> getBorrowedBooks() {
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
    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
        user.deleteAccount(user);
    }

    public void addBook(Book book){
        this.books.add(book);
    }
    public ArrayList<Book> getBooks() {
        return books;
    }

    public void removeBook(Book book){
        this.books.remove(book);
    }

   // public void addBorrowedBook(Book borrowedBook){borrowedBooks.add(borrowedBook);}

    public void removeBorrowedBook(Book borrowedBook){this.books.remove(borrowedBook);}

    public ArrayList<Book> reassignBooksToFilter(ArrayList<Book> booksToFilter,ArrayList<Book> booksAfterFiltration){
        while(booksToFilter.size()>booksAfterFiltration.size()){
            booksToFilter.remove(booksToFilter.size()-1);
        }
        Collections.copy(booksToFilter,booksAfterFiltration);
        return booksToFilter;
    }

    public ArrayList<Book> filtration(String author, int pages, int pages2, int publishYear, int publishYear2, String genre, int volumes, int volumes2, int rating, int rating2) {
        ArrayList<Book> booksToFilter = new ArrayList<>(getBooks());
        ArrayList<Book> booksAfterFiltration = new ArrayList<>();
//        if(!title.equals("0")){
//            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
//                    .filter(book -> book.title.equals(title))
//                    .collect(Collectors.toList());
//            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
//        }
        if(!author.equals("-")){
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> book.author.equals(author))
                    .collect(Collectors.toList());
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
        if(pages2 != 0 || pages !=0){
            for(Book bookAfter : booksToFilter){
                if(bookAfter.pages>pages2) {
                    booksAfterFiltration.remove(bookAfter);
                }
                if(bookAfter.pages<pages){
                     booksAfterFiltration.remove(bookAfter);
                }
            }
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }

        if(publishYear != 0 || publishYear2 !=0){
            for(Book bookAfter: booksToFilter){
                if(bookAfter.publishYear>publishYear2) {
                    booksAfterFiltration.remove(bookAfter);
                }
                if(bookAfter.publishYear<publishYear){
                    booksAfterFiltration.remove(bookAfter);
                }
            }
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
        if(!genre.equals("-")){
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> book.genre.equals(genre))
                    .collect(Collectors.toList());
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
//        if(!series.equals("0")){
//            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
//                    .filter(book -> Objects.equals(book.series, series))
//                    .collect(Collectors.toList());
//            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
//        }
        if(volumes2 != 0 || volumes != 0){
            HashSet<String> namesOfFittingSeries = new HashSet<>();
            HashSet<String> namesOfUnFittingSeries = new HashSet<>();
            ArrayList<Book> booksAfterFiltration2 = new ArrayList<>();
            if(volumes == 0 || volumes == 1){
                booksAfterFiltration2 = (ArrayList<Book>) booksToFilter.stream()
                        .filter(book -> book.series == null)
                        .collect(Collectors.toList());
                volumes = 1;
            }
            for(Book bookAfter: booksToFilter){
                if(bookAfter.series != null) {
                    if (bookAfter.seriesVolume > volumes2)
                        namesOfUnFittingSeries.add(bookAfter.series);
                    if(!namesOfUnFittingSeries.contains(bookAfter.series)) {
                        if (bookAfter.seriesVolume > volumes)
                            namesOfFittingSeries.add(bookAfter.series);
                    }
                }
            }
            namesOfFittingSeries.removeIf(next -> namesOfUnFittingSeries.contains(next));
            System.out.println(namesOfFittingSeries);
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> namesOfFittingSeries.contains(book.series))
                    .collect(Collectors.toList());
            booksAfterFiltration.addAll(booksAfterFiltration2);
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
        if(rating != 0 || rating2 != 0){
            for(Book bookAfter: booksToFilter){
                if(bookAfter.rating>rating2) {
                    booksAfterFiltration.remove(bookAfter);
                }
                if(bookAfter.rating<rating){
                    booksAfterFiltration.remove(bookAfter);
                }
            }
            reassignBooksToFilter(booksToFilter,booksAfterFiltration);
        }
        return booksAfterFiltration;
    }
}
