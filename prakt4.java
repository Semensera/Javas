
class Transport {
    protected String brand;
    protected String model;
    protected int year;

    
    public Transport(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    
    public String getInfo() {
        return "Марка: " + brand + ", Модель: " + model + ", Рік випуску: " + year;
    }
}


class Car extends Transport {
    private int passengerCount;

    public Car(String brand, String model, int year, int passengerCount) {
        super(brand, model, year);
        this.passengerCount = passengerCount;
    }

    public int getPassengerCapacity() {
        return passengerCount;
    }

   
    @Override
    public String getInfo() {
        return "Автомобіль -> " + super.getInfo() + ", Кількість пасажирів: " + passengerCount;
    }
}


class Truck extends Transport {
    private double cargoCapacity; 
    public Truck(String brand, String model, int year, double cargoCapacity) {
        super(brand, model, year);
        this.cargoCapacity = cargoCapacity;
    }

    public double getCargoCapacity() {
        return cargoCapacity;
    }

  
    @Override
    public String getInfo() {
        return "Вантажівка -> " + super.getInfo() + ", Вантажопідйомність: " + cargoCapacity + " т";
    }
}


class Bike extends Transport {
    private int engineVolume; 

    public Bike(String brand, String model, int year, int engineVolume) {
        super(brand, model, year);
        this.engineVolume = engineVolume;
    }

    public int getEngineVolume() {
        return engineVolume;
    }


    @Override
    public String getInfo() {
        return "Мотоцикл -> " + super.getInfo() + ", Об'єм двигуна: " + engineVolume + " куб.см";
    }
}

//  для тестування
public class Main {
    public static void main(String[] args) {
        // 1. створення об'єктів для кожного типу транспортного засобу
        Car myCar = new Car("Toyota", "Camry", 2022, 5);
        Truck myTruck = new Truck("Volvo", "FH16", 2020, 20.5);
        Bike myBike = new Bike("Yamaha", "R1", 2023, 998);

        System.out.println("--- Перевірка специфічних можливостей ---");
        // 2. виклик специфічних методів кожного класу
        System.out.println("Місткість автомобіля: " + myCar.getPassengerCapacity() + " осіб");
        System.out.println("Вантажопідйомність вантажівки: " + myTruck.getCargoCapacity() + " тонн");
        System.out.println("Об'єм двигуна мотоцикла: " + myBike.getEngineVolume() + " куб.см\n");

        System.out.println("--- Поліморфний виклик getInfo() ---");
        // 3. реалізація поліморфізму
        // створюємо масив типу базового класу, але поміщаємо в нього об'єкти похідних класів
        Transport[] vehicles = {myCar, myTruck, myBike};

        // проходимо циклом по масиву. Завдяки поліморфізму, для кожного об'єкта 
        // буде викликана його власна, перевизначена версія методу getInfo().
        for (Transport vehicle : vehicles) {
            System.out.println(vehicle.getInfo());
        }
    }
}
