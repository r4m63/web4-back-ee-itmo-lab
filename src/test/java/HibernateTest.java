import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
    public static void main(String[] args) {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("SessionFactory created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}