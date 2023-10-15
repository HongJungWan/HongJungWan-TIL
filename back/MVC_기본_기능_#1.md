# MVC 기본 기능 #1

<br>

#### ✅ 참고

Packaging을 War가 아니라 Jar를 선택해야 한다. JSP를 사용하지 않기에 Jar를 사용하는 것이 좋고 스프링 부트를 사용하면 이 방식을 주로 사용하게 된다.

Jar를 선택하면 항상 내장 서버(톰캣)를 사용하고 webapp 경로도 사용하지 않는다. 내장 서버 사용에 최적화된 기능이다. War는 내장 서버도 사용 가능하지만, 주로 외부 서버에 배포하는 목적으로 사용한다.

<br><br><br>

## 로깅

지금까지는 콘솔 창에 실행 결과나 기댓값을 `System.out.println("")`을 통해 출력을 했다.
하지만 실제 운영을 할 때는 시스템 콘솔이 아닌 별도의 로깅 라이브러리를 사용해 출력한다.
많은 로깅 라이브러리 중 `SLF4J`, `Logback` 정도만 알아보자.

<br><br><br>

### 로깅 라이브러리

스프링 부트 라이브러리를 사용할 경우 스프링 부트 로깅 라이브러리(spring-boot-starter-loggin)이 포함되는데 이 라이브러리는 내부에 다음 로깅 라이브러리가 사용된다.

* SLF4J = `http://www.slf4j.org`
* Logback = `http://logback.qos.ch`

<br><br><br>

로그 라이브러리는 `Logback`, `Log4J`, `Log4J2` 등 정말 많은 라이브러리를 통합해서 제공하는 게 `SLF4J`이고 이 인터페이스의 구현체로 `Logback` 과 같은 로그 라이브를 선택해서 사용한다.

<br><br><br>

#### 1. 클래스 참조 변수 선언

```java
/* getClass() 메서드를 통해 사용되는 클래스 타입 반환하여 삽입 */
private Logger log = LoggerFactory.getLogger(getClass());

/* 직접적으로 해당 클래스 타입을 입력해 줘도 된다. */
private static final Logger log = LoggerFactory.getLogger(Xxx.class);
```

<br>

#### 2. Lombok 사용

```java
@Slf4j
public class TestController {
	...
}
```

<br>

#### LogTestController

```java
@RestController
public class LogTestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);
        return "ok";
    }
}
```

<br><br>

#### 매핑 정보

* `@RestController`

    * `@Controller`는 반환 값이 `String`이면 뷰 이름으로 인식된다. 그래서 **뷰를 찾고 뷰가 렌더링 된다.**

    * `@RestController`는 반환 값으로 뷰를 찾는 것이 아니라, **HTTP 메시지 바디에 바로 입력**한다. 따라서 실행 결과로 ok 메시지를 받을 수 있다.

<br>

#### 테스트

* 로그가 출력되는 포맷 확인

    * 시간, 로그 레벨, 프로세스 ID, 스레드 명, 클래스명, 로그 메시지

<br>

* 로그 레벨 설정을 변경해서 출력 결과를 보자.

    * LEVEL : `TRACE > DEBUG > INFO > WARN > ERROR`

    * 개발 서버는 debug 출력

    * 운영 서버는 info 출력

<br>

* `@Slf4j`로 변경

<br><br><br>

#### 로그 레벨 설정 - application.properties

```yml
# 전체 로그 레벨 설정 (기본 info)
logging.level.root=info

# hello.springmvc 패키지와 그 하위 로그 레벨 설정
logging.level.hello.springmvc=debug
```

<br><br><br>

#### 올바른 로그 사용법

* `log.debug("data="+data)`

    * 로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data가 실제 실행이 되어 버린다.
      결과적으로 문자 더하기 연산이 발생한다.



* `log.debug("data={}", data)`

    * 로그 출력 레벨을 info로 설정하면 아무 일도 발생하지 않는다. 따라서 앞과 같은 의미 없는 연산이 발생하지 않는다.

<br><br><br>

#### 로그 사용 시 장점

* 스레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.


* 로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영 서버에서는 출력하지 않는 등 로그를 상황에 맞게 조절할 수 있다.


* 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 특히 파일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.


* 성능도 일반 System.out보다 좋다. (내부 버퍼링, 멀티 쓰레드 등) 그래서 실무에서는 꼭 로그를 사용해야 한다.

<br><br><br>

### SLF4J와 Logback에 대한 보충 설명

<br>

#### SLF4J (Simple Logging Facade for Java)

`추상화 레이어`: SLF4J는 다양한 로깅 프레임워크와 상호 작용하기 위한 추상화 레이어다. 

즉, SLF4J는 자체적으로 로그를 기록하지 않는다. 대신 개발자는 SLF4J API를 사용하여 로깅을 수행하고, 실제 로깅은 선택된 로깅 프레임워크 (예: Logback, log4j, JUL 등)를 통해 이루어진다.

`로깅 구현체 전환의 유연성`: SLF4J를 사용하면, 애플리케이션 코드를 변경하지 않고도 로깅 구현체를 변경할 수 있다. 즉, log4j에서 Logback으로 또는 그 반대로 변경하는 것이 간단하다.

<br>

#### Logback

`SLF4J의 구현체`: Logback은 SLF4J의 구현체 중 하나다. 즉, SLF4J 인터페이스를 통해 호출된 로깅 작업을 실제로 처리하는 구체적인 로거다.

`성능`: Logback은 성능 최적화와 유연성을 목표로 개발되었습니다. 특히, log4j 1.x에 비해 많은 개선사항을 포함하고 있다.

`XML 기반의 구성`: Logback 설정은 XML 파일을 통해 이루어지며, 이를 통해 로깅 레벨, 패턴, 출력 대상 등을 세밀하게 설정할 수 있다.

`동적 재구성`: Logback은 런타임 중에 설정을 변경하고 이를 자동으로 감지하고 반영할 수 있다.

<br>

요약하면, SLF4J는 로깅 API의 추상화를 제공하는 반면, Logback은 실제 로깅 작업을 수행하는 구현체다. 

따라서 일반적으로 SLF4J API를 사용하여 애플리케이션 내에서 로깅을 수행하고, Logback 같은 구현체를 통해 실제 로그를 출력한다.

<br><br><br>

### 요청 매핑

<br>

#### MappingController

```java
@RestController
public class MappingController {
   
   private Logger log = LoggerFactory.getLogger(getClass());
   
   /**
   * 기본 요청
   * 둘 다 허용 /hello-basic, /hello-basic/
   * HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
   */
   
   @RequestMapping("/hello-basic")
   public String helloBasic() {
       log.info("helloBasic");
       return "ok";
   }
}
```

<br>

* `@RequestMapping("/hello-basic")`

    * `/hello-basic` URL 호출이 오면 이 메서드가 실행되도록 매핑한다.

    * 대부분의 속성을 `배열[]`로 제공하므로 다중 설정이 가능하다. `{"/hello-basic", "/hello-go"}`

<br>

#### HTTP 메서드

* `@RequestMapping`에 `method` 속성으로 HTTP 메서드를 지정하지 않으면 HTTP 메서드와 무관하게 호출된다. `모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE`

<br><br><br>

#### PathVariable (경로 변수) 사용

```java
/**
 * PathVariable 사용
 * 변수명이 같으면 생략 가능
 * @PathVariable("userId") String userId -> @PathVariable userId
 */
 
@GetMapping("/mapping/{userId}")
public String mappingPath(@PathVariable("userId") String data) {
   	log.info("mappingPath userId={}", data);
   	return "ok";
}
```

<br>

최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호한다.

* `/mapping/userA`


* `/users/1`


* `@RequestMapping`은 URL 경로를 템플릿화 할 수 있는데, `@PathVariable`을 사용하면 매칭되는 부분을 편리하게 조회할 수 있다.


* `@PathVariable`의 이름과 파라미터 이름이 같으면 생략할 수 있다.

<br><br><br>

#### PathVariable 사용 - 다중

```java
// PathVariable 사용 다중

@GetMapping("/mapping/users/{userId}/orders/{orderId}")
public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
 	log.info("mappingPath userId={}, orderId={}", userId, orderId);
 	return "ok";
}
```

<br><br><br>

#### 미디어 타입 조건 매핑 - HTTP 요청 Content-Type, consume

```java
/**
 * Content-Type 헤더 기반 추가 매핑 Media Type
 * consumes="application/json",
 * consumes="!application/json",
 * consumes="application/*",
 * consumes="*\/*"
 * MediaType.APPLICATION_JSON_VALUE
 */
@PostMapping(value = "/mapping-consume", consumes = "application/json")
public String mappingConsumes() {
    log.info("mappingConsumes");
    return "ok";
}
```

<br>

* HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑한다.


* 일치하지 않을 경우 HTTP 415(Unsupported Media Type)을 반환한다.


* 조건을 배열로 설정할수도 있고 상수로 제공하는 매직넘버를 사용해도 된다.

```java
consumes = "application/json"
consumes = {"text/plain", "application/*"}
consumes = MediaType.TEXT_PLAIN_VALUE
```

<br><br><br>

#### 미디어 타입 조건 매핑 - HTTP 요청 Accept, produce

```java
/**
 * Accept 헤더 기반 Media Type
 * produces="text/html",
 * produces="!text/html",
 * produces="text/*",
 * produces="*\/*"
 */
@PostMapping(value = "/mapping-produces", produces = "text/html")
public String mappingProduces() {
    log.info("mappingProduces");
    return "ok";
}
```

* HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다.
* 만약 맞지 않으면 HTTP 406(Not Acceptable)을 반환한다.

<br><br><br>

## 요청 매핑 - API 예시

<br>

### 회원 관리 API

<br>

#### MappingClassController

```java
@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping
    public String user() {
        return "get users";
    }

    @PostMapping
    public String addUser() {
        return "add user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return "get userId= " + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update userId= " + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete userId= " + userId;
    }
    
}
```

<br>

#### @RequestMapping("/mapping/users")

* 클래스 레벨에 매핑 정보를 두면 메서드 레벨에서 해당 정보를 조합해서 사용

<br><br><br>

## HTTP 요청 - 기본, 헤더 조회

어노테이션 기반의 스프링 컨트롤러는 다양한 파라미터를 지원한다.

스프링에서는 아주 유연하게 컨트롤러의 메서드가 요구하는 파라미터를 정말 대부분 지원을 해주는데, 코드를 통해 알아보자.

<br><br><br>

#### RequestHeaderController

```java
@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie) {

        log.info("request = {}", request);
        log.info("response = {}", response);
        log.info("httpMethod = {}", httpMethod);
        log.info("locale = {}", locale);
        log.info("headerMap = {}", headerMap);
        log.info("host = {}", host);
        log.info("cookie = {}", cookie);

        return "ok";
    }
}
```

<br><br><br>

#### HttpMethod

* HTTP 메서드를 조회한다. (org.springframework.http.HttpMethod)

<br>

#### Locale

* Locale 정보를 조회한다. (ko-kr, euc-kr, kr ...)

<br>

#### @RequestHeader MultiValueMap<String, String> headerMap

* 모든 HTTP 헤더를 `MultiValueMap` 형식으로 조회한다.

<br>

#### @RequestHeader("host") String host

* 특정 HTTP 헤더를 조회한다.
* 속성
    * 필수 값 여부(required)
    * 기본 값 속성(defaultValue)

<br>

#### @CookieValue(value = "myCookie", required = false) String cookie

* 특정 쿠키를 조회한다.
* 속성
    * 필수 값 여부(required)
    * 기본 값 속성(defaultValue)

<br><br><br>

#### ✅ 참고 : MultiValueMap

* Map과 유사하지만 하나의 키에 여러 값을 받을 수 있다.


* HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용한다.
    * keyA=value1&keyA=value2

```java
MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
map.add("keyA", "value1");
map.add("keyA", "value2");

// [value1, value2]
List<String> values = map.get("keyA");
```

<br><br><br>

## HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form



HTTP 요청 메시지를 개발자가 사용하기 편하게 변환해 제공하는 것이 `HttpServletRequest` 객체다.

이 객체 내의 `getParameter()`를 이용하면 요청 파라미터를 조회할 수 있는데,  `queryString`으로 요청 메시지를 전달하는 것은 `GET`, `쿼리 파라미터 전송`과 `POST HTML Form` 전송 방식이다.

<br><br><br>

#### GET 쿼리 파라미터 전송

`http://localhost:8080/request-param?username=hello&age=20`

<br><br><br>

#### POST, HTML Form 전송

```java
POST /request-param ...
content-type: application/x-www-form-urlencoded

username=hello&age=20
```

<br><br><br>

위 두 방식은 모두 형식이 동일하기에 구분 없이 getParameter() 메서드를 이용해 조회할 수 있는데 이를 `요청 파라미터(request parameter) 조회`라 한다.

<br><br><br>

#### RequestParamController

```java
@Slf4j
@Controller
public class RequestParamController {

    /**
     * 서블릿 시절 사용하던 쿼리 스트링 추출 방식
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }
    
}
```

<br>

* 반환 타입이 없으면서 응답에 값을 직접 입력("ok") 하면 view 조회를 할 수 없다.


* 이전 서블릿 코드를 구현하던 시절과 같이 HttpServletRequest에서 getParameter로 요청 파라미터를 조회했다.

<br><br><br>

#### ✅ 참고

* Jar를 사용하면 webapp 경로 사용을 못 하기에 정적 리소스도 클래스 경로에 함께 포함해야 한다.

<br><br><br>

## HTTP 요청 파라미터 - @RequestParam



스프링이 제공하는 어노테이션인 @RequestParam을 사용하면 위에서 사용한 getParameter 메서드를 통해 꺼내는 대신 매개변수 레벨에서 더 빠르게 바로 꺼내 사용할 수 있다.

<br><br><br>

#### RequestParamController.requestParamV2

```java
/**
 * RequestParam 어노테이션을 활용해 내부 속성으로 쿼리 스트링의 Key를 작성해서 해당 key의 value 추출
 */
@RequestMapping("/request-param-v2")
@ResponseBody
public String requestParamV2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge) {
    log.info("username={}, age={}", memberName, memberAge);
    return "ok";
}
```

<br>

#### @RequestParam("username")

* 파라미터 이름으로 바인딩

<br>

#### @ResponseBody

* View 조회를 무시하고, HTTP message body에 직접 해당 내용을 입력


* 클래스 레벨에서 @Controller를 사용하는 경우 메서드 레벨에서 해당 어노테이션을 사용해서 메시지 바디에 직접 내용 입력하는 게 가능하다.

<br><br><br>

#### RequestParamController.requestParamV3

```java
@RequestMapping("/request-param-v3")
@ResponseBody
public String requestParamV3(@RequestParam String username, @RequestParam int age) {
    log.info("username={}, age={}", username, age);
    return "ok";
}
```

<br><br><br>

* HTTP 파라미터 이름이 변수 이름과 같을 경우 파라미터 속성 생략이 가능하다.

    * `@RequestParam("username") String username → @RequestParam String username`

<br><br><br>

#### RequestParamController.requestParamV4

```java
@RequestMapping("/request-param-v4")
@ResponseBody
public String requestParamV4(String username, int age) {
    log.info("username={}, age={}", username, age);
    return "ok";
}
```

* String, int, Integer 등의 단순 타입이면 @RequestParam도 생략이 가능하다.

<br><br><br>

지금까지는 `@RequestParam`의 속성 중 바인딩을 위한 요청 파라미터 이름만 사용했는데, 그 외에도 여러 속성이 있다. 다음 소개할 속성은 `required`라는 속성으로 해당 파라미터의 필수 여부를 설정할 수 있다.

<br><br><br>

#### RequestParamController.requestParamRequired

```java
@RequestMapping("/request-param-required")
@ResponseBody
public String requestParamRequired(@RequestParam(required = true) String username,
                                   @RequestParam(required = false) Integer age) {
    log.info("username={}, age={}", username, age);
    return "ok";
}
```

<br><br><br>

#### @RequestParam.required

* 파라미터 필수 여부
* 기본값은 파라미터 필수 (true)이다.


* 해당 파라미터를 공백(ex: `username=`)으로 전송하면 빈 문자로 통과된다.
* `required`가 `true`인 파라미터를 보내주지 않으면 400 예외(`BAD_REQUEST`)가 발생한다.


* 원시 타입은 `null`이 들어갈 수 없어서 `required`가 `false`여도 500 에러가 발생한다.
    * `int`형으로 에러가 발생하면 `Integer`같은 `wrapper` 타입을 사용해야 한다.
    * 혹은 기본값을 설정해 주는 `defaultValue`를 사용하면 된다.

<br><br><br>

속성 중 필수 여부 속성(required)를 설정할 수 있다. 그럼 필수인데 값을 매번 공통 초기값을 넣거나 기본값이 필요한 경우에는 어떻게 해야 할까? 속성 중에는 `defaultValue`라는 기본값 속성이 있다.

<br><br><br>

#### RequestParamController.requestParamDefault

```java
@RequestMapping("/request-param-default")
@ResponseBody
public String requestParamDefault(@RequestParam(defaultValue = "hong") String username,
                                  @RequestParam(defaultValue = "20") int age) {
    log.info("username={}, age={}", username, age);
    return "ok";
}
```

<br>

* 파라미터가 없는 경우 기본값으로 설정한 값이 적용된다.
* 이미 기본값이 있기에 `required`는 의미가 없어 빼도 된다.


* 빈 문자("")의 경우에도 설정한 기본 값이 적용된다.
    * 요청(?age=)을 공백 설정 시, `defaultValue`로 설정한 값이 적용되어 age에 20이 주입된다.

<br><br><br>

#### RequestParamController.requestParamMap

```java
@RequestMapping("/request-param-map")
@ResponseBody
public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
    log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
    return "ok";
}
```

<br>

* 파라미터를 `Map`, `MultiValueMap`으로 조회할 수 있다
    * `@RequestParam Map`
        * Map(key=value)


* `@RequestParam MultiValueMap`
    * MultiValueMap(key=[value1, value2]) (key=userIds, value=[id1, id2])


* 파라미터 값이 1개가 확실하면 `Map`을 써도 되지만 그렇지 않다면 `MultiValueMap`을 사용하자.

<br><br><br>

## HTTP 요청 파라미터 - @ModelAttribute

<br><br><br>

요청 파라미터가 많아질수록 코드는 길어지고 오류 확률은 올라간다.

이런 번거로운 부분을 자동화해주는 어노테이션을 스프링에서 제공해 주는데 그게 `@ModelAttribute`이다.

<br><br><br>

#### HelloData

```java
@Data
public class HelloData {
    private String username;
    private int age;
}
```

<br><br><br>

#### RequestParamController.modelAttributeV1

```java
@RequestMapping("/model-attribute-v1")
@ResponseBody
public String modelAttributeV1(@ModelAttribute HelloData helloData) {
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    return helloData.toString();
}
```

<br>

* `?username=hong&age=20` 쿼리 스트링을 담아 요청하면 HelloData 객체에 담겨서 사용할 수 있다.


* `스프링 MVC`는 `@ModelAttribute`가 있으면 아래와 같이 수행한다.

    1. `HelloData 객체 생성`
    2. 요청 파라미터의 이름으로 `HelloData` 객체 프로퍼티를 찾는다.
    3. 해당 프로퍼티의 `setter`를 호출해서 파라미터의 값을 바인딩
    4. 파라미터 명이 `username` 이면 `setUsername()` 메서드를 찾아 호출


* 만약 `age` 필드에 숫자가 아닌 문자 `age=hello`를 넣으려 하면 `BindException` 발생

<br><br><br>

#### RequestParamController.modelAttributeV2

* 생략 가능한 `@ModelAttribute` 어노테이션

<br>

```java
@RequestMapping("/model-attribute-v2")
@ResponseBody
public String modelAttributeV2(HelloData helloData) {
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    return helloData.toString();
}
```

<br>

* `@ModelAttribute`는 생략할 수 있는데 `@RequestParam`도 생략 가능하다. 스프링은 생략 시 아래와 같은 규칙을 적용한다.


* `String`, `int`, `Integer` 같은 단순 타입 = `@RequestParam`
* 나머지 = `@ModelAttribute` (`argument resolver`로 지정해둔 타입 제외)

<br><br><br>

## HTTP 요청 메시지 - 단순 텍스트



지금까지는 쿼리 스트링을 이용해 요청 파라미터를 전송했다. 그 외에도 `HTTP message body`에 데이터를 직접 담아 요청하는 방법도 있다.

HTTP API에서 주로 사용하며 `JSON, XML, TEXT ...` 거의 모든 데이터를 전송할 수 있다. 주로 JSON 형식의 데이터를 주고받을 때 많이 사용한다.

<br><br><br>

주의할 점은 요청 파라미터와는 다르게 HTTP 메시지 바디를 통해 데이터가 직접 넘어오는 경우는 `HTML Form` 방식을 제외하고는 `@RequestParam`, `@ModelAttribute`를 사용할 수 없다.

<br><br><br>

#### RequestBodyStringController.requestBodyStringV3

```java
@PostMapping("/request-body-string-v3")
public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity){

    log.info("messageBody={}", httpEntity.getBody());
    return new HttpEntity<>("ok");
}
```

<br>

* `HttpEntity` : HTTP header, body 정보를 편리하게 조회할 수 있게 해준다.

    * 메시지 바디 정보를 직접 조회 가능(`getBody()`)

    * 요청 파라미터를 조회하는 기능과 관계없다.(`@RequestParam`, `@ModelAttribute`)

    * 응답에서도 사용할 수 있다.
        * 헤더 정보 포함도 가능하지만, View 조회는 안된다.

<br><br><br>

#### ✅ 참고

스프링 MVC 내부에서 HTTP 메시지 바디를 읽어 문자나 객체로 변환해서 전달해 주는데, 이때 `HTTP 메시지 컨버터(HttpMessageConverter)` 기능을 사용한다.

<br><br><br>

#### RequestBodyStringController.requestBodyStringV4

<br>

```java
@ResponseBody
@PostMapping("/request-body-string-v4")
public String requestBodyStringV4(@RequestBody String body){

    log.info("messageBody={}", body);
    return "ok";
}
```

<br>

#### @RequestBody

* HTTP 메시지 바디 정보를 편리하게 조회하게 해주는 어노테이션으로 만약 바디가 아니라 헤더 정보가 필요하면 `HttpEntity`나 `@RequestHeader` 어노테이션을 사용하면 된다.


* 요청 파라미터를 조회하는 `@RequestParam`, `@ModelAttribute` 와는 관계가 없다.

<br><br><br>

#### ✅ 참고

요청 파라미터를 조회하는 기능은 `@RequestParam`, `@ModelAttribute`를 사용하고 HTTP 메시지 바디를 직접 조회하는 기능은 `@RequestBody`를 사용한다.

<br><br><br>