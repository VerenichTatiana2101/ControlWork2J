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
            int choice = 0;
            while (choice < 1 || choice > 7) {
                System.out.print("Введите номер действия от 1 до 7: ");
                if (scan.hasNextInt()) {
                    choice = scan.nextInt();
                    scan.nextLine();
                } else {
                    System.out.println("Вы ввели некорректное значение. Пожалуйста, введите число от 1 до 7.");
                    scan.nextLine();
                }
            }

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
                    priceToy();
                    break;
                case 7:
                    System.exit(0);
                    break;
            }
            saveToysToFile("toys.txt");
            view.printMessage("Программа завершена.");
        }
    }

    private void addToy() {
        System.out.print("Введите ID игрушки: ");
        int id = 0;
        while (id == 0) {
            System.out.print("Введите число: ");
            if (scan.hasNextInt()) {
                id = scan.nextInt();
                scan.nextLine();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.println("Вы ввели некорректное значение. Пожалуйста, введите число.");
                scan.nextLine();
            }
        }

        System.out.print("Введите название игрушки: ");
        String name = scan.nextLine();

        System.out.print("Введите частоту выпадения игрушки: ");
        int weight = 0;
        while (weight < 1 || weight > 10) {
            System.out.print("Введите число от 1 до 10: ");
            if (scan.hasNextInt()) {
                weight = scan.nextInt();
                scan.nextLine();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.println("Вы ввели некорректное значение. Пожалуйста, введите число.");
                scan.nextLine();
            }
        }

        if (id == 0 || name.isEmpty() || weight == 0) {
            System.out.println("Вы не ввели все данные.");
        } else {
            model.addToy(new Toy(id, name, weight));
            view.printMessage("Игрушка успешно добавлена в каталог.");
        }
    }

    private void removeToy() {
        System.out.println("Введите ID игрушки, которую нужно удалить: ");
        while (!scan.hasNextInt()) {
            System.out.println("Ошибка ввода данных, попробуйте ещё раз: ");
            scan.next();
        }
        int id = scan.nextInt();
        scan.nextLine();

        model.removeToy(id);
        view.printMessage("Игрушка успешно удалена из каталога.");

    }

    private void editToy() {
        System.out.println("Введите ID игрушки, которую нужно удалить: ");
        while (!scan.hasNextInt()) {
            System.out.print("\033[H\033[2J");
            System.out.println("Ошибка ввода данных, попробуйте ещё раз: ");
            scan.next();
        }
        int id = scan.nextInt();
        scan.nextLine();

        System.out.println("Введите новую частоту выпадения игрушки: ");
        while (!scan.hasNextInt()) {
            System.out.print("\033[H\033[2J");
            System.out.println("Ошибка ввода данных, попробуйте ещё раз: ");
            scan.next();
        }
        int newWeight = scan.nextInt();
        scan.nextLine();

        model.editToy(id, newWeight);
        view.printMessage("Игрушка успешно отредактирована.");
    }

    private void runLottery() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("results.txt"), "UTF-8"))) {
            Random random = new Random();
            if (model.getToys().isEmpty()) {
                view.printMessage("игрушки отсутствуют");
                return;
            }
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
                        String result = toy.toString();
                        view.printMessage(result);
                        writer.write(result);
                        writer.newLine();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            view.printMessage("Ошибка при записи результатов розыгрыша: " +
                    e.getMessage());
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

    // private void priceToy(){
    // String filename = "results.txt";
    // File file = new File(filename);

    // if (file.exists()) {
    // try {
    // BufferedReader br = new BufferedReader(new FileReader(file));
    // String firstLine = br.readLine();
    // System.out.println(firstLine);
    // br.close();

    // BufferedWriter bw = new BufferedWriter(new FileWriter(file));
    // String remainingLines = "";
    // String line;
    // while ((line = br.readLine()) != null) {
    // remainingLines += line + "\n";
    // }
    // bw.write(remainingLines);
    // bw.close();
    // } catch (IOException e) {
    // System.out.println("Обновите очередь игрушек");
    // }
    // } else {
    // System.out.println("Отсутствуют игрушки для розыгрыша");
    // }
    // }

    private void priceToy() {
        String filename = "results.txt";
        File file = new File(filename);

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String firstLine = br.readLine();
                System.out.println(firstLine);
                br.close();

                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                String remainingLines = "";
                String line;
                while ((line = br.readLine()) != null) {
                    remainingLines += line + "\n";
                }
                bw.write(remainingLines);
                bw.close();
            } catch (IOException e) {
                System.out.println("Обновите очередь игрушек");
            }
        } else {
            System.out.println("Отсутствуют игрушки для розыгрыша");
        }
    }

}
