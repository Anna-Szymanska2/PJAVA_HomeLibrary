import java.util.HashMap;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Book {
    String title ;
    String author;
    int pages;
    int publishYear;
    String genre;
    String series ;
    int seriesVolume;
    double rating;
    HashMap<String,Integer> ratings;
    private boolean isBorrowed = false;
    private Calendar returningDate = null;


    public Book(String title, String author, int pages, int publishYear, String genre, String series, int seriesVolume){
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishYear = publishYear;
        this.genre = genre;
        this.series = series;
        this.seriesVolume = seriesVolume;
        this.calculateRating();
    }

    public void calculateRating(){
        double ratingsSum = 7;
        if(ratings!=null) {
            for (Integer i : ratings.values()) {
                ratingsSum += i;
            }
            this.rating = ratingsSum / (ratings.size() + 1);
        }
        else
            this.rating = ratingsSum;
    }
    // just for testing
    public boolean isBorrowed() {
        return isBorrowed;
    }


    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public void setReturningDate(Calendar returningDate) {
        this.returningDate = returningDate;
    }

    // just for testing
    public void description(){
        if(series != null){
            System.out.println("seria - " + series);
            System.out.println("tom - " + seriesVolume);
        }
        System.out.println("tytuł - " + title);
        System.out.println("autor - " + author);
        System.out.println("rok wydania - " + publishYear);
        System.out.println("liczba stron - " + pages);
        System.out.println("gatunek - " + genre);

        if(returningDate != null){
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            System.out.println("Książka jest chwilowo pożyczona, data zwrotu: " + dateFormat.format(returningDate.getTime()));
        }
    }

    @Override
    public String toString(){
        String decription = "";
        if(series != null){
            decription = series + " " + "tom " + seriesVolume;
        }
        decription = decription +  " " + title + " " + author + " " + publishYear + " " + pages + " " + genre;

        return decription;
    }
}
