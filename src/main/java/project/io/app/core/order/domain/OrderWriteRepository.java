package project.io.app.core.order.domain;

public interface OrderWriteRepository {
    Long save(Order order);
}
