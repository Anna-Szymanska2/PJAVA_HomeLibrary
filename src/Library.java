import java.util.ArrayList;

public class Library {
    ArrayList<Book> books;

    public Library(ArrayList<Book> books){
        this.books = books;
    }

    public void addBook(Book book){
        this.books.add(book);
    }

    public void removeBook(Book book){
        this.books.remove(book);
    }
}
