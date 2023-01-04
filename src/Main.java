import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Book> books = FileLoader.returnBooksFromFile();
        Library library = new Library(books);
        //System.out.println(library.getBooks());
        //library.filtration("0","Sanderson Brandon",0,0,0,0,"0","0",0,6,0,0);


//        User Agata = new User("Agata","1234", library);
//        User sonia = new User("sonia","oko", library);
//        User Krysia = new User("Krysia","332", library);
//
//        Scanner myObj = new Scanner(System.in);
//
//        System.out.println("Enter name and password: ");
//
//        String name = myObj.nextLine();
//        String password = myObj.nextLine();
//
//        User loggedUser = CheckLogin.checkLogin(name,password, library);
//
//        if(loggedUser != null) {
//
//            System.out.println("Podaj index przeczytanej książki: ");
//            int x = myObj.nextInt();
//            Book ksiazka = library.books.get(x);
//            loggedUser.addRead(ksiazka);
//            loggedUser.displayReadBooks();
//
//            System.out.println("Podaj ocenę do książki: ");
//            int z = myObj.nextInt();
//            loggedUser.addRating(z, ksiazka);
//            ksiazka.calculateRating();
//            ksiazka.description();
//        }
    }

}
