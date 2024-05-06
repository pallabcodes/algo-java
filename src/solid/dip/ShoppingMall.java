package solid.dip;

public class ShoppingMall {
    private DebitCard debitCard; // private CreditCard creditCard;

    // so as seen here I could've used creditCard and the code below will work just fine

    public ShoppingMall(DebitCard cardInfo) {
        this.debitCard = cardInfo; // this.creditCard = cardInfo;
    }

    public void doPurchaseSomething(long amount) {
        debitCard.doTransaction(amount); // creditCard.doTransaction(amount);
    }

    public static void main(String[] args) {
        DebitCard debitcard = new DebitCard(); // CreditCard creditCard = new CreditCard();
        ShoppingMall shoppingMall = new ShoppingMall(debitcard); // new ShoppingMall(creditCard)
        shoppingMall.doPurchaseSomething(5000);
    }
}
