public class Book {
    String title ;
    String author;
    int pages;
    int publishYear;
    String genre;
    String series ;
    int seriesVolume;
    double rating;


    public Book(String title, String author, int pages, int publishYear, String genre, String series, int seriesVolume){
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishYear = publishYear;
        this.genre = genre;
        this.series = series;
        this.seriesVolume = seriesVolume;
        this.rating = 7;
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
    }
}
