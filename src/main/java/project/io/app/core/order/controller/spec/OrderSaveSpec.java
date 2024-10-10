package project.io.app.core.order.controller.spec;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import project.io.app.common.exception.ErrorResponse;
import project.io.app.core.order.controller.request.OrderSaveRequest;
import project.io.app.core.order.controller.response.OrderSaveResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "상품이 성공적으로 저장되었습니다.",
        content = @Content(schema = @Schema(implementation = OrderSaveResponse.class))),
    @ApiResponse(responseCode = "400", description = "올바르지 않은 파라미터가 입력되었습니다.",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class),
            examples = {
                @ExampleObject(
                    name = "InvalidParamExample",
                    value = "{\"code\":400, \"message\":\"올바르지 않은 파라미터입니다.\"}")
            })),
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "ServerErrorExample",
                    value = "{\"code\":500, \"message\":\"서버 내부 오류입니다.\"}")
            }))
})
@RequestBody(
    description = "주문 저장 요청 예시",
    content = @Content(
        schema = @Schema(implementation = OrderSaveRequest.class),
        examples = {
            @ExampleObject(
                name = "OrderSaveRequestExample",
                value = "{\"orderId\": 1}"
            )
        }
    )
)
public @interface OrderSaveSpec {
}
