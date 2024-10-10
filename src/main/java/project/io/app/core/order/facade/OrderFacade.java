package project.io.app.core.order.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.io.app.core.order.domain.Order;
import project.io.app.core.order.external.OrderExternalComponent;
import project.io.app.core.order.service.OrderWriteService;
import project.io.app.core.order.service.command.OrderSaveCommand;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderExternalComponent externalComponent;
    private final OrderWriteService orderWriteService;

    public Long save(final Long orderId) {
        final OrderSaveCommand command = externalComponent.fetchOrderData(orderId);
        final Order newOrder = command.toEntity();
        return orderWriteService.save(newOrder);
    }
}
