package model;

public class CoinPay {

    public int payment() {
        print("Вы пополнили баланс автомата на 10");
        return 10;
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
