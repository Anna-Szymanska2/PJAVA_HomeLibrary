package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Reminder is a class that enables to operate on reminders that remind about returning books.
 * Thanks to it is possible to set, postpone and cancel reminders. It informs its listeners that reminders time is up
 * and book should be returned.
 */
public class Reminder implements Serializable{
    private Calendar returningDate;
    private Book borrowedBook;
    /**
     * Timer that counts time till returning date.
     */
    transient Timer timer;
    /**
     * Task that sends info that time for returning book is up.
     */
    transient TimerTask task;
    /**
     * Controller is a reminder listener that gets info that time for returning book is up.
     */
    transient private ReminderListener controller;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'o' HH:mm");

    /**
     * Constructor of reminder.
     * @param borrowedBook book of which returning reminder reminds.
     * @param controller listener that gets info that time for returning book is up.
     */
    public Reminder(Book borrowedBook, ReminderListener controller){
        this.borrowedBook = borrowedBook;
        this.controller = controller;
        returningDate = borrowedBook.getReturningDate();
    }

    public Book getBorrowedBook() {
        return borrowedBook;
    }

    public void setController(ReminderListener controller) {
        this.controller = controller;
    }

    /**
     * Informs listener that time of returning book is up.
     */
    public void sendReminder(){
        controller.reminderSendAction(this);
    }

    /**
     * Creates new timer and timertask which count time till returning book date and when it's up informs listener.
     */
    public void setReminder(){
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                sendReminder();
                timer.cancel();
            }
        };
        timer.schedule(task,returningDate.getTime());
    }

    /**
     * Checks whether current date is after the date of returning book.
     * @return true when current date is  after the date of returning book, false otherwise.
     */
    public boolean isTimeUp(){
        Calendar currentDate = Calendar.getInstance();
        return currentDate.compareTo(returningDate) >= 0;
    }

    /**
     * Checks whether current date is after the date of returning book and if so sends info to listener otherwise
     * it sets reminder.
     */

    public void sendOrSet(){
        if(isTimeUp())
            sendReminder();
        else
            setReminder();

    }

    /**
     * Cancels reminder timer, changes returning date and sets reminder again.
     */
    public void postponeReminder(){
        cancelReminderTimer();
        returningDate = borrowedBook.getReturningDate();
        setReminder();
    }

    /**
     * Cancels reminder timer if it was assigned previously.
     */
    public void cancelReminderTimer(){
        if(timer!= null)
            timer.cancel();
    }

    /**
     * Returns info about reminder's book.
     * @return description of borrowed book.
     */
    @Override
    public String toString(){
        return borrowedBook.getDescription();
    }

    /**
     * Returns detail of reminder.
     * @return message about time of returning book, borrower name and short description of the book .
     */
    public String returnReminderMessage(){
        String message = dateFormat.format(returningDate.getTime()) + " książka " + borrowedBook.toString() + " powinna zostać zwrócona przez " +
                borrowedBook.getBorrowerName();
        return message;
    }

}
