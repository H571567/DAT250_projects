package Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String name;

    @ManyToMany
    public final List<Address> addressList = new ArrayList<Address>();

    @OneToMany
    public final List<CreditCard> creditCardList = new ArrayList<CreditCard>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }
}
