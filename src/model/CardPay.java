package model;

import java.util.Scanner;

public class CardPay {
    public Card cardModel;

    public CardPay(Card card)
    {
        cardModel = card;
    }
    public int payment() {
        Scanner sc = new Scanner(System.in);

        String input1;

        while (true) {
            try {
                print("Введите номер карты: ");
                input1 = sc.nextLine().strip();
                if (!input1.matches("\\d+")) {
                    throw new NumberFormatException("Номер карты должен содержать только цифры");
                }
                if (input1.length() != 16) {
                    throw new IllegalArgumentException("Номер карты должен содержать 16 цифр");
                }
                break;
            } catch (NumberFormatException ne) {
                print(ne.getMessage());
            }catch (IllegalArgumentException iae){
                print(iae.getMessage());
            }
        }

        cardModel.setCode(input1);

        String input2;
        while (true) {
            try {
                print("Введите трёхзначный код: ");
                input2 = sc.nextLine().strip();
                if (!input2.matches("\\d+")) {
                    throw new NumberFormatException("Проверочный код должен содержать только цифры");
                }
                if (input2.length() != 3) {
                    throw new IllegalArgumentException("Проверочный код должен состоять из 3-х цифр");
                }
                break;
            } catch (NumberFormatException ne) {
                print(ne.getMessage());
            }catch (IllegalArgumentException iae){
                print(iae.getMessage());
            }
        }
        int authCode = Integer.parseInt(input2);
        cardModel.setAuthCode(authCode);

        print("Вы пополнили баланс автомата на 10");
        return 10;
    }

    private void print(String msg) {
        System.out.println(msg);
    }

}
