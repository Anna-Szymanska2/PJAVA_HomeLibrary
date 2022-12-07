import java.util.ArrayList;
import java.util.Scanner;
public class Administrator extends User{
    ArrayList<Reminder> reminders = new ArrayList<>();

    public void addBook(Library library,String title, String author, int pages, int publishYear, String genre, String series, int seriesVolume){
        Book book = new Book(title,author,pages,publishYear,genre,series,seriesVolume);
        library.addBook(book);
    }

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }

    public void addReminder(String borrowerName, Book borrowedBook, int time){

        Reminder reminder = new Reminder(borrowerName, borrowedBook,time);
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

    public void cancelReminders(){
        for(Reminder reminder: reminders){
            reminder.cancelReminderTimer();
        }
    }

    public void borrowBook(Library library){
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
        int chosenNumber = scanner.nextInt();
        addReminder(borrowerName, borrowedBook, chosenNumber);
        library.addBorrowedBook(borrowedBook);
    }

    public void bookReturned(Library library){
        Book returnedBook = selectBook(library.getBooks());
        library.removeBorrowedBook(returnedBook);
        Reminder reminderToDelete = null;
        for(Reminder reminder: reminders){
            if(returnedBook == reminder.getBorrowedBook()){
                reminderToDelete = reminder;
                break;
            }
        }
        reminderToDelete.cancelReminderTimer();
        reminders.remove(reminderToDelete);
    }


    public void postponeReminder(Reminder reminder, int time){
        reminder.postponeReminder(time);
    }


    public void deleteUser(User user){
        user = null;
    }

    public String remindPasswordForUser(User user){
        return user.getPassword();
    }

    public Administrator(String name, String password) {
        super(name, password);
    }
}
