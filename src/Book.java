import java.util.HashMap;

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
    public void description(){
        if(series != null){
            System.out.println("series - " + series);
            System.out.println("volume of series - " + seriesVolume);
        }
        System.out.println("title - " + title);
        System.out.println("author - " + author);
        System.out.println("year of publishing - " + publishYear);
        System.out.println("pages - " + pages);
        System.out.println("genre - " + genre);
        System.out.println("rating " + rating);
    }
}
