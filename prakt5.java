import java.util.ArrayList;
import java.util.List;

// існує незалежно від книг
class Author {
    private String name;
    private int birthYear;

    public Author(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return "Ім'я: " + name + ", Рік народження: " + birthYear;
    }
}

// містить посилання на Author, але Author може існувати і без цієї книги
class Book {
    private String title;
    private Author author; // Агрегація
    private int year;
    private String annotation;

    // анотація
    public Book(String title, Author author, int year, String annotation) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.annotation = annotation;
    }

    //  без анотації 
    public Book(String title, Author author, int year) {
        this(title, author, year, "");
    }

    public Author getAuthor() {
        return author;
    }

    public String getInfo() {
        String baseInfo = "Назва: " + title + ", Рік видання: " + year + ", Автор: " + author.getName();
        // якщо анотація не порожня і не null, додаємо її з нового рядка
        if (annotation != null && !annotation.trim().isEmpty()) {
            return baseInfo + "\n" + annotation;
        }
        return baseInfo;
    }
}

// містить список книг і керує їх життєвим циклом у своєму контексті
class Library {
    private String name;
    private List<Book> books; // композиція

    public Library(String name) {
        this.name = name;
        // Бібліотека створює свій список книг. Якщо об'єкт Library буде знищено (наприклад, збирачем сміття), 
        // посилання на цей список також зникне.
        this.books = new ArrayList<>(); 
    }

    // додавання книги до бібліотеки
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Книгу '" + book.getInfo().split(",")[0].substring(7) + "' додано до бібліотеки.");
    }

    //список всіх книг
    public void listBooks() {
        System.out.println("\n--- Список книг у бібліотеці '" + name + "' ---");
        if (books.isEmpty()) {
            System.out.println("Бібліотека порожня.");
            return;
        }
        for (Book book : books) {
            System.out.println(book.getInfo());
            System.out.println("-".repeat(30)); // для кращого читання
        }
    }

    // пошук книг за ім'ям автора
    public void findBooksByAuthor(String authorName) {
        System.out.println("\n--- Результати пошуку для автора: " + authorName + " ---");
        boolean found = false;
        for (Book book : books) {
            // перевіряємо збіг імені автора (ігноруючи регістр)
            if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
                System.out.println(book.getInfo());
                System.out.println("-".repeat(30));
                found = true;
            }
        }
        if (!found) {
            System.out.println("Книг цього автора не знайдено.");
        }
    }
}

//клас для тестування
public class Main {
    public static void main(String[] args) {
        // 1. створюємо авторів (Вони існують самостійно)
        Author author1 = new Author("Джордж Орвелл", 1903);
        Author author2 = new Author("Сергій Жадан", 1974);

        System.out.println("--- Інформація про авторів ---");
        System.out.println(author1.getInfo());
        System.out.println(author2.getInfo() + "\n");

        // 2. створюємо книги, передаючи їм посилання на об'єкти авторів (Агрегація)
        Book book1 = new Book("1984", author1, 1949, "Антиутопія про тоталітарне суспільство.");
        Book book2 = new Book("Колгосп тварин", author1, 1945); // без анотації
        Book book3 = new Book("Інтернат", author2, 2017, "Роман про життя вчителя в умовах війни на Донбасі.");

        // 3. створюємо бібліотеку (Композиція)
        Library myLibrary = new Library("Центральна міська бібліотека");

        // додаємо книги до бібліотеки
        myLibrary.addBook(book1);
        myLibrary.addBook(book2);
        myLibrary.addBook(book3);

        // 4. виводимо список усіх книг
        myLibrary.listBooks();

        // 5. виконуємо пошук за автором
        myLibrary.findBooksByAuthor("Джордж Орвелл");
        myLibrary.findBooksByAuthor("Тарас Шевченко"); // Перевірка пошуку неіснуючого автора
    }
}
