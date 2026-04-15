import java.util.ArrayList;
import java.util.List;


// 1. СПІЛЬНИЙ ІНТЕРФЕЙС (Component)

// Описує загальні операції як для окремих працівників, так і для відділів.
interface Employee {
    void add(Employee employee);
    void remove(Employee employee);
    List<Employee> getSubordinates();
    void displayInfo(String indent); // indent використовується для красивого виводу дерева
}


// 2. КІНЦЕВИЙ ЕЛЕМЕНТ (Leaf)

// Клас IndividualEmployee представляє співробітника, який не має підлеглих.
class IndividualEmployee implements Employee {
    private final String name;
    private final String position;

    public IndividualEmployee(String name, String position) {
        this.name = name;
        this.position = position;
    }

    // Оскільки звичайний працівник не може мати підлеглих, ці методи 
    // або нічого не роблять, або викидають виняток. Це стандартний підхід для шаблону Composite.
    @Override
    public void add(Employee employee) {
        throw new UnsupportedOperationException("Неможливо додати підлеглого до окремого співробітника.");
    }

    @Override
    public void remove(Employee employee) {
        throw new UnsupportedOperationException("Неможливо видалити підлеглого в окремого співробітника.");
    }

    @Override
    public List<Employee> getSubordinates() {
        return new ArrayList<>(); // Повертає порожній список
    }

    @Override
    public void displayInfo(String indent) {
        System.out.println(indent + "- Співробітник: " + name + " (" + position + ")");
    }
}

// 
// 3. СКЛАДЕНИЙ ЕЛЕМЕНТ (Composite)

// Клас Department представляє відділ, який може містити інших працівників або навіть підвідділи.
class Department implements Employee {
    private final String name;
    private final String position; // В контексті відділу це може бути його функція
    private final List<Employee> subordinates;

    public Department(String name, String position) {
        this.name = name;
        this.position = position;
        this.subordinates = new ArrayList<>();
    }

    @Override
    public void add(Employee employee) {
        subordinates.add(employee);
    }

    @Override
    public void remove(Employee employee) {
        subordinates.remove(employee);
    }

    @Override
    public List<Employee> getSubordinates() {
        return subordinates;
    }

    @Override
    public void displayInfo(String indent) {
        System.out.println(indent + "+ Відділ: " + name + " (" + position + ")");
        // Рекурсивно викликаємо displayInfo для всіх підлеглих елементів
        for (Employee e : subordinates) {
            e.displayInfo(indent + "    ");
        }
    }
}


// 4. КЛІЄНТСЬКИЙ КОД (Демонстрація роботи)

public class CompanyStructureDemo {
    public static void main(String[] args) {
        // 1. Створюємо окремих співробітників (Листя)
        Employee dev1 = new IndividualEmployee("Іван Франко", "Junior Java Developer");
        Employee dev2 = new IndividualEmployee("Леся Українка", "Middle Backend Developer");
        Employee dev3 = new IndividualEmployee("Тарас Шевченко", "Senior Java Developer");
        
        Employee qa1 = new IndividualEmployee("Ліна Костенко", "Manual QA");
        Employee qa2 = new IndividualEmployee("Григорій Сковорода", "QA Automation");
        
        Employee hr1 = new IndividualEmployee("Михайло Коцюбинський", "HR Manager");

        // 2. Створюємо підвідділи (Композити)
        Employee devDepartment = new Department("Відділ розробки", "Створення програмного забезпечення");
        devDepartment.add(dev1);
        devDepartment.add(dev2);
        devDepartment.add(dev3);

        Employee qaDepartment = new Department("Відділ тестування", "Забезпечення якості");
        qaDepartment.add(qa1);
        qaDepartment.add(qa2);

        // 3. Створюємо головний відділ компанії (Кореневий Композит)
        Employee headOffice = new Department("Головний офіс", "Управління компанією");
        headOffice.add(hr1); // HR підпорядковується безпосередньо головному офісу
        headOffice.add(devDepartment); // Додаємо цілий відділ як підлеглий елемент
        headOffice.add(qaDepartment);  // Додаємо ще один відділ

        // 4. Виводимо всю структуру компанії
        // Клієнтський код працює з головним офісом (Department) так само, як працював би з одним співробітником.
        System.out.println("=== Організаційна структура компанії ===");
        headOffice.displayInfo("");

        System.out.println("\n=== Вивід інформації лише про відділ розробки ===");
        devDepartment.displayInfo("");
    }
}
