import java.util.*;
import java.io.*;

public class Controller {
    Scanner scan = new Scanner(System.in);
    private Model model;
    private UserView view;

    public Controller(Model model, UserView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        while (true) {
            view.printMenu();
            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    addToy();
                    break;
                case 2:
                    removeToy();
                    break;
                case 3:
                    editToy();
                    break;
                case 4:
                    view.printToyCatalog(model.getToys());
                    break;
                case 5:
                    runLottery();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    view.printMessage("Неверный выбор. Пожалуйста, введите номер действия от 1 до 6.");
                    break;
            }
            saveToysToFile("toys.txt");
            view.printMessage("Программа завершена.");
        }
    }

    // public void run() {
    // try (Scanner scanner = new Scanner(System.in)) {
    // boolean running = true;

    // while (running) {
    // view.printMenu();
    // int choice = scanner.nextInt();
    // scanner.nextLine();

    // switch (choice) {
    // case 1:
    // addToy();
    // break;
    // case 2:
    // removeToy();
    // break;
    // case 3:
    // editToy();
    // break;
    // case 4:
    // view.printToyCatalog(model.getToys());
    // break;
    // case 5:
    // runLottery();
    // break;
    // case 6:
    // running = false;
    // break;
    // default:
    // view.printMessage("Неверный выбор. Пожалуйста, введите номер действия от 1 до
    // 6.");
    // break;
    // }
    // }

    // saveToysToFile("toys.txt");
    // view.printMessage("Программа завершена.");
    // }
    // }

    private void addToy() {
        System.out.print("Введите ID игрушки: ");
        int id = scan.nextInt();
        scan.nextLine();
        System.out.print("Введите название игрушки: ");
        String name = scan.nextLine();
        System.out.print("Введите частоту выпадения игрушки: ");
        int weight = scan.nextInt();
        scan.nextLine();

        model.addToy(new Toy(id, name, weight));
        view.printMessage("Игрушка успешно добавлена в каталог.");

    }

    private void removeToy() {
        System.out.print("Введите ID игрушки, которую нужно удалить: ");
        int id = scan.nextInt();
        scan.nextLine();

        model.removeToy(id);
        view.printMessage("Игрушка успешно удалена из каталога.");

    }

    private void editToy() {
        System.out.print("Введите ID игрушки, которую нужно отредактировать: ");
        int id = scan.nextInt();
        scan.nextLine();

        System.out.print("Введите новое название игрушки: ");
        String newName = scan.nextLine();
        System.out.print("Введите новую частоту выпадения игрушки: ");
        int newWeight = scan.nextInt();
        scan.nextLine();

        model.editToy(id, newName, newWeight);
        view.printMessage("Игрушка успешно отредактирована.");

    }

    private void runLottery() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("results.txt"), "UTF-8"))) {
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                int totalWeight = 0;
                for (Toy toy : model.getToys()) {
                    totalWeight += toy.getWeight();
                }

                int randomNumber = random.nextInt(totalWeight) + 1;
                int currentWeight = 0;
                for (Toy toy : model.getToys()) {
                    currentWeight += toy.getWeight();
                    if (randomNumber <= currentWeight) {
                        String result = toy.getId() + " - " + toy.getName();
                        view.printMessage(result);
                        writer.write(result);
                        writer.newLine();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            view.printMessage("Ошибка при записи результатов розыгрыша: " + e.getMessage());
        }
    }

    private void saveToysToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
            for (Toy toy : model.getToys()) {
                writer.write(toy.getId() + " " + toy.getName() + " " + toy.getWeight());
                writer.newLine();
            }
        } catch (IOException e) {
            view.printMessage("Ошибка при сохранении игрушек в файл: " + e.getMessage());
        }
    }
}
