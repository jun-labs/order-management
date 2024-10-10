package project.io.app.core.order.repository;

import org.springframework.stereotype.Component;
import project.io.app.common.cursor.Cursor;
import project.io.app.core.order.domain.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class OrderPersistence {

    private final Map<Long, Order> repository = new HashMap<>();

    public Long save(final Order order) {
        repository.put(order.getId(), order);
        return order.getId();
    }

    public Order get(final Long orderId) {
        return repository.get(orderId);
    }

    public List<Order> get(final Cursor cursor) {
        final Stream<Order> sortedStream = repository.values().stream()
            .sorted((o1, o2) -> Long.compare(o2.getId(), o1.getId()));
        if (cursor.isNull()) {
            return sortedStream
                .limit(cursor.getLimit() != null ? cursor.getLimit() : 10)
                .toList();
        }
        return sortedStream
            .filter(order -> order.getId() < cursor.getIndex())
            .limit(cursor.getLimit() != null ? cursor.getLimit() : 10)
            .toList();
    }

    public void clear() {
        repository.clear();
    }
}
