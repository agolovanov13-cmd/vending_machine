import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final MoneyAcceptor moneyAcceptor;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        moneyAcceptor = new MoneyAcceptor(100);
    }

    public static void run() {
        AppRunner app = new AppRunner();
        CardPay cardPay = new CardPay(new Card(200));
        while (!isExit) {
            app.startSimulation(cardPay);
        }
    }

    private void startSimulation(CardPay cardPay) {
        print("В автомате доступны:");
        showProducts(products);

        print("Баланс автомата: " + moneyAcceptor.getAmount());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts, cardPay);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (moneyAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products , CardPay cardPay) {

        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            print("Выберите удобный для Вас способ пополнения:");
            print(" a - Карта\n" +
                    " b - Монеты");
            String methodOfReplenishment = fromConsole().substring(0, 1);
            if ("a".equalsIgnoreCase(methodOfReplenishment)){
                try {
                    moneyAcceptor.setAmount(cardPay.payment());
                    cardPay.cardModel.setBalance(-10);
                    print("Ваш баланс на карте: " + cardPay.cardModel.getBalance());
                }catch (NumberFormatException ne){
                    print(ne.getMessage());
                }
                return;
            } else if("b".equalsIgnoreCase(methodOfReplenishment)){
                CoinPay coinPay = new CoinPay();
                moneyAcceptor.setAmount(coinPay.payment());
                return;
            } else {
                print("Недопустимая буква. Попробуйте еще раз.");
                chooseAction(products, cardPay);
                return;
            }
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    moneyAcceptor.setAmount(- products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попробуйте еще раз.");
                chooseAction(products, cardPay);
            }
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
