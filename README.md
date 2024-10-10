# 주문 관리 시스템

요구사항을 만족하는 주문 관리 시스템을 구현한다.

<br/><br/>

# 📝 요구사항

1. 외부 시스템으로부터 주문 데이터를 받아와 시스템에 저장한다.
    - 주문은 **`주문 ID`**, **`고객명`**, **`주문 날짜`**, **`주문 상태`** 등의 속성을 가진다.
    - 외부 시스템에서 JSON 형태의 주문 데이터를 HTTP 통신을 통해 받아와 내부 시스템에 저장한다.

2. 데이터 저장 및 조회
    - 받아온 주문 데이터는 메모리에 저장하며, 별도의 데이터베이스 연동은 고려하지 않는다.
    - 저장된 데이터는 ID를 통해 조회할 수 있다.
    - 저장된 주문 데이터는 리스트 형식으로 조회할 수 있다.

3. 주문 데이터를 내부 시스템에서 JSON 형태로 변환해, 외부 시스템으로 전송하는 기능을 구현한다.

<br/><br/>

# 💻 프로그래밍 요구사항

1. 외부 시스템과의 데이터 통신을 위한 인터페이스를 설계하고, 주요 클래스와 이들 간의 관계를 설명한다.
2. 데이터 연동 시 발생할 수 있는 다양한 예외 상황을 고려한다.
3. 설계의 유연성과 확장성을 고려한다.

<br/><br/><br/><br/>

# ⌨️ 실행

프로그램 실행 전, application.yml에 아래 **`목 서버 URL`** 을 입력합니다.

```yaml
external:
  url: ${URL}

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html

```

> https://86a4c5b5-5085-4b7a-8943-67ae4b031c30.mock.pstmn.io/api/orders

<br/><br/><br/><br/>

프로그램을 빌드한 후, 실행합니다. HTTP 요청은 http 폴더 내의 run.http를 통해 실행할 수 있습니다.

```shell
$ ./gradlew build
$ ./gradlew bootRun
```

<br/><br/><br/><br/>

프로그램을 실행한 후, 아래 주소로 가면 API 스펙을 볼 수 있습니다. API 문서는 간단히 Swagger를 사용했습니다.

```text
http://localhost:8080/swagger-ui/index.html
```

<br/><br/><br/><br/>

#  패키지

패키지는 크게 core/common으로 구분하고 있으며, core에서 각 패키지는 도메인 별로 구분하고 있습니다.

```shell
├── common              # 공통
└── core                # 비즈니스 로직
    ├── common
    └── order
        ├── controller  # 컨트롤러
        ├── domain      # 도메인
        ├── exception   # 예외
        ├── external    # 외부 계층 호출
        ├── facade      # 퍼사드
        ├── repository  # 영속
        └── service     # 서비스
```

<br/><br/><br/><br/>

# 📖 Contents

주요 컴포넌트는 Order, Command, OrderExternalComponent 입니다. Order는 주문 엔티티로 결제 정보를 담고 있습니다. 

```java
@Getter
public class Order extends BaseEntity {

    private final Long id;
    private final String name;
    private final BigDecimal totalPrice;
    private final String customerName;
    private final OrderStatus orderStatus;
    private final LocalDateTime orderDate;
    
    ......
   
}
```

<br/><br/><br/><br/>

Command는 외부 시스템에서 넘어온 값을 우리 서비스의 도메인 객체로 변환해 내부 시스템으로 들여보내는 클래스 입니다. 외부 시스템의 값을 그대로 사용하지 않고 한 번 변환해 사용하는 것입니다.

```java
public record OrderSaveCommand(
    Long orderId,
    String name,
    String customerName,
    BigDecimal totalPrice,
    OrderStatus orderStatus,
    LocalDateTime orderDate
) {
    public Order toEntity() {
        return new Order(
            orderId,
            name,
            totalPrice,
            customerName,
            orderStatus,
            orderDate
        );
    }
}
```

<br/><br/><br/><br/>

OrderExternalComponent는 외부 시스템을 호출하는 컴포넌트 입니다. 외부 서버와 통신과정 중, **`서버의 일시적 다운`**, **`타임아웃`** 에 대비하기 위해 **`서버 에러`** 에 대해서는 최대 3번까지 Retry를 시도합니다. 

```java
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderExternalComponent {

    @Value("${external.url}")
    private String url;

    private final OrderDataValidator validator;
    private final RestTemplate restTemplate;

    @Retryable(
        retryFor = {HttpServerErrorException.class, ResourceAccessException.class},
        backoff = @Backoff(delay = 1_000, maxDelay = 3_000, multiplier = 1.5, random = true)
    )
    public OrderSaveCommand fetchOrderData(final Long orderId) {
        try {
            final String url = this.url + orderId;
            final ResponseEntity<OrderDataResponse> response = restTemplate.getForEntity(url, OrderDataResponse.class);
            validator.validate(response);
            final OrderDataResponse payload = response.getBody();
            return createCommand(payload);
        } catch (HttpClientErrorException | IllegalArgumentException ex) {
            log.error("주문 데이터를 받아오는데 실패했습니다. 주문 ID:{}", orderId);
            throw new InvalidDataFetchingException(INVALID_ARGUMENT);
        }
    }

   ......

    @Recover
    public OrderSaveCommand recover(
        final ExternalDataFetchingFailedException ex,
        final Long orderId
    ) {
        log.error("모든 재시도가 실패했습니다. 주문 ID: {}", orderId, ex);
        throw ex;
    }
}
```

<br/><br/><br/><br/>

외부 서버와의 통신이 성공하면 validator를 통해 데이터를 검증하고 도메인 객체로 변환해 서비스에서 처리합니다.

```java
@Component
public class OrderDataValidator {

    public void validate(final ResponseEntity<OrderDataResponse> response) {
        if (response == null) {
            throw new InvalidDataFetchingException(BAD_GATEWAY);
        }
        ......
        if (payload.getOrderDate() == null) {
            throw new InvalidDataFetchingException(INVALID_ARGUMENT);
        }
    }
}
```

<br/><br/><br/><br/>

응답 코드/메시지는 CodeAndMessage를 통해 관리하며 동일한 코드라도 **`이름`** 을 통해 구분할 수 있습니다.

```java
public interface CodeAndMessage {
    int getCode();

    String getMessage();
}

```

```java
public enum CommonCodeAndMessage implements CodeAndMessage {
    OK(200, "OK"),
    CREATED(201, "Created"),
    INVALID_ARGUMENT(400, "올바른 파라미터를 입력해주세요."),
    BAD_REQUEST(400, "올바른 파라미터를 입력해주세요"),
    INVALID_DATA(400, "데이터가 올바른 양식이 아닙니다."),
    EMPTY_DATA(400, "외부 데이터를 받아오는데 실패했습니다."),
    INVALID_URL(404, "올바르지 않은 URL 입니다."),
    INTERNAL_SERVER(500, "서버 내부 오류입니다."),
    BAD_GATEWAY(502, "외부 서버와 통신하는 과정에서 에러가 발생했습니다.");

    .......
   
}
```
