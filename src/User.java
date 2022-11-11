import java.util.ArrayList;

public class User {
    private String name;
    private String password;
    ArrayList<Book> booksRead;
    ArrayList<Book> booksToRead;

    public User(String name, String password){
        this.name = name;
        this.password = password;
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

    public void addRating(int rating){

    }

    public void removeRating(Book book){

    }

    public void deleteAccount(User user){
        user = null;
    }
}
