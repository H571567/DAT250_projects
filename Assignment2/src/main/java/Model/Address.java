package Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String street;
    private int number;

    @ManyToMany()
    public final List<Person> people = new ArrayList<Person>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() { return street; }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {return number; }

    public List<Person> getPeople() { return people; }

}

