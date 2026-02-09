public class Main {
    public static void main(String[] args) {

        Book book1 = new Book("1984", "Джордж Орвелл", 1949, 328);
        Book book2 = new Book("Java для початківців", "Герберт Шилдт", 2018, 720);

        System.out.println(book1.getInfo());
        System.out.println("Сучасна книга? " + book1.isModern());

        System.out.println();

        System.out.println(book2.getInfo());
        System.out.println("Сучасна книга? " + book2.isModern());
    }
}
