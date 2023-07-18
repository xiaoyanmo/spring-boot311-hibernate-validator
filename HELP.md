# Spring Boot Hibernate Validator
### Hibernate Validator

---

1. #### Hibernate Validator的Maven坐标:
~~~xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
~~~
2. #### 实体类Person:
~~~java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @NotBlank(message = "name,不允许为空")
    @NotNull(message = "name,不允许为空")
    private String name;
    @Min(value = 1, message = "age,至少为1")
    @Max(value = 200, message = "age,不能超过200")
    private int age;
    @NotNull(message = "birthday,不允许为空")
    private Date birthday;
}
~~~
3. #### 测试接口类:
~~~java
import com.example.demo.entities.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RestTemplateController {
    @GetMapping("/getMsg")
    public String getMsg() {
        return "Hello World!";
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {

        return ResponseEntity.ok(person);
    }
}
~~~
4. #### 处理响应体结构:
~~~java

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode httpStatus, WebRequest request) {
        Map<String, Object> objectBody = new LinkedHashMap<>();
        objectBody.put("Current Timestamp", new Date());
        objectBody.put("Status", httpStatus.value());

        // 获得所有错误消息
        List<String> exceptionalErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        objectBody.put("Errors", exceptionalErrors);

        return new ResponseEntity<>(objectBody, httpStatus);
    }
}

~~~
5. #### 调用接口地址:
| 请求方法   | 接口地址                           | 请求体内容  | 响应体内容                          |
|--------|--------------------------------|--------------------------------|--------------------------------|
| POST   | http://localhost:8080/person   |{"name":"xxx","age":0,"birthday":"2023-07-15 15:40:21"} | {"Current Timestamp":"2023-07-15 17:09:51","Status":400,"Errors":["age,至少为1"]} |


