package project.io.app.core.order.domain;

import project.io.app.common.cursor.Cursor;

import java.util.List;
import java.util.Optional;

public interface OrderReadRepository {
    Optional<Order> findById(Long orderId);

    List<Order> findOrders(Cursor cursor);
}
