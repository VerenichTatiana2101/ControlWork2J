import java.util.List;

public class UserView {
    public void printMenu() {
        System.out.println("1. Добавить игрушку");
        System.out.println("2. Удалить игрушку");
        System.out.println("3. Редактировать частоту выпадения игрушки");
        System.out.println("4. Просмотреть каталог игрушек");
        System.out.println("5. Обновить очередь игрушек");
        System.out.println("6. Розыгрыш");
        System.out.println("7. Завершить программу");
        System.out.println("-----------------------------");
        System.out.print("Введите номер действия: ");
    }

    public void printToyCatalog(List<Toy> toys) {
        for (Toy toy : toys) {
            System.out.println(toy.getId() + " - " + toy.getName() + " - " + toy.getWeight());
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}