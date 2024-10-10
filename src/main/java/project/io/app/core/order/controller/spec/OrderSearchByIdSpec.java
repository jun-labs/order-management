package project.io.app.core.order.controller.spec;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import project.io.app.common.exception.ErrorResponse;
import project.io.app.core.order.controller.response.OrderDetailResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "주문이 성공적으로 조회되었습니다.",
        content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = OrderDetailResponse.class))),
    @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없습니다.",
        content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "OrderNotFoundExample",
                    value = "{\"code\":404, \"message\":\"주문을 찾을 수 없습니다.\"}")
            })),
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
        content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "ServerErrorExample",
                    value = "{\"code\":500, \"message\":\"서버 내부 오류입니다.\"}")
            }))
})
public @interface OrderSearchByIdSpec {
}
