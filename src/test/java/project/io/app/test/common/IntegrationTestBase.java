package project.io.app.test.common;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import project.io.app.core.order.repository.OrderPersistence;

@SpringBootTest
@ActiveProfiles(value = "test")
public abstract class IntegrationTestBase {

    @Autowired
    private OrderPersistence orderPersistence;

    @BeforeEach
    void setUp() {
        orderPersistence.clear();
    }
}
