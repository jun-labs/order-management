package project.io.app.core.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.OK;
import project.io.app.common.cursor.Cursor;
import project.io.app.common.cursor.CursorPageable;
import project.io.app.common.response.ApiResponse;
import project.io.app.core.order.controller.response.OrderDetailResponse;
import project.io.app.core.order.controller.spec.OrderSearchByIdSpec;
import project.io.app.core.order.controller.spec.OrderSearchOrdersSpec;
import project.io.app.core.order.domain.Order;
import project.io.app.core.order.service.OrderReadService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/orders")
public class OrderSearchController {

    private final OrderReadService orderReadService;

    @OrderSearchByIdSpec
    @GetMapping(path = "/{orderId}")
    public ApiResponse<OrderDetailResponse> searchById(@PathVariable(name = "orderId") final Long orderId) {
        final Order findOrder = orderReadService.findById(orderId);
        final OrderDetailResponse payload = new OrderDetailResponse(findOrder);
        return ApiResponse.from(OK, payload);
    }

    @OrderSearchOrdersSpec
    @GetMapping
    public ApiResponse<List<OrderDetailResponse>> searchOrders(@CursorPageable final Cursor cursor) {
        final List<OrderDetailResponse> payload = orderReadService.findOrders(cursor).stream()
            .map(OrderDetailResponse::new)
            .toList();
        return ApiResponse.from(OK, payload);
    }
}
