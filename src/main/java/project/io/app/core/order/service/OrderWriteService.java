package project.io.app.core.order.service;

import project.io.app.core.order.domain.Order;

public interface OrderWriteService {
    Long save(Order order);
}
