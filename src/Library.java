import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class Library {
    ArrayList<Book> books;
    ArrayList<User> users = new ArrayList<>();
    HashMap<String,String> namesAndPasswords = new HashMap<>();
    public Library(ArrayList<Book> books){
        this.books = books;
    }

    public void addBook(Book book){
        this.books.add(book);
    }

    public void removeBook(Book book){
        this.books.remove(book);
    }

    public void filtration(String title, String author, int pages, int pages2, int publishYear, int publishYear2, String genre, String series, int volumes,int volumes2, int rating, int rating2) {
        ArrayList<Book> booksToFilter = this.books;
        ArrayList<Book> booksAfterFiltration = new ArrayList<>();
        if(!title.equals("0")){
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> book.title.equals(title))
                    .collect(Collectors.toList());
            while(booksToFilter.size()>booksAfterFiltration.size()){
                booksToFilter.remove(booksToFilter.size()-1);
            }
            Collections.copy(booksToFilter,booksAfterFiltration);
        }
        if(!author.equals("0")){
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> book.author.equals(author))
                    .collect(Collectors.toList());
            while(booksToFilter.size()>booksAfterFiltration.size()){
                booksToFilter.remove(booksToFilter.size()-1);
            }
            Collections.copy(booksToFilter,booksAfterFiltration);
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
            while(booksToFilter.size()>booksAfterFiltration.size()){
                booksToFilter.remove(booksToFilter.size()-1);
            }
            Collections.copy(booksToFilter,booksAfterFiltration);
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
            while(booksToFilter.size()>booksAfterFiltration.size()){
                booksToFilter.remove(booksToFilter.size()-1);
            }
            Collections.copy(booksToFilter,booksAfterFiltration);
        }
        if(!genre.equals("0")){
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> book.genre.equals(genre))
                    .collect(Collectors.toList());
            while(booksToFilter.size()>booksAfterFiltration.size()){
                booksToFilter.remove(booksToFilter.size()-1);
            }
            Collections.copy(booksToFilter,booksAfterFiltration);
        }
        if(!series.equals("0")){
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> Objects.equals(book.series, series))
                    .collect(Collectors.toList());
            while(booksToFilter.size()>booksAfterFiltration.size()){
                booksToFilter.remove(booksToFilter.size()-1);
            }
            Collections.copy(booksToFilter,booksAfterFiltration);
        }
        if(volumes2 != 0 || volumes != 0){
            ArrayList<String> namesOfFittingSeries = new ArrayList<>();
            if(volumes == 0){
                booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                        .filter(book -> book.series == null)
                        .collect(Collectors.toList());
                volumes = 1;
                while(booksToFilter.size()>booksAfterFiltration.size()){
                    booksToFilter.remove(booksToFilter.size()-1);
                }
                Collections.copy(booksToFilter,booksAfterFiltration);
            }
            for(Book bookAfter: booksToFilter){
                if(bookAfter.series != null) {
                    if (bookAfter.seriesVolume < volumes2) {
                        namesOfFittingSeries.add(bookAfter.series);
                        if (bookAfter.seriesVolume > volumes) {
                            namesOfFittingSeries.add(bookAfter.series);
                        }
                    }
                }
            }
            System.out.println(namesOfFittingSeries);
            booksAfterFiltration = (ArrayList<Book>) booksToFilter.stream()
                    .filter(book -> namesOfFittingSeries.contains(book.series))
                    .collect(Collectors.toList());
            while(booksToFilter.size()>booksAfterFiltration.size()){
                booksToFilter.remove(booksToFilter.size()-1);
            }
            Collections.copy(booksToFilter,booksAfterFiltration);
        }
        if(rating != 0 || rating2 !=0){
            for(Book bookAfter: booksToFilter){
                if(bookAfter.rating>rating2) {
                    booksAfterFiltration.remove(bookAfter);
                }
                if(bookAfter.rating<rating){
                    booksAfterFiltration.remove(bookAfter);
                }
            }
            while(booksToFilter.size()>booksAfterFiltration.size()){
                booksToFilter.remove(booksToFilter.size()-1);
            }
            Collections.copy(booksToFilter,booksAfterFiltration);
        }
        System.out.println("Twoje wybrane książki: ");
        for(Book bookAfter: booksToFilter){
            bookAfter.description();
        }
        System.out.println();
    }
}
