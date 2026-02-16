public class Main {

    // Клас Car
    static class Car implements AutoCloseable {

        private String brand;
        private String model;
        private int year;
        private double mileage;

        // 1. Стандартний конструктор
        public Car() {
            this.brand = "Unknown";
            this.model = "Unknown";
            this.year = 0;
            this.mileage = 0.0;
            System.out.println("Створено автомобіль (стандартний конструктор)");
        }

        // 2. Параметризований конструктор
        public Car(String brand, String model, int year, double mileage) {
            this.brand = brand;
            this.model = model;
            this.year = year;
            this.mileage = mileage;
            System.out.println("Створено автомобіль (параметризований конструктор)");
        }

        // 3. Копіювальний конструктор
        public Car(Car other) {
            this.brand = other.brand;
            this.model = other.model;
            this.year = other.year;
            this.mileage = other.mileage;
            System.out.println("Створено автомобіль (копіювальний конструктор)");
        }

        // Метод для виводу інформації
        public String getInfo() {
            return "Марка: " + brand +
                    ", Модель: " + model +
                    ", Рік: " + year +
                    ", Пробіг: " + mileage + " км.";
        }

        // Аналог деструктора
        @Override
        public void close() {
            System.out.println("Автомобіль " + brand + " " + model + " видалено з пам'яті.");
        }
    }

    // Головний метод
    public static void main(String[] args) {

        // 1. Стандартний конструктор
        try (Car car1 = new Car()) {
            System.out.println(car1.getInfo());
        }

        System.out.println();

        // 2. Параметризований конструктор
        try (Car car2 = new Car("Toyota", "Camry", 2020, 45000.5)) {
            System.out.println(car2.getInfo());

            System.out.println();

            // 3. Копіювальний конструктор
            try (Car car3 = new Car(car2)) {
                System.out.println(car3.getInfo());
            }
        }
    }
}
