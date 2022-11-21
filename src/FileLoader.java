import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {
    public static void main(String []arg) throws FileNotFoundException {
        test();
    }
    public static ArrayList<Book> returnBooksFromFile() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("C:\\Users\\Dorota\\Documents\\Cukierki\\Studia\\3 rok\\Pijawa\\Książki.csv"));
        sc.useDelimiter(";");   //sets the delimiter pattern
        sc.nextLine();
        int counter = 1;
        String title = null;
        String series = null;
        int seriesVolume = 0;
        String author = null;
        int pages = 0;
        int publishYear = 0;
        String genre = null;
        String scanningResult;
        ArrayList<Book> books = new ArrayList<>();
        String find = "tom";


        while (sc.hasNext())  //returns a boolean value
        {
            scanningResult = sc.next();  //find and returns the next complete token from this scanner
            switch(counter){
                case 2: title = scanningResult;
                    int beginningIndex = title.indexOf(find);
                    if(beginningIndex>0){ // it's a series
                        series = title.substring(0, beginningIndex - 2);
                        if(Character.isDigit(title.charAt(beginningIndex+5))){ //tom XX
                            if(title.charAt(beginningIndex+4) == '0') //tom 0X
                                seriesVolume = Integer.parseInt(title.substring(beginningIndex+5, beginningIndex + 6));
                            else {
                                seriesVolume = Integer.parseInt(title.substring(beginningIndex+4, beginningIndex+6));
                            }
                            title = title.substring(beginningIndex+8);
                        }
                        else{ //tom X
                            seriesVolume = Integer.parseInt(title.substring(beginningIndex+4, beginningIndex + 5));
                            title = title.substring(beginningIndex+7);
                        }
                    }
                    break;
                case 3: author = scanningResult; break;
                case 4: publishYear = Integer.parseInt(scanningResult); break;
                case 5: pages = Integer.parseInt(scanningResult); break;
                case 6: genre = scanningResult; break;
                default: break;

            }
            counter++;
            if(counter == 11){ //scanning next line
                counter = 1;
                Book nextBook = new Book(title, author, pages, publishYear, genre, series, seriesVolume);
                seriesVolume = 0;
                series = null;
                books.add(nextBook);
            }

        }
        sc.close();  //closes the scanner
        return books;
    }

    public static void test() throws FileNotFoundException {
        ArrayList<Book> books = returnBooksFromFile();
        for(Book book: books){
            book.description();
            System.out.println();
        }
    }
}
