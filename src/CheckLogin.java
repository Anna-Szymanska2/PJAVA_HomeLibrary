
public class CheckLogin {
    public static User checkLogin(String name, String password, Library library) {
        User loggedUser = null;
        if (library.namesAndPasswords.containsKey(name)) {
            if (library.namesAndPasswords.get(name).equals(password)) {
                System.out.println("Logowanie przebiegło pomyślnie jako: " + name);
                int i = 0;
                for (User u : library.users) {
                    if (u.getName().equals(name)) {
                        loggedUser = library.users.get(i);
                    }
                    i++;
                }
            } else
                System.out.println("Błędne hasło");
        } else
            System.out.println("Użytkownik o takim nicku nie istnieje");
        return loggedUser;
    }
}
