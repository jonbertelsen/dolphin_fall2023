import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Dolphin!");

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("dolphin");
        try(EntityManager em = emf.createEntityManager())
        {
            Person p1 = new Person("Hanzi");
            PersonDetail pd1 = new PersonDetail("Algade 3", 4300, "Holbæk", 45);
            p1.addPersonDetail(pd1);
            Fee f1 = new Fee(125, LocalDate.of(2023, 8, 25));
            Fee f2 = new Fee(150, LocalDate.of(2023, 7, 19));
            p1.addFee(f1);
            p1.addFee(f2);
            Event e1 = new Event("DM-senior", LocalDate.of(2023, 8, 29));
            Event e2 =new Event("SM-junior", LocalDate.of(2023, 9, 22));
            p1.addEvent(p1, e1, LocalDate.of(2023, 9, 21), 175);
            p1.addEvent(p1, e2, LocalDate.of(2023, 10, 1), 225);

            em.getTransaction().begin();
                em.persist(e1);
                em.persist(e2);
                em.persist(p1);
            em.getTransaction().commit();

            Query q = em.createQuery("select ep.person.personDetail.city FROM Event e JOIN e.persons ep");
            List<String> resultList = (List<String>) q.getResultList();
            for (String s : resultList)
            {
                System.out.println(s);
            }
        }
    }
}