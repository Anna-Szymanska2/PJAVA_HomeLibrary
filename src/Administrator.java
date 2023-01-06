import java.util.ArrayList;
import java.util.Scanner;
public class Administrator extends User{


    private ArrayList<Reminder> reminders = new ArrayList<>();

    public void addBook(Library library,String description, String title, String author, int pages, int publishYear, String genre, String series, int seriesVolume){
        Book book = new Book(description, title,author,pages,publishYear,genre,series,seriesVolume);
        library.addBook(book);
    }

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }

    public void addReminder(Book borrowedBook, ReminderListener controller){

        Reminder reminder = new Reminder(borrowedBook, controller);
        reminder.setReminder();
        reminders.add(reminder);
    }

    public void displayReminders(){
        for(Reminder reminder: reminders){
            reminder.displayReminder();
        }
    }
    public void setReminders(){
        for(Reminder reminder: reminders){
            reminder.setReminder();
        }
    }
    public void setOrSendReminders(){
        for(Reminder reminder: reminders){
            reminder.sendOrSet();
        }
    }


    public void cancelReminders(){
        for(Reminder reminder: reminders){
            reminder.cancelReminderTimer();
        }
    }

    public void borrowBookConsole(Library library){
        ArrayList<Book> books = library.getBooks();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Teraz będziesz wybierać poźyczaną książkę");
        Book borrowedBook = selectBook(books);
        if(borrowedBook.isBorrowed()){
            System.out.println("Ta książka jest już pożyczona");
            return;
        }
        System.out.println("Jak się nazywa pożyczająca osoba?");
        String borrowerName = scanner.next();
        System.out.println("Wybierz na jak długo chccesz pożyczać książkę 1-tydzień, 2- dwa tygodnie, 3 - miesiąc, 4 - dwa miesiące");
        String chosenTime = scanner.next();
        //addReminder(borrowedBook, controller);
       // library.addBorrowedBook(borrowedBook);
    }

    public void borrowBook(Book book, String borrowerName, String time, Boolean willReminderBeSet, ReminderListener controller){
        book.borrowBook(borrowerName, time);
        if(willReminderBeSet){
            addReminder(book, controller);
        }
        addToBorrowed(book);
    }

    public Reminder returnBooksReminder(Book book){
        for(Reminder reminder: getReminders()){
            if(reminder.getBorrowedBook() == book)
                return reminder;
        }
        return null;
    }

    public User returnBooksBorrower(String name, ArrayList<User> users){
        for(User user: users){
            if(user.getName() == name)
                return user;
        }
        return null;
    }


    public void bookReturnedConsole(Library library){
        Book returnedBook = selectBook(library.getBooks());
        library.removeBorrowedBook(returnedBook);
        Reminder reminderToDelete = null;
        for(Reminder reminder: reminders){
            if(returnedBook == reminder.getBorrowedBook()){
                reminderToDelete = reminder;
                break;
            }
        }
        deleteReminder(reminderToDelete);
    }

    public void deleteReminder(Reminder reminder){
        reminder.cancelReminderTimer();
        reminders.remove(reminder);
    }

    public void bookReturned(Book returnedBook, ArrayList<User> users){
        getBorrowedBooks().remove(returnedBook);
        String borrowerName = returnedBook.getBorrowerName();
        returnedBook.returnBook();

        Reminder reminderToDelete = returnBooksReminder(returnedBook);
        if(reminderToDelete!=null){
            deleteReminder(reminderToDelete);
        }
        User borrower = returnBooksBorrower(borrowerName, users);
        if(borrower!= null)
            borrower.getBorrowedBooks().remove(returnedBook);

    }


    public void postponeReminder(Reminder reminder, String time){
        //reminder.getBorrowedBook().addTimeToReturningDate(time);
        reminder.postponeReminder();
    }
    public void postponeReturningBook(String time, Book book){
        book.addTimeToReturningDate(time);
        Reminder reminder = returnBooksReminder(book);
        if(reminder!=null)
            reminder.postponeReminder();
    }


    public void deleteUser(User user, ArrayList<User> users){
        users.remove(user);
    }

    public String remindPasswordForUser(User user){
        return user.getPassword();
    }

    public Administrator(String name, char[] password, Library library) {

        super(name, password, library);
        library.setAdmin(this);
    }
}
