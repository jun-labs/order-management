package project.io.app.core.order.service.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.io.app.common.cursor.Cursor;
import project.io.app.core.order.domain.Order;
import project.io.app.core.order.domain.OrderReadRepository;
import static project.io.app.core.order.exception.OrderCodeAndMessage.ORDER_NOT_FOUND;
import project.io.app.core.order.exception.OrderNotFoundException;
import project.io.app.core.order.service.OrderReadService;

import java.util.List;

@Service
@RequiredArgsConstructor
class OrderReadUseCase implements OrderReadService {

    private final OrderReadRepository orderReadRepository;

    @Override
    public Order findById(final Long orderId) {
        return orderReadRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));
    }

    @Override
    public List<Order> findOrders(final Cursor cursor) {
        return orderReadRepository.findOrders(cursor);
    }
}
