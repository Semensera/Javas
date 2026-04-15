import java.util.ArrayList;
import java.util.List;


// 1. ІНТЕРФЕЙСИ


// Інтерфейс Observer (Спостерігач/Підписник)
// Визначає метод, який буде викликатися при оновленні стану Суб'єкта
interface Observer {
    void update(String stockName, double newPrice);
}

// Інтерфейс Subject (Суб'єкт/Видавець)
// Визначає методи для управління підписками та розсилки сповіщень
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}


// 2. РЕАЛІЗАЦІЯ СУБ'ЄКТА


// Клас StockExchange (Біржа)
class StockExchange implements Subject {
    // Список усіх поточних підписників
    private final List<Observer> observers = new ArrayList<>();
    
    // Стан, за яким спостерігають
    private String stockName;
    private double stockPrice;

    // Головний метод, який імітує зміну ціни на біржі
    public void setStockPrice(String stockName, double newPrice) {
        this.stockName = stockName;
        this.stockPrice = newPrice;
        notifyObservers(); // Як тільки ціна змінилася, автоматично сповіщаємо всіх
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockName, stockPrice);
        }
    }
}


// 3. РЕАЛІЗАЦІЯ СПОСТЕРІГАЧІВ


// Клас Investor (Інвестор)
class Investor implements Observer {
    private final String name;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockName, double newPrice) {
        System.out.println("Інвестор " + name + " повідомлений: Акція " + stockName + " змінила ціну на " + newPrice);
    }
}

// Клас Broker (Брокер)
class Broker implements Observer {
    private final String name;

    public Broker(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockName, double newPrice) {
        System.out.println("Брокер " + name + " повідомлений: Акція " + stockName + " тепер коштує " + newPrice);
    }
}


// 4. ГОЛОВНИЙ КЛАС ДЛЯ ДЕМОНСТРАЦІЇ

public class ObserverPatternDemo {
    public static void main(String[] args) {
        System.out.println("=== Запуск симуляції біржі (Шаблон Observer) ===\n");

        // 1. Створюємо "Суб'єкт" (Біржу)
        StockExchange stockExchange = new StockExchange();

        // 2. Створюємо "Спостерігачів" (Підписників)
        Observer investor1 = new Investor("Олександр");
        Observer investor2 = new Investor("Марія");
        Observer broker = new Broker("Компанія 'ТрейдМакс'");

        // 3. Додаємо підписників до системи сповіщень
        stockExchange.attach(investor1);
        stockExchange.attach(investor2);
        stockExchange.attach(broker);

        // 4. Симулюємо зміни на біржі
        System.out.println("--- Оновлення 1 ---");
        stockExchange.setStockPrice("Apple", 145.50);
        
        System.out.println("\n--- Оновлення 2 ---");
        stockExchange.setStockPrice("Google", 2730.20);

        // 5. Видаляємо одного підписника (він більше не хоче отримувати сповіщення)
        stockExchange.detach(investor2);

        // 6. Симулюємо ще одну зміну
        System.out.println("\n--- Оновлення 3 (Марія відписалася) ---");
        stockExchange.setStockPrice("Microsoft", 310.00);
    }
}
