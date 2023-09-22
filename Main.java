public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        UserView view = new UserView();
        Controller controller = new Controller(model, view);

        controller.run();
    }
}
