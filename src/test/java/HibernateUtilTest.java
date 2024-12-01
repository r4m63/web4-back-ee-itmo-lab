import dev.ramil21.web4back.util.HibernateUtil;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HibernateUtilTest {

    @Inject
    private HibernateUtil hibernateUtil; // Инъекция HibernateUtil

    private SessionFactory sessionFactory;

    @BeforeAll
    public void setup() {
        hibernateUtil = new HibernateUtil(); // Ручная инициализация
        hibernateUtil.init(); // Вызываем метод для создания SessionFactory
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @AfterAll
    public void tearDown() {
        hibernateUtil.closeSessionFactory(); // Закрываем SessionFactory
    }

    @Test
    public void testSessionFactoryInitialization() {
        assertNotNull(sessionFactory, "SessionFactory должна быть инициализирована");
    }

    @Test
    public void testSessionCreation() {
        try (Session session = sessionFactory.openSession()) {
            assertNotNull(session, "Сессия должна быть создана");
        }
    }

    @Test
    public void testSessionFactoryClose() {
        hibernateUtil.closeSessionFactory();
        assertTrue(sessionFactory.isClosed(), "SessionFactory должна быть закрыта");
    }
}
