package test.demo.model;

import java.util.StringJoiner;

/**
 * @author xiongyx
 * @date 2019/6/24
 */
public class Order {

    private String id;

    private int amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]").add("id='" + id + "'")
            .add("amount=" + amount)
            .toString();
    }
}
