public class Book {

    String title;
    String author;
    int year;
    int pages;

    public Book(String title, String author, int year, int pages) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    public String getInfo() {
        return "Назва: " + title +
               ", Автор: " + author +
               ", Рік видання: " + year +
               ", Сторінок: " + pages;
    }

    public boolean isModern() {
        return year > 2010;
    }
}
