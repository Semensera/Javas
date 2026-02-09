public class BankAccount {

    // ===== Статичне поле для генерації унікального номера =====
    private static int counter = 1000;

    // ===== Приховані поля =====
    private String accountNumber;
    private String ownerName;
    private double balance;

    // ===== Конструктор =====
    public BankAccount(String ownerName, double initialBalance) {
        if (ownerName == null || ownerName.length() < 3) {
            throw new IllegalArgumentException("Ім'я має містити щонайменше 3 символи");
        }

        if (initialBalance < 0) {
            throw new IllegalArgumentException("Баланс не може бути від’ємним");
        }

        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    // ===== Генерація номера рахунку =====
    private static String generateAccountNumber() {
        counter++;
        return "UA" + counter;
    }

    // ===== Гетери =====
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    // ===== Сетер для імені =====
    public void setOwnerName(String ownerName) {
        if (ownerName == null || ownerName.length() < 3) {
            System.out.println("❌ Ім'я має містити щонайменше 3 символи");
            return;
        }
        this.ownerName = ownerName;
    }

    // ===== Поповнення рахунку =====
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Сума поповнення має бути додатною");
            return;
        }
        balance += amount;
    }

    // ===== Зняття коштів =====
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Сума зняття має бути додатною");
            return;
        }

        if (amount > balance) {
            System.out.println("❌ Недостатньо коштів на рахунку");
            return;
        }

        balance -= amount;
    }
}
