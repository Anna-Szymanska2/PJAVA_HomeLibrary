import java.io.FileNotFoundException;
import java.io.IOException;
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
     * Task - sends info that time for returning book is up.
     */
    transient TimerTask task;
    /**
     * Controller is a reminder listener that gets info that time for returning book is up.
     */
    transient private ReminderListener controller;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'o' HH:mm");

    public Reminder( Book borrowedBook, ReminderListener controller){
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

    public void sendReminder(){
        controller.reminderSendAction(this);
    }


    public void setReminder(){
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                //sendReminderMessageConsole();
                sendReminder();
                timer.cancel();
            }
        };
        timer.schedule(task,returningDate.getTime());
    }
    public void displayReminder(){
        System.out.println("Data zwrotu: " + dateFormat.format(returningDate.getTime()));
        System.out.println("Kto pożyczył: " + borrowedBook.getBorrowerName());
        System.out.println("Książka: ");
        borrowedBook.description();
        System.out.println();
    }

    public boolean isTimeUp(){
        Calendar currentDate = Calendar.getInstance();
        return currentDate.compareTo(returningDate) >= 0;
    }


    public void sendOrSet(){
        if(isTimeUp())
            sendReminder();
        else
            setReminder();

    }


    public void postponeReminder(){
        cancelReminderTimer();
        returningDate = borrowedBook.getReturningDate();
        setReminder();
    }

   /* public void addTimeToReturningDate(String time){
        switch(time){
            case "tydzień" -> returningDate.add(Calendar.DAY_OF_YEAR, 7);
            case "2 tygodnie" -> returningDate.add(Calendar.DAY_OF_YEAR, 14);
            case "miesiąc" -> returningDate.add(Calendar.MONTH, 1);
            case "2 miesiące" -> returningDate.add(Calendar.MONTH, 2);
        }

    }*/

    public void cancelReminderTimer(){
        if(timer!= null)
            timer.cancel();
    }
    @Override
    public String toString(){
        return borrowedBook.getDescription();
    }
    public String returnReminderMessage(){
        String message = dateFormat.format(returningDate.getTime()) + " książka " + borrowedBook.toString() + " powinna zostać zwrócona przez " +
                borrowedBook.getBorrowerName();
        return message;
    }

    public static void main(String []arg) throws FileNotFoundException {
        //test();

    }

    public static void test() throws IOException {
        //ArrayList<Book> books = FileLoader.returnBooksFromFile();
        //Library library = new Library(books);
        /*Administrator admin = new Administrator("ania", "haslo");
        //admin.borrowBook(library);
        //admin.borrowBook(library);
        admin.displayReminders();
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToRead(library.getBooks());
        admin.chooseAndAddToRead(library.getBooks());
        admin.chooseAndAddToRead(library.getBooks());


        SaveRestoreData.save(admin);*/
        //Scanner scanner = new Scanner(System.in);
        //scanner.next();


        Administrator admin = SaveRestoreData.restore();
        admin.displayReminders();
        admin.setReminders();
        admin.displayReminders();
        admin.cancelReminders();
        admin.displayBooksToRead();
        admin.displayReadBooks();
        //ArrayList<Reminder> reminders = admin.getReminders();
        //admin.postponeReminder(reminders.get(0), 1);
        //admin.bookReturned(library);


    }
}
