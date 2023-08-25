# MVC - 구조 이해

<br>

## 스프링 MVC 전체 구조

---

이전 포스팅들에서 스프링을 사용하지 않고 직접 만든 `MVC 프레임워크`의 구조와 `spring MVC` 구조를 비교해 보면 거의 유사하다.

<br/>

#### 직접 만든 MVC 프레임워크 구조

<img src="https://velog.velcdn.com/images/daydream/post/403e5b70-a2a3-4a05-a7e8-4ac64df77b0a/image.png" width="750">

<br/><br/>

#### Spring MVC 구조

<img src="https://velog.velcdn.com/images/daydream/post/4b88e897-bd90-4174-9f69-d9203029f6f2/image.png" width="750">

<br/>

우리가 `FrontController`라 부르던 서블릿은 `DispatcherServlet`으로 불리는 등 명칭만 달라졌고 역할은 동일하다. 명칭이 달라진 부분은 아래와 같다

<br/>

|직접 만든 MVC 프레임워크|Spring MVC|
|---|---|
|FrontController|DispatcherServlet|
|handlerMappingMap|HandlerMapping|
|MyHandlerAdapter|HandlerAdapter|
|ModelView|ModelAndView|
|viewResolver|ViewResolver|
|MyView|View|

<br/>

우선 스프링 MVC 구조에서 가장 중요한 `DispatcherServlet`의 일부 코드를 살펴보자.

<br/><br/>

### DispatcherServlet 구조

<br/>

`DispacherServlet`도 부모 클래스에서 `HttpServlet`을 상속받아서 사용하고, 서블릿으로 동작한다.

* `DispatcherServelt` → `FrameworkServlet` → `HttpServletBean` → `HttpServlet`

![](https://velog.velcdn.com/images/daydream/post/4470227d-2e8b-4247-b6df-f6b7e0c66fc6/image.png)

<br/>

스프링 부트 구동시 `DispatcherServlet`을 서블릿으로 자동 등록하며 **`모든 경로(urlPatterns="/")`** 에 대해 매핑한다. 즉, Spring MVC 역시 프론트 컨트롤러 패턴으로 구현되어 있고 `DispatcherServlet`이 프론트 컨트롤러의 역할을 한다.

<br/>

#### ✅ 참고

> 더 자세한 경로가 우선순위가 높다. 그래서 기존에 등록한 서블릿도 함께 동작한다.

<br/><br/>

### 요청 흐름

① 서블릿이 호출되면 `HttpServlet`이 제공하는 `service()` 메서드가 호출된다.
② 스프링 MVC는 `DispatcherServlet`의 부모인 `FrameworkServlet`에서 `service()`를 오버라이드 해뒀다.
③ `FrameworkServlet.service()`를 시작으로 여러 메서드가 실행되며 `DispatcherServlet.doDispatch()`가 호출된다.

<br/>

### DispatcherServlet.doDispatch() 핵심 로직

<br/>

```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {

    HttpServletRequest processedRequest = request;
    HandlerExecutionChain mappedHandler = null;
    ModelAndView mv = null;

    // 1. 핸들러 조회
    mappedHandler = getHandler(processedRequest);
    if (mappedHandler == null) {
        noHandlerFound(processedRequest, response);
        return;
    }
    
    // 2. 핸들러 어댑터 조회 핸들러를 처리할 수 있는 어댑터
    HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
    
    /**
     * 3. 핸들러 어댑터 실행 
     * 4. 핸들러 어댑터를 통해 핸들러 실행 
     * 5. ModelAndView 반환 mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
     */
	
    mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

    processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
}

private void processDispatchResult(HttpServletRequest request,
                                   HttpServletResponse response, 
                                   HandlerExecutionChain mappedHandler, 
                                   ModelAndView mv, Exception exception) throws Exception {
    // 뷰 렌더링 호출
    render(mv, request, response);
}

protected void render(ModelAndView mv, HttpServletRequest request,
                      HttpServletResponse response) throws Exception {
    View view;
    String viewName = mv.getViewName(); // 6. 뷰 리졸버를 통해서 뷰 찾기, 7.View 반환
    view = resolveViewName(viewName, mv.getModelInternal(), locale, request);
    
    // 8. 뷰 렌더링
    view.render(mv.getModelInternal(), request, response);
}
```

<br/>

#### mappedHandler = getHandler(processedRequest);

* 요청에 맞는 적절한 핸들러를 찾아 반환해 준다.

<br/>

#### noHandlerFound(processedRequest, response);

* 적절한 핸들러를 찾지 못한 경우 404 에러코드를 반환해 준다.

<br/>

#### HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

* 찾은 핸들러를 처리할 수 있는 핸들러 어댑터를 찾아준다.
* 만약 찾지 못할 경우 `ServletException` 발생

<br/>

#### mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

* 찾은 핸들러 어댑터를 이용해 로직을 수행하는 handle 메서드를 호출한다.
* 결과로 `ModelAndVIew`를 반환받고, 이를 이용해 렌더링까지 수행된다.

<br/>

#### processDispatchResult(processedRequest, response, mappedHandler, mv,           dispatchException);

* 실제 코드는 복잡하게 돼있는데 결국 `render()` 메서드를 호출해 준다.
* `render()`에서는 `ModelAndView`에서 `View`를 찾아 뷰 리졸버를 이용해 뷰의 물리적 이름을 완성해서 `forward` 해준다.

<br/><br/>

### Spring MVC 동작 순서

<img src="https://velog.velcdn.com/images/daydream/post/c9bc6fcf-8541-495a-b2d1-6d34f09a9a6a/image.png" width="750">

<br/>

1. **핸들러 조회** : 핸들러 매핑을 통해 URL에 매핑된 핸들러(컨트롤러) 조회

2. **핸들러 어댑터 조회** : 핸들러를 실행할 수 있는 핸들러 어댑터 조회

3. **핸들러 어댑터 실행** : 핸들러 어댑터 실행

4. **핸들러 실행** : 핸들러 어댑터가 실제 핸들러를 실행

5. **ModelAndView 반환** : 핸들러 어댑터는 핸들러가 반환하는 정보를 `ModelAndView`로 **변환**해 반환.

<br/>

6. **viewResolver 호출** : 뷰 리졸버를 찾아 실행한다.

* JSP : `InternalResourceViewResolver`가 자등 등록되어 사용된다.

<br/>

7. **View 반환** : 뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고 렌더링 역할을 담당하는 뷰 객체 반환.

* JSP : `InternalResourceView(JstlView)`를 반환하는데, 내부에는 `forward()`가 있다.

<br/>

8. **뷰 렌더링** : 뷰를 통해서 뷰를 렌더링 한다.

<br/><br/>

### 인터페이스 살펴보기

`Spring MVC`는 `DispatcherServlet` 코드 변경을 하지 않고도 원하는 기능을 변경하거나 확장을 할 수 있다. 그리고 이런 인터페이스들을 구현해 `DispatcherServlet`에 등록하면 커스텀 컨트롤러를 만들 수도 있다.

<br/><br/>

### 주요 인터페이스 목록

* `핸들러 매핑` : `org.springframework.web.servlet.HandlerMapping`
* `핸들러 어댑터` : `org.springframework.web.servlet.HandlerAdapter`
* `뷰 리졸버` : `org.springframework.web.servlet.ViewResolver`
* `뷰` : `org.springframework.web.servlet.View`

<br/><br/>

## 핸들러 매핑과 핸들러 어댑터

---

* 핸들러 매핑과 핸들러 어댑터에 대해 좀 더 자세히 알아보자.


* 과거에 주로 사용했던 스프링이 제공하는 간단한 컨트롤러로 핸들러 매핑과 어댑터를 이해해 보자.

<br/><br/>

### Controller 인터페이스

* 과거 버전의 스프링 컨트롤러 인터페이스

`org.springframework.web.servlet.mvc.Controller`

```java
public interface Controller {

ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
```

<br/>

#### ✅ 참고

> * `Controller` 인터페이스는 `@Controller` 어노테이션과는 전혀 다르다.

<br/><br/>

#### 예제 - OldController

```java
@Component("/springmvc/old-controller")
public class OldController implements Controller {
    
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return null;
    }
}
```

<br/>

* `@Component` : 이 컨트롤러는 `/springmvc/old-controller`라는 이름의 스프링 빈으로 등록되었다.
* Bean의 이름으로 **URL을 매핑**할 것이다
* 콘솔에 `OldController.handleRequest`이 출력되면 성공이다.

<br/><br/>

#### 어떻게 URL을 통해 해당 컨트롤러를 찾을 수 있는 것인가 ❓

* 위 컨트롤러가 호출되기 위해서는 2가지가 필요하다.

<br/><br/>

#### HandlerMapping
* 핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야 한다.
* ex) `스프링 빈의 이름으로 핸드러를 찾을 수 있는 핸들러 매핑`이 필요하다.

<br/>

#### HandlerAdapter
* 핸들러 매핑을 통해 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.
* ex) `Controller` 인터페이스를 실행할 수 있는 핸들러 어댑터를 찾아야 한다.

<br/><br/>

스프링은 이미 필요한 핸들러 매핑과 핸들러 어댑터를 대부분 구현해두었다. 개발자가 직접 핸들러 매핑과 핸들러 어댑터를 만드는 일은 거의 없다.

<br/><br/>

**스프링 부트가 자동 등록하는 핸들러 매핑과 핸들러 어댑터 (일부)**

<br/>

#### HandlerMapping
```
0 = RequestMappingHandlerMapping : 어노테이션 기반의 컨트롤러인 @Requestmapping에서 사용한다. 
1 = BeanNameUrlHandlerMapping    : 스프링 빈의 이름으로 핸들러를 찾는다.
```

<br/>

#### HandlerAdapter
```
0 = RequestmappingHandlerAdapter  : 어노테이션 기반의 컨트롤러인 @Requestmapping에서 사용한다. 
1 = HttpRequestHandlerAdapter     : HttpRequesthandler 처리
2 = SimpleControllerHandlerAdapter: Controller 인터페이스(어노테이션 X) 처리 
```

<br/>

핸들러 매핑도, 핸들러 어댑터도 모두 순서대로 찾고 만약 없으면 다음 순서로 넘어간다.

<br/><br/>

### 1. 핸들러 매핑으로 핸들러 조회
* `HandlerMapping`을 순서대로 실행해서, 핸들러를 찾는다. 


* 이 경우 빈 이름으로 핸들러를 찾아야 하기 때문에 이름 그대로 빈 이름으로 핸들러를 찾아주는 `BeanNameUrlHandlerMapping`가 실행에 성공하고 핸들러인 `OldController`를 반환한다.

<br/>

### 2. 핸들러 어댑터 조회
* `HandlerAdapter`의 `supports()`를 순서대로 호출한다.


* `SimpleControllerHandlerAdapter`가 `Controller` 인터페이스를 지원하므로 대상이 된다.

<br/>

### 3. 핸들러 어댑터 실행
* 디스패처 서블릿이 조회한 `SimpleControllerHandlerAdapter`를 실행하면서 핸들러 정보도 함께 넘겨준다.


* `SimpleControllerHandlerAdapter`는 핸들러인 `OldController`를 내부에서 실행하고, 그 결과를 반환한다.

<br/><br/>

#### 정리 - OldController 핸들러 매핑, 어댑터

`OldController`를 실행하면서 사용된 객체는 다음과 같다.

`HandlerMapping = BeanNameUrlHandlerMapping`

`HandlerAdapter = SimpleControllerHandlerAdapte`

<br/><br/>

## 뷰 리졸버

---

View의 논리 이름을 물리 이름으로 완성시켜주는 뷰 리졸버를 `spring MVC`에서는 어떻게 만들어줄까 ❓

위에서 만든 `OldController`는 현재는 반환값이 null이라 URL을 요청해도 어떤 화면도 안 나오지만 조회가 되도록 변경해 보자.

<br/>

### OldController - View 조회 되도록 변경

```java
@Override
public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    System.out.println("OldController.handleRequest");
    return new ModelAndView("new-form");
}
```

<br/>

* View를 사용할 수 있도록 `new ModelAndView("new-form")`을 반환

<br/>

`http://localhost:8080/springmvc/old-controller` 경로로 접속해서 동작이 되는지 확인을 해보자.
컨트롤러 호출은 되지만, Whitelabel Error Page 오류가 발생한다.

`new-form`이라는 `viewPath`를 물리 이름으로 완성하기 위해선 어떤 경로인지 상위 경로 `prefix`와 이게 html 인지 jsp 인지 확장자를 저장한 `suffix`가 없기 때문이다.

<br/>

`application.properties` 파일에 아래 코드를 추가하고 실행하면 정상적으로 동작할 것이다.

```yml
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
```

<br/><br/>

### 뷰 리졸버 - InternalResourceViewResolver

스프링 부트는 `InternalResourceViewResolver`라는 뷰 리졸버를 자동 등록하는데, 이때 설정 파일에서 작성한 `spring.mvc.view.prefix`, `spring.mvc.view.suffix` 설정 정보를 사용한다.

<br/>

### 뷰 리졸버 동작 방식

<img src="https://velog.velcdn.com/images/daydream/post/46f537ee-a81e-461e-8e8d-0495803169f5/image.png" width="750">

<br/>

`spring MVC` 구조 6번, 7번 과정에서 뷰 리졸버가 동작한다.

스프링 부트는 구동시 자동 등록하는 뷰 리졸버가 많은데, 몇 가지만 살펴보자.

<br/>

```
1 = BeanNameViewResolver         : 빈 이름으로 뷰를 찾아 반환한다. 
2 = InternalResourceViewResolver : JSP를 처리할 수 있는 뷰를 반환한다.
```

<br/><br/>

#### 1. 핸들러 어댑터 호출
핸들러 어댑터를 통해 `new-form`이라는 논리 뷰 이름을 획득한다.

<br/>

#### 2. ViewResolver 호출
* `new-form`이라는 뷰 이름으로 viewResolver를 순서대로 호출한다.
* `BeanNameViewResolver`는 `new-form`이라는 이름의 스프링 빈으로 등록된 뷰를 찾아야 하는데 없다.
* `InternalResourceViewResolver`가 호출된다.

<br/>

#### 3. InternalResourceViewResolver
이 뷰 리졸버는 `InternalResourceView`를 반환한다.

<br/>

#### 4. 뷰 - InternalResourceView
`InternalResourceView`는 JSP처럼 포워드 `forward()`를 호출해서 처리할 수 있는 경우에 사용한다.

<br/>

#### 5. view.render()
`view.render()`가 호출되고 `InternalResourceView`는 `forward()`를 사용해서 JSP를 실행한다.

<br/><br/>

#### ✅ 참고
> `InternalResourceViewResolver`는 만약 JSTL 라이브러리가 있으면 `InternalResourceView`를 상속받은 `JstlView`를 반환한다. `JstlView`는 JSTL 태그 사용 시 약간의 부가 기능이 추가된다.

> 다른 뷰는 실제 뷰를 렌더링 하지만, JSP의 경우 `forward()`를 통해서 해당 JSP로 이동(실행) 해야 렌더링이 된다. JSP를 제외한 나머지 뷰 템플릿들은 `forward()` 과정 없이 바로 렌더링 된다.

> Thymeleaf 뷰 템플릿을 사용하면 `ThymeleafViewResolver`를 등록해야 한다. 최근에는 라이브러리만 추가하면 스프링 부트가 이런 작업도 모두 자동화해준다.

<br/><br/>

## 스프링 MVC - 시작하기

---

스프링이 제공하는 컨트롤러는 어노테이션 기반으로 동작해서, 매우 유연하고 실용적이다. 과거에는 자바 언어에 어노테이션이 없기도 했고, 스프링도 처음부터 이런 유연한 컨트롤러를 제공한 것은 아니다

<br/>

### @RequestMapping

스프링은 어노테이션을 활용한 매우 유연하고, 실용적인 컨트롤러를 만들었는데 이것이 바로
`@RequestMapping` 어노테이션을 사용하는 컨트롤러이다.

<br/>

#### `@RequestMapping`
* `RequestMappingHandlerMapping`
* `RequestMappingHandlerAdapter`

<br/>

가장 우선순위가 높은 핸들러 매핑과 핸들러 어댑터는 `RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter`이다.

`@RequestMapping`의 앞 글자를 따서 만든 이름인데, 이것이 바로 지금 스프링에서 주로 사용하는 어노테이션 기반의 컨트롤러를 지원하는 핸들러 매핑과 어댑터이다. 실무에서는 99.9% 이 방식의 컨트롤러를 사용한다고 한다.

지금까지 만들었던 프레임워크에서 사용했던 컨트롤러를 `@RequestMapping` 기반의 `Spring MVC` 컨트롤러로 변경해 보자.

<br/><br/>

#### SpringMemberFormControllerV1 - 회원 등록 폼

```java
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
```

<br/>

#### @Controller

스프링이 자동으로 스프링 빈으로 등록한다. (내부에 `@Component`이 있어서 컴포넌트 스캔의 대상) `Spring MVC`에서 어노테이션 기반 컨트롤러로 인식한다.

<br/>

#### @RequestMapping

요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다.
어노테이션을 기반으로 동작하기 때문에, 메서드의 이름은 임의로 지으면 된다.

<br/>

#### ModelAndView

모델과 뷰 정보를 담아서 반환하면 된다.

<br/><br/>

#### SpringMemberSaveControllerV1 - 회원 가입

```java
@Controller
public class SpringMemberSaveControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("springmvc/v1/members/save")
    public ModelAndView process(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        Member save = memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member", member);
        return mv;
    }
    
}
```

<br/>

#### mv.addObject("member", member)

스프링이 제공하는 `ModelAndView`를 통해 Model 데이터를 추가할 때는 `addObject()`를 사용하면 된다. 이 데이터는 이후 뷰를 렌더링 할 때 사용된다.

<br/><br/>

#### SpringMemberListControllerV1 - 회원 목록 조회

```java
@Controller
public class SpringMemberListControllerV1 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members")
    public ModelAndView process() {
        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", members);

        return mv;
    }

}
```

<br/><br/>

#### ✅ 참고

> `@Controller` 어노테이션이 없어도 직접 설정 영역에서 빈으로 등록해 줘도 된다.

> 클래스 영역에 `@RequestMapping`과 `@Component` 어노테이션을 사용하면 정상적으로 등록되어 사용할 수 있다.

<br/><br/>

## 스프링 MVC - 컨트롤러 통합

---

`@RequestMapping`을 잘 보면 클래스 단위가 아니라 메서드 단위에 적용된 것을 확인할 수 있다. 따라서 컨트롤러 클래스를 유연하게 하나로 통합할 수 있다

<br/><br/>

#### SpringMemberControllerV2

```java
/**
 * 클래스 단위 -> 메서드 단위
 * @RequestMapping 클래스 레벨과 메서드 레벨 조합
 */
@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        return new ModelAndView("new-form");
    }

    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        Member save = memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member", member);
        return mv;
    }

    @RequestMapping
    public ModelAndView members() {
        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", members);

        return mv;
    }
}
```

<br/>

### 조합
컨트롤러 클래스를 통합하는 것을 넘어 조합도 가능한데, 

클래스 레벨 `@RequestMapping("/springmvc/vX/member")` 어노테이션을 추가해 주고, 각각의 메서드 레벨에서 중복 경로를 제거해 주면 클래스 레벨과 메서드 레벨이 조합돼서 등록된다.

<br/>

### 조합 결과

* 클래스 레벨 `@RequestMapping("/springmvc/vX/member")`
    * 메서드 레벨 `@RequestMapping("/new-form")` → `/springmvc/vX/members/new-form`
    * 메서드 레벨 `@RequestMapping("/save")` → `/springmvc/vX/members/save`
    * 메서드 레벨 `@RequestMapping` → `/springmvc/vX/members`

<br/><br/>

## 스프링 MVC - 실용적인 방식

---

<br/>

#### SpringMemberControllerV3

```java
@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    // @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    @GetMapping("/new-form")
    public String newForm() {
        return "new-form";
    }

    // @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(@RequestParam("username")String username,
                       @RequestParam("age")int age,
                       Model model) {

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";
    }

    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
        return "members";
    }
    
}
```

<br/>

#### Model 파라미터
`save()`, `members()`를 보면 Model을 파라미터로 받는 것을 확인할 수 있다. `Spring MVC`도 이런 편의 기능을 제공한다.

<br/>

#### ViewName 직접 반환
뷰의 논리 이름을 반환할 수 있다.

<br/>

#### @RequestParam 사용
스프링은 HTTP 요청 파라미터를 `@RequestParam`으로 받을 수 있다.
`@RequestParam("username")`은 `request.getParameter("username")`와 거의 같은 코드라 생각하면 된다. 물론 GET 쿼리 파라미터, POST Form 방식을 모두 지원한다.

<br/>

#### @RequestMapping 👉 @GetMapping, @PostMapping
`@RequestMapping`은 URL만 매칭하는 것이 아니라, HTTP Method도 함께 구분할 수 있다.
예를 들어서 URL이 `/new-form`이고, HTTP Method가 GET인 경우를 모두 만족하는 매핑을 하려면 다음과 같이 처리하면 된다.

<br/>

```java
@RequestMapping(value = "/new-form", method = RequestMethod.GET)
```

<br/>

이것을 `@GetMapping`, `@PostMapping`으로 더 편리하게 사용할 수 있다.

참고로 Get, Post, Put, Delete, Patch 모두 어노테이션이 준비되어 있다.