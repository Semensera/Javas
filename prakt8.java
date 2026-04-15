import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// ЧАСТИНА 1: МОНОЛІТНА РЕАЛІЗАЦІЯ (Без дотримання SOLID)
// 

// Усі обов'язки (дані, розрахунки, чеки, знижки) знаходяться в одному класі.
// Жорсткі залежності та порушення майже всіх принципів SOLID.
class CafeOrderMonolith {
    private final Map<String, Double> menu = new HashMap<>();
    private final Map<String, Integer> currentOrder = new HashMap<>();
    private String discountType = "NONE";

    public CafeOrderMonolith() {
        menu.put("Кава", 50.0);
        menu.put("Чай", 40.0);
        menu.put("Тістечко", 80.0);
    }

    public void addItem(String itemName, int quantity) {
        if (menu.containsKey(itemName) && quantity > 0) {
            currentOrder.put(itemName, currentOrder.getOrDefault(itemName, 0) + quantity);
        } else {
            System.out.println("Помилка: Страви немає в меню або неправильна кількість.");
        }
    }

    public void setDiscountType(String type) {
        this.discountType = type;
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<String, Integer> entry : currentOrder.entrySet()) {
            total += menu.get(entry.getKey()) * entry.getValue();
        }

        // Жорстка прив'язка до типів знижок (Порушення OCP)
        if (discountType.equals("HOLIDAY")) {
            total *= 0.90; // 10% знижки
        } else if (discountType.equals("VOLUME") && getTotalItems() >= 5) {
            total *= 0.85; // 15% знижки на обсяг
        }
        return total;
    }

    private int getTotalItems() {
        return currentOrder.values().stream().mapToInt(Integer::intValue).sum();
    }

    // Порушення SRP (відповідає ще й за вивід) та DIP (залежить від консолі)
    public void generateReceipt() {
        System.out.println("\n--- ЧЕК (МОНОЛІТ) ---");
        for (Map.Entry<String, Integer> entry : currentOrder.entrySet()) {
            double price = menu.get(entry.getKey());
            System.out.println(entry.getKey() + " x" + entry.getValue() + " = " + (price * entry.getValue()) + " грн");
        }
        System.out.printf("До сплати: %.2f грн\n", calculateTotal());
        System.out.println("---------------------\n");
    }
}



// ЧАСТИНА 2: РЕАЛІЗАЦІЯ З ДОТРИМАННЯМ ПРИНЦИПІВ SOLID


// Дані замовлення (Тільки зберігання стану)
record OrderItem(String name, double price, int quantity) {}

class SolidOrder {
    private final List<OrderItem> items = new ArrayList<>();

    public void addItem(String name, double price, int quantity) {
        items.add(new OrderItem(name, price, quantity));
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(item -> item.price() * item.quantity()).sum();
    }

    public int getTotalQuantity() {
        return items.stream().mapToInt(OrderItem::quantity).sum();
    }
}

// OCP: Інтерфейс для знижок. Легко додавати нові без зміни існуючого коду.
interface DiscountStrategy {
    double applyDiscount(SolidOrder order);
}

// LSP: Реалізації DiscountStrategy можуть взаємозамінювати одна одну без помилок.
class HolidayDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(SolidOrder order) {
        return order.getSubtotal() * 0.90; // Святкова знижка 10%
    }
}

class VolumeDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(SolidOrder order) {
        if (order.getTotalQuantity() >= 5) {
            return order.getSubtotal() * 0.85; // Знижка 15% за обсяг (5+ позицій)
        }
        return order.getSubtotal();
    }
}

class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(SolidOrder order) {
        return order.getSubtotal();
    }
}

// SRP: Окремий клас, що відповідає виключно за розрахунок знижок
class DiscountCalculator {
    private DiscountStrategy strategy;

    public DiscountCalculator(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateFinalPrice(SolidOrder order) {
        return strategy.applyDiscount(order);
    }
}

// ISP: Невеликий, спеціалізований інтерфейс тільки для генерації чека
interface ReceiptGenerator {
    void generateReceipt(SolidOrder order, double finalPrice);
}

// DIP: Залежність від абстракції (інтерфейсу ReceiptGenerator)
class ConsoleReceiptGenerator implements ReceiptGenerator {
    @Override
    public void generateReceipt(SolidOrder order, double finalPrice) {
        System.out.println("=== ЧЕК (SOLID) ===");
        for (OrderItem item : order.getItems()) {
            System.out.println(item.name() + " x" + item.quantity() + " = " + (item.price() * item.quantity()) + " грн");
        }
        System.out.printf("До сплати зі знижкою: %.2f грн\n", finalPrice);
        System.out.println("===================\n");
    }
}

// SRP: Менеджер відповідає ТІЛЬКИ за управління процесом замовлення
// DIP: OrderManager залежить від абстракцій (DiscountCalculator, ReceiptGenerator), а не від конкретних реалізацій
class OrderManager {
    private final DiscountCalculator discountCalculator;
    private final ReceiptGenerator receiptGenerator;

    public OrderManager(DiscountCalculator discountCalculator, ReceiptGenerator receiptGenerator) {
        this.discountCalculator = discountCalculator;
        this.receiptGenerator = receiptGenerator;
    }

    public void processOrder(SolidOrder order) {
        double finalPrice = discountCalculator.calculateFinalPrice(order);
        receiptGenerator.generateReceipt(order, finalPrice);
    }
}



// ГОЛОВНИЙ КЛАС ДЛЯ ДЕМОНСТРАЦІЇ

public class CafeAssignment {
    public static void main(String[] args) {
        // Демонстрація 1: Монолітний підхід
        System.out.println("ДЕМO 1: МОНОЛІТНА СИСТЕМА");
        CafeOrderMonolith monolithOrder = new CafeOrderMonolith();
        monolithOrder.addItem("Кава", 2);
        monolithOrder.addItem("Тістечко", 3);
        monolithOrder.setDiscountType("VOLUME");
        monolithOrder.generateReceipt();

        // Демонстрація 2: SOLID підхід
        System.out.println("ДЕМO 2: СИСТЕМА ЗА ПРИНЦИПАМИ SOLID");
        SolidOrder solidOrder = new SolidOrder();
        solidOrder.addItem("Кава", 50.0, 2);
        solidOrder.addItem("Тістечко", 80.0, 3);

        // Налаштовуємо компоненти (Впровадження залежностей)
        DiscountCalculator calculator = new DiscountCalculator(new VolumeDiscount());
        ReceiptGenerator receiptPrinter = new ConsoleReceiptGenerator();
        
        // Створюємо менеджера
        OrderManager manager = new OrderManager(calculator, receiptPrinter);
        
        // Обробляємо замовлення
        manager.processOrder(solidOrder);
    }
}
