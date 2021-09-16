package Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private int number;
    private int limit;
    private int balance;

    @ManyToOne
    public Bank bank;

    @OneToOne
    public Pincode pincode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLimit() {
        return limit;
    }

    public int getNumber() {
        return number;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setPincode(Pincode pincode) {
        this.pincode = pincode;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
