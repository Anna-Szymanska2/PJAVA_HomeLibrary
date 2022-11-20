import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Reminder implements Serializable{

    Calendar borrowingDate;
    Calendar returningDate;
    String borrowerName;
    Book borrowedBook;
    transient Timer timer;
    transient TimerTask task;
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'o' HH:mm");

    public Reminder(String borrowerName, Book borrowedBook, int time){
        this.borrowedBook = borrowedBook;
        this.borrowerName = borrowerName;
        borrowingDate = Calendar.getInstance();
        returningDate = Calendar.getInstance();
        addTimeToReturningDate(time);
        borrowedBook.setReturningDate(returningDate);
        borrowedBook.setBorrowed(true);

    }

    public Book getBorrowedBook() {
        return borrowedBook;
    }

    public void sendReminderMessage(){
        System.out.println("POWIADOMIENIE");
        System.out.println(dateFormat.format(returningDate.getTime()) + " " + borrowerName + " powinien zwrócić Ci niżej wspomnianą książkę");
        borrowedBook.description();
        System.out.println();
    }

    public void setReminder(){
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                sendReminderMessage();
                timer.cancel();
            }
        };
        timer.schedule(task,returningDate.getTime());
    }
    public void displayReminder(){
        System.out.println("Data zwrotu: " + dateFormat.format(returningDate.getTime()));
        System.out.println("Kto pożyczył: " + borrowerName);
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
            sendReminderMessage();
        else
            setReminder();

    }

    public void postponeReminder(int time){
        cancelReminderTimer();
        addTimeToReturningDate(time);
        setReminder();
        borrowedBook.setReturningDate(returningDate);
    }

    public void addTimeToReturningDate(int time){
        switch(time){
            case 1 -> returningDate.add(Calendar.DAY_OF_YEAR, 7);
            case 2 -> returningDate.add(Calendar.DAY_OF_YEAR, 14);
            case 3 -> returningDate.add(Calendar.MONTH, 1);
            case 4 -> returningDate.add(Calendar.MONTH, 2);
        }

    }

    public void cancelReminderTimer(){
        timer.cancel();
    }

    public static void main(String []arg) throws FileNotFoundException {
        test();

    }

    public static void test() throws FileNotFoundException {
        ArrayList<Book> books = FileLoader.returnBooksFromFile();
        Library library = new Library(books);
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
