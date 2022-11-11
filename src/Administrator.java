public class Administrator extends User{

    public void addBook(Library library,String title, String author, int pages, int publishYear, String genre, String series, int seriesVolume){
        Book book = new Book(title,author,pages,publishYear,genre,series,seriesVolume);
        library.addBook(book);
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
