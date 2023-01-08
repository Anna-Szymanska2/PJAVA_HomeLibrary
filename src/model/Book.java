package model;

import java.util.HashMap;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A Book class represents books stored in the library. Books can be used
 * to perform various actions within library management program.
 */
public class Book implements Serializable{
    private final String description;
    private final String title ;
    private final String author;
    private final int pages;
    private final int publishYear;
    private final String genre;
    private final String series ;
    private final int seriesVolume;
    /**
     * Book rating calculated from all ratings added by users.
     */
    private double rating;
    private String borrowerName;
    /**
     * Storing all ratings added to the book by users with username attached to them.
     */
    private HashMap<String,Integer> ratings = new HashMap<>();
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

    /**
     * Sets name of borrower and sets date of returning as a date distant specific time from the current date.
     * @param borrowerName name of borrower.
     * @param time time for which book is borrowed.
     */
    public void borrowBook(String borrowerName, String time){
        this.borrowerName = borrowerName;
        returningDate = Calendar.getInstance();
        addTimeToReturningDate(time);
        setBorrowed(true);
    }

    /**
     * Method calculates rating of the book from all individual ratings added by users.
     * Each book has 7 added to the sum of ratings, which represents rating added by owner of the book collection.
     * Rating is calculated as an average of added ratings.
     */
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
    public boolean isBorrowed() {
        return isBorrowed;
    }


    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public void setReturningDate(Calendar returningDate) {
        this.returningDate = returningDate;
    }

    /**
     * Sets arguments of book in that way that they indicate that book is nor borrowed.
     */
    public void returnBook(){
        setBorrowed(false);
        returningDate = null;
        borrowerName = null;
    }

    /**
     * Adds specific amount of time to the returning date. Before adding it checks whether the returning date is in the
     * past if so it sets it as the current date.
     * @param time time that is added.
     */
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

    /**
     * Returns long description of the book.
     * @return description and values of most attributes of the book.
     */
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

    @Override
    public String toString(){
        return description;
    }

    /**
     * Checks whether two books are the same.
     * @param o compared book.
     * @return result of comparison.
     */
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


