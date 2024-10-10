package project.io.app.core.order.service;

import project.io.app.common.cursor.Cursor;
import project.io.app.core.order.domain.Order;

import java.util.List;

public interface OrderReadService {
    Order findById(Long orderId);

    List<Order> findOrders(Cursor cursor);
}
