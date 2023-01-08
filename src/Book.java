import java.util.HashMap;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Book implements Serializable{
    private final String description;
    private final String title ;
    private final String author;
    private final int pages;
    private final int publishYear;
    private final String genre;
    private final String series ;
    private final int seriesVolume;
    private double rating;
    private String borrowerName;
    private HashMap<String,Integer> ratings;
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

    public HashMap<String, Integer> getRatings() {
        return ratings;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public Calendar getReturningDate() {
        return returningDate;
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

    public void borrowBook(String borrowerName, String time ){
        this.borrowerName = borrowerName;
        returningDate = Calendar.getInstance();
        addTimeToReturningDate(time);
        setBorrowed(true);
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

    public void returnBook(){
        setBorrowed(false);
        returningDate = null;
        borrowerName = null;
    }

    public void addTimeToReturningDate(String time){
        Calendar currentDate = Calendar.getInstance();
        if(currentDate.compareTo(returningDate) >= 0){
            returningDate = currentDate;
        }

        switch(time){
            case "tydzień" -> returningDate.add(Calendar.DAY_OF_YEAR, 7);
            case "2 tygodnie" -> returningDate.add(Calendar.DAY_OF_YEAR, 14);
            case "miesiąc" -> returningDate.add(Calendar.MONTH, 1);
            case "2 miesiące" -> returningDate.add(Calendar.MONTH, 2);
        }

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
            longDescription = "<html>Seria - " + series + "<br/>";
            longDescription = longDescription + "Tom - " + seriesVolume +"<br/>";
            longDescription = longDescription + "Tytuł - " + title +"<br/>";
        }
        else
            longDescription = longDescription + "<html>Tytuł - " + title +"<br/>";

        longDescription = longDescription + "Autor - " + author +"<br/>";
        longDescription = longDescription + "Rok wydania - " + publishYear +"<br/>";
        longDescription = longDescription + "Liczba stron - " + pages +"<br/>";
        longDescription = longDescription + "Gatunek - " + genre+"<br/>";
        longDescription = longDescription + "Ocena - " + rating +"<br/>";

        if(returningDate != null){
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            longDescription = longDescription + "Książka jest chwilowo pożyczona przez " + getBorrowerName()+"<br/>";
            longDescription = longDescription + "Data zwrotu: " + dateFormat.format(returningDate.getTime())+"</html>";
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

    @Override
    public boolean equals(Object o){
        Book book = (Book) o;
        if(!book.getTitle().equals(title))
            return false;
        if(!book.getAuthor().equals(author))
            return false;
        if(book.getPages() != pages)
            return false;
        if(book.getPublishYear() != publishYear)
            return false;
        if(!book.getGenre().equals(genre))
            return false;

        if(book.getSeries() != null){
            if(!book.getSeries().equals(series))
                return false;
            if(book.getSeriesVolume() != seriesVolume)
                return false;
        }

        return true;
    }
}


