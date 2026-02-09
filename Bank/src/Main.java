import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ===== Створення рахунків =====
        BankAccount acc1 = new BankAccount("Олександр", 1000);
        BankAccount acc2 = new BankAccount("Марія", 500);
        BankAccount acc3 = new BankAccount("Іван", 300);

        // ===== Операції =====
        acc1.deposit(200);
        acc1.withdraw(150);

        acc2.withdraw(800);     // помилка
        acc2.deposit(-50);      // помилка
        acc2.setOwnerName("Ма"); // помилка
        acc2.setOwnerName("Марина");

        // ===== Вивід =====
        System.out.println(acc1.getAccountNumber() + " | " + acc1.getOwnerName() + " | " + acc1.getBalance());
        System.out.println(acc2.getAccountNumber() + " | " + acc2.getOwnerName() + " | " + acc2.getBalance());
        System.out.println(acc3.getAccountNumber() + " | " + acc3.getOwnerName() + " | " + acc3.getBalance());

        // ===== Пошук рахунку =====
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(acc1);
        accounts.add(acc2);
        accounts.add(acc3);

        BankAccount found = findByAccountNumber(accounts, acc2.getAccountNumber());

        if (found != null) {
            System.out.println("✅ Знайдено рахунок: " + found.getOwnerName());
        } else {
            System.out.println("❌ Рахунок не знайдено");
        }
    }

    // ===== Пошук рахунку за номером =====
    public static BankAccount findByAccountNumber(List<BankAccount> accounts, String number) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(number)) {
                return acc;
            }
        }
        return null;
    }
}
