import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;
public class User {
    private String name;
    private String password;
    ArrayList<Book> booksRead = new ArrayList<>();;
    ArrayList<Book> booksToRead = new ArrayList<>();;
    ArrayList<Book> booksRated = new ArrayList<>();

    public User(String name, String password, Library library){
        this.name = name;
        this.password = password;
        library.namesAndPasswords.put(name,password);
        library.users.add(this);
    }
    Book selectBook(ArrayList<Book> books){
        for(int i = 0; i < books.size(); i++){
            System.out.print((i+1) + ". ");
            books.get(i).description();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj numer książki, którą chcesz wybrać: ");
        int chosenNumber = scanner.nextInt();
        scanner.nextLine();

        return books.get(chosenNumber - 1);
    }

    public void chooseAndAddToRead(ArrayList<Book> books){
        Book readBook = selectBook(books);
        if(!booksRead.contains(readBook))
            addRead(readBook);
        else
            System.out.println("Już dodałeś tę książkę do przeczytanych");
    }

    public void chooseAndAddToBeRead(ArrayList<Book> books){
        Book bookToRead = selectBook(books);
        if(!booksToRead.contains(bookToRead))
            addToRead(bookToRead);
        else
            System.out.println("Już dodałeś tę książkę do listy książek do czytania");
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

    public void addRating(int rating,Book book){
        if(booksRead.contains(book)) {
            book.ratings.put(this.name, rating);
            booksRated.add(book);
        }
        else
            System.out.println("Nie możesz ocenić książki, której nie przeczytałeś");
    }

    public void removeRating(Book book){
        if(booksRated.contains(book))
        book.ratings.remove(this.name);
        else
            System.out.println("Nie wystawiłeś tej książce oceny");
    }

    public void deleteAccount(User user){
        user = null;
    }
    public void displayReadBooks(){
        System.out.println("Twoje przeczytane książki: ");
        for(Book readBook: booksRead){
            readBook.description();
        }
        System.out.println();
    }
    public void displayRatedBooks(){
        System.out.println("Twoje ocenione książki: ");
        for(Book ratedBook: booksRated){
            ratedBook.description();
        }
        System.out.println();
    }
    public void displayBooksToRead(){
        System.out.println("Twoje książki do przeczytania: ");
        for(Book bookToRead: booksToRead){
            bookToRead.description();
        }
        System.out.println();
    }

}
