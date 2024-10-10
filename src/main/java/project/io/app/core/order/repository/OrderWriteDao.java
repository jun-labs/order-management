package project.io.app.core.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.io.app.core.order.domain.Order;
import project.io.app.core.order.domain.OrderWriteRepository;

@Repository
@RequiredArgsConstructor
class OrderWriteDao implements OrderWriteRepository {

    private final OrderPersistence orderPersistence;

    @Override
    public Long save(final Order order) {
        return orderPersistence.save(order);
    }
}
