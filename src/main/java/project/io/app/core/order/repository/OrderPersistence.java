package project.io.app.core.order.repository;

import org.springframework.stereotype.Component;
import project.io.app.core.order.domain.Order;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderPersistence {

    private final Map<Long, Order> repository = new HashMap<>();

    public Long save(final Order order) {
        repository.put(order.getId(), order);
        return order.getId();
    }
}
