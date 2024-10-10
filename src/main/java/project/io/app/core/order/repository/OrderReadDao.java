package project.io.app.core.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.io.app.common.cursor.Cursor;
import project.io.app.core.order.domain.Order;
import project.io.app.core.order.domain.OrderReadRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class OrderReadDao implements OrderReadRepository {

    private final OrderPersistence orderPersistence;

    @Override
    public Optional<Order> findById(final Long orderId) {
        return Optional.ofNullable(orderPersistence.get(orderId));
    }

    @Override
    public List<Order> findOrders(final Cursor cursor) {
        return orderPersistence.get(cursor);
    }
}
