package Test;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import Model.*;
import org.junit.Before;
import org.junit.Test;

public class JpaTest {

    private static final String PERSISTENCE_UNIT_NAME = "user2";
    private EntityManagerFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        // Begin a new local transaction so that we can persist a new entity
        em.getTransaction().begin();

        // read the existing entries
        Query q = em.createQuery("select m from Person m");
        // Persons should be empty

        // do we have entries?
        boolean createNewEntries = (q.getResultList().size() == 0);

        // No, so lets create new entries
        if (createNewEntries) {
            assertTrue(q.getResultList().size() == 0);
            Person user = new Person();
            user.setName("Max Mustermann");

            Address address = new Address();
            address.setStreet("Inndalsveien");
            address.setNumber(28);

            CreditCard creditCard = new CreditCard();
            creditCard.setNumber(12345);
            creditCard.setLimit(-10000);
            creditCard.setBalance(-5000);

            CreditCard creditCard2 = new CreditCard();
            creditCard2.setNumber(123);
            creditCard2.setBalance(1);
            creditCard2.setLimit(2000);

            Bank bank = new Bank();
            bank.setName("Pengebank");

            Pincode pincode = new Pincode();
            pincode.setPincode("123");
            pincode.setCount(1);

            //Add address to Person and vica verca
            user.addressList.add(address);
            address.people.add(user);

            //Add creditcards to Person
            user.creditCardList.add(creditCard);
            user.creditCardList.add(creditCard2);

            //Add bank to creditCard and vica cerca
            bank.creditCardList.add(creditCard);
            bank.creditCardList.add(creditCard2);
            creditCard.setBank(bank);
            creditCard2.setBank(bank);

            //Add pincode to creditCard
            creditCard.setPincode(pincode);
            creditCard2.setPincode(pincode);

            //Now persists the objects relationship
            em.persist(user);
            em.persist(address);
            em.persist(creditCard);
            em.persist(creditCard2);
            em.persist(bank);
            em.persist(pincode);

        }

        // Commit the transaction, which will cause the entity to
        // be stored in the database
        em.getTransaction().commit();

        // It is always good practice to close the EntityManager so that
        // resources are conserved.
        em.close();

    }

    @Test
    public void checkAvailablePeople() {

        // now lets check the database and see if the created entries are there
        // create a fresh, new EntityManager
        EntityManager em = factory.createEntityManager();

        // Perform a simple query for all the Message entities
        Query q = em.createQuery("select m from Person m");

        // We should have 40 Persons in the database
        assertTrue(q.getResultList().size() == 40);

        em.close();
    }

}
