import java.util.HashMap;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Book {
    private final String description;
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


    public Book(String description, String title, String author,  int pages, int publishYear, String genre, String series, int seriesVolume){
        this.description = description;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishYear = publishYear;
        this.genre = genre;
        this.series = series;
        this.seriesVolume = seriesVolume;
        this.calculateRating();
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public String getGenre() {
        return genre;
    }

    public String getSeries() {
        return series;
    }

    public int getSeriesVolume() {
        return seriesVolume;
    }

    public double getRating() {
        return rating;
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

    public String returnLongDescription(){
        String longDescription = "";
        if(series != null){
            longDescription = "<html>seria - " + series + "<br/>";
            longDescription = longDescription + "tom - " + seriesVolume +"<br/>";
            longDescription = longDescription + "tytuł - " + title +"<br/>";
        }
        else
            longDescription = longDescription + "<html>tytuł - " + title +"<br/>";

        longDescription = longDescription + "autor - " + author +"<br/>";
        longDescription = longDescription + "rok wydania - " + publishYear +"<br/>";
        longDescription = longDescription + "liczba stron - " + pages +"<br/>";
        longDescription = longDescription + "gatunek - " + genre+"<br/>";

        if(returningDate != null){
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            longDescription = longDescription + "Książka jest chwilowo pożyczona, data zwrotu: " + dateFormat.format(returningDate.getTime())+"</html>";
        }
    return longDescription;
    }

    /*@Override
    public String toString(){
        String decription = "";
        if(series != null){
            decription = series + " " + "tom " + seriesVolume;
        }
        decription = decription +  " " + title + " " + author + " " + publishYear + " " + pages + " " + genre;

        return decription;
    }*/
    @Override
    public String toString(){
        return description;
    }
}
