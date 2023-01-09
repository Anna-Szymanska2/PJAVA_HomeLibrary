package model;

import java.util.ArrayList;

/**
 * Administrator is a class that extends User functions. Thanks to it is possible to add new books to library, borrow
 * and return them and manage reminders.
 */
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
public class Administrator extends User{
    private ArrayList<Reminder> reminders = new ArrayList<>();
    private ArrayList<Reminder> remindersToDelete = new ArrayList<>();

    /**
     * Adds new book to library.
     * @param library library to which book will be added.
     * @param title title of the added book.
     * @param author author of the added book.
     * @param pages pages of the added book.
     * @param publishYear year of publish of the added book.
     * @param genre genre of the added book.
     * @param series series of the added book.
     * @param seriesVolume volume of series of the added book.
     * @throws SimilarBookException is thrown when there is a book with the same characteristics already in the library.
     */
    public void addBook(Library library, String title, String author, int pages, int publishYear, String genre, String series, int seriesVolume) throws SimilarBookException {

        String description = "";
        if(series != null){
            description = series + ", " + "tom " + seriesVolume + ", ";
        }
        description = description + title;
        Book book = new Book(description, title,author,pages,publishYear,genre,series,seriesVolume);
        for(Book bookLibrary: library.getBooks()){
            if(bookLibrary.equals(book))
                throw new SimilarBookException("Taka książka jest już w bibliotece");
        }
        library.addBook(book);
        Comparator<Book> c = (b1, b2) -> b1.getAuthor().compareTo(b2.getAuthor());
        library.getBooks().sort(c);
    }

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }

    public ArrayList<Reminder> getRemindersToDelete() {
        return remindersToDelete;
    }

    /**
     * Creates new reminder and adds it to the list of them.
     * @param borrowedBook book of which returning reminder will remind.
     * @param controller listener of reminder.
     */
    public void addReminder(Book borrowedBook, ReminderListener controller){

        Reminder reminder = new Reminder(borrowedBook, controller);
        reminder.setReminder();
        reminders.add(reminder);
    }

    /**
     * Sends or sets all reminders and deletes reminders.
     */
    public void setOrSendReminders(){
        for(Reminder reminder: reminders){
            reminder.sendOrSet();
        }
       deleteReminders();
    }

    /**
     * Deletes all reminders from lists of reminders to delete.
     */
    public void deleteReminders(){
        for(Reminder reminderToDelete: remindersToDelete){
            reminders.remove(reminderToDelete);
            reminderToDelete.cancelReminderTimer();

        }
        remindersToDelete.clear();
    }

    /**
     * Cancels timers of all reminders.
     */
    public void cancelReminders(){
        for(Reminder reminder: reminders){
            reminder.cancelReminderTimer();
        }
    }

    /**
     * Adds book to list of borrowed books and adds reminder if it was specified.
     * @param book book that is borrowed.
     * @param borrowerName name of borrower.
     * @param time how long from current date book should be returned.
     * @param willReminderBeSet flag whether reminder should be set or not.
     * @param controller listener of reminder.
     */
    public void borrowBook(Book book, String borrowerName, String time, Boolean willReminderBeSet, ReminderListener controller){
        book.borrowBook(borrowerName, time);
        if(willReminderBeSet){
            addReminder(book, controller);
        }
        addToBorrowed(book);
    }

    /**
     * Checks whether at list of reminders is reminder with specific borrowed boo and if so it returns this reminder.
     * @param book specific borrowed book.
     * @return reminder with specific book or null if it wasn't found.
     */
    public Reminder returnBooksReminder(Book book){
        for(Reminder reminder: getReminders()){
            if(reminder.getBorrowedBook() == book)
                return reminder;
        }
        return null;
    }

    /**
     * Checks whether at list is user with specific name and if so it returns this user.
     * @param name name of searched user.
     * @param users list of users that it is being searched.
     * @return user with specific name or null if it wasn't found.
     */
    public User returnUserOfThisName(String name, ArrayList<User> users){
        for(User user: users){
            if(user.getName().equals(name))
                return user;
        }
        return null;
    }

    /**
     * Deletes book from administrator list of borrowed books and from borrower list. If reminder was set it is canceled.
     * @param returnedBook book that was deleted from lists.
     * @param users list of users from which borrower is found.
     */
    public void bookReturned(Book returnedBook, ArrayList<User> users){
        getBorrowedBooks().remove(returnedBook);
        String borrowerName = returnedBook.getBorrowerName();
        returnedBook.returnBook();

        Reminder reminderToDelete = returnBooksReminder(returnedBook);
        if(reminderToDelete!=null){
            remindersToDelete.add(reminderToDelete);
            reminderToDelete.cancelReminderTimer();
        }
        User borrower = returnUserOfThisName(borrowerName, users);
        if(borrower!= null)
            borrower.getBorrowedBooks().remove(returnedBook);

    }

    /**
     * Changes returning date of the book and postpone its reminder.
     * @param time time that will be added to returning date.
     * @param book book which return will be postponed.
     */
    public void postponeReturningBook(String time, Book book){
        book.addTimeToReturningDate(time);
        Reminder reminder = returnBooksReminder(book);
        if(reminder!=null)
            reminder.postponeReminder();
    }

    /**
     * Deletes user of ArrayList of users.
     * @param user user that will be deleted.
     * @param users ArrayList of users from which user will be deleted.
     */
    public void deleteUser(User user, ArrayList<User> users){
        users.remove(user);
    }

    /**
     * Constructor that create administrator object using its base class constructor and sets it as a library admin.
     * @param name name of administrator.
     * @param password password of administrator.
     * @param library library which they manage.
     */
    public Administrator(String name, char[] password, Library library) {

        super(name, password, library);
        library.setAdmin(this);
    }
}
