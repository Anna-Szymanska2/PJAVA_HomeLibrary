import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable {
    ArrayList<Book> books;
    ArrayList<Book> borrowedBooks = new ArrayList<>();

    public ArrayList<Book> getBooks() {
        return books;
    }

    public Library(ArrayList<Book> books){
        this.books = books;
    }

    public void addBook(Book book){
        this.books.add(book);
    }

    public void addBorrowedBook(Book borrowedBook){borrowedBooks.add(borrowedBook);}

    public void removeBorrowedBook(Book borrowedBook){this.books.remove(borrowedBook);}

    public void removeBook(Book book){
        this.books.remove(book);
    }

}
