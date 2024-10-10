package project.io.app.core.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.CREATED;
import project.io.app.common.response.ApiResponse;
import project.io.app.core.order.controller.request.OrderSaveRequest;
import project.io.app.core.order.controller.response.OrderSaveResponse;
import project.io.app.core.order.controller.spec.OrderSaveSpec;
import project.io.app.core.order.facade.OrderFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/orders")
public class OrderSaveController {

    private final OrderFacade orderFacade;

    @PostMapping
    @OrderSaveSpec
    public ApiResponse<OrderSaveResponse> saveOrder(@RequestBody @Valid final OrderSaveRequest request) {
        final Long newOrderId = orderFacade.save(request.getOrderId());
        final OrderSaveResponse payload = new OrderSaveResponse(newOrderId);
        return ApiResponse.from(CREATED, payload);
    }
}
