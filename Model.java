import java.util.*;

public class Model {
    private List<Toy> toys;

    public Model() {
        toys = new ArrayList<>();
    }

    public List<Toy> getToys() {
        return toys;
    }

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void removeToy(int id) {
        toys.removeIf(toy -> toy.getId() == id);
    }

    public void editToy(int id, int newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                toy.getName();
                toy.setWeight(newWeight);
                return;
            }
        }
    }
}
