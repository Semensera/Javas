import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// 1. Клас Автор (існує незалежно від книг)
class Author {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Author(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAuthorInfo() {
        return getFullName() + " (нар. " + birthDate.getYear() + ")";
    }
}

// 2. Базовий клас Книга (Агрегація об'єкта Author)
class Book {
    private String title;
    private Author author; // Агрегація: книга має автора, але автор існує окремо
    private int publicationYear;

    public Book(String title, Author author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    // Базовий метод для отримання інформації, який буде перевизначено
    public String getBookInfo() {
        return String.format("Книга: '%s', Автор: %s, Рік: %d", 
                title, author.getAuthorInfo(), publicationYear);
    }
}

// 3. Клас-нащадок ЕлектроннаКнига (Успадкування)
class EBook extends Book {
    private String fileFormat;
    private double fileSizeMB;

    public EBook(String title, Author author, int publicationYear, String fileFormat, double fileSizeMB) {
        super(title, author, publicationYear); // Виклик конструктора базового класу
        this.fileFormat = fileFormat;
        this.fileSizeMB = fileSizeMB;
    }

    // Перевизначення методу базового класу (Поліморфізм)
    @Override
    public String getBookInfo() {
        return super.getBookInfo() + String.format(" | Формат: %s, Розмір: %.1f МБ (Електронна версія)", 
                fileFormat, fileSizeMB);
    }
}

// 4. Клас Відділ (Агрегація книг у колекцію)
class Department {
    private String name;
    private List<Book> books; // Колекція для зберігання книг

    public Department(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    public void displayDepartmentInfo() {
        System.out.println("\n--- Відділ: " + name + " ---");
        if (books.isEmpty()) {
            System.out.println("Відділ порожній.");
        } else {
            for (Book book : books) {
                // Поліморфний виклик: для EBook викличеться перевизначений метод
                System.out.println(book.getBookInfo());
            }
        }
    }
}

// 5. Головний клас Бібліотека (Агрегація відділів)
class Library {
    private String name;
    private String address;
    private int foundationYear;
    private List<Department> departments; // Колекція відділів

    public Library(String name, String address, int foundationYear) {
        this.name = name;
        this.address = address;
        this.foundationYear = foundationYear;
        this.departments = new ArrayList<>();
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    // Метод-помічник для додавання книги одразу в певний відділ
    public void addBookToDepartment(String departmentName, Book book) {
        for (Department dept : departments) {
            if (dept.getName().equalsIgnoreCase(departmentName)) {
                dept.addBook(book);
                System.out.println("Книгу '" + book.getTitle() + "' успішно додано у відділ '" + dept.getName() + "'.");
                return;
            }
        }
        System.out.println("Помилка: Відділ '" + departmentName + "' не знайдено.");
    }

    public void displayLibraryInfo() {
        System.out.println("=========================================");
        System.out.println("Бібліотека: " + name);
        System.out.println("Адреса: " + address + " | Заснована: " + foundationYear + " р.");
        System.out.println("=========================================");
        
        for (Department dept : departments) {
            dept.displayDepartmentInfo();
        }
    }
}

// 6. Клас для тестування (Main)
public class Main {
    public static void main(String[] args) {
        // Створення авторів
        Author author1 = new Author("Артур Конан", "Дойл", LocalDate.of(1859, 5, 22));
        Author author2 = new Author("Айзек", "Азімов", LocalDate.of(1920, 1, 2));

        // Створення книг (звичайних та електронних)
        Book book1 = new Book("Пригоди Шерлока Холмса", author1, 1892);
        Book book2 = new Book("Фундація", author2, 1951);
        EBook ebook1 = new EBook("Я, робот", author2, 1950, "EPUB", 2.5);
        EBook ebook2 = new EBook("Собака Баскервілів", author1, 1902, "PDF", 5.1);

        // Створення відділів
        Department detectiveDept = new Department("Детективи");
        Department sciFiDept = new Department("Наукова фантастика");

        // Створення бібліотеки
        Library centralLibrary = new Library("Головна Міська Бібліотека", "вул. Наукова, 10", 1905);

        // Додавання відділів до бібліотеки
        centralLibrary.addDepartment(detectiveDept);
        centralLibrary.addDepartment(sciFiDept);

        // Розподіл книг по відділах через методи бібліотеки
        centralLibrary.addBookToDepartment("Детективи", book1);
        centralLibrary.addBookToDepartment("Детективи", ebook2);
        centralLibrary.addBookToDepartment("Наукова фантастика", book2);
        centralLibrary.addBookToDepartment("Наукова фантастика", ebook1);

        // Виведення загальної інформації (демонстрація ієрархії та поліморфізму)
        System.out.println();
        centralLibrary.displayLibraryInfo();
    }
}
