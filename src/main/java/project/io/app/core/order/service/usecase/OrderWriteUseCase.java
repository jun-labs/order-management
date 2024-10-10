package project.io.app.core.order.service.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.io.app.core.order.domain.Order;
import project.io.app.core.order.domain.OrderWriteRepository;
import project.io.app.core.order.service.OrderWriteService;

@Service
@RequiredArgsConstructor
class OrderWriteUseCase implements OrderWriteService {

    private final OrderWriteRepository orderWriteRepository;

    @Override
    @Transactional
    public Long save(final Order order) {
        return orderWriteRepository.save(order);
    }
}
