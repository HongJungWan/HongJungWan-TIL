# MVC 웹 애플리케이션 이해

<br>

## 웹 서버, 웹 애플리케이션 서버

---

### 웹 - HTTP 기반

<img src="https://velog.velcdn.com/images/daydream/post/2fdfbdd1-3ae0-4428-9813-7eb0dcc5ed28/image.png" width="600">

<br>

* 웹은 결국 HTTP 기반으로 통신한다.
* 클라이언트에서 URL을 입력하면 인터넷을 통해 서버에 접속해서 응답받은 HTML을 받아 보여준다.
* 거의 모든 형태의 데이터를 HTTP를 통해 주고받을 수 있다.

<br><br>

### 웹 서버(Web Server)

<img src="https://velog.velcdn.com/images/daydream/post/3bd6fa58-63aa-4c64-8bda-8a55d998c364/image.png" width="600">

<br>

* HTTP를 기반으로 동작하는 서버로써 정적 리소스와 기타 부가기능을 제공한다.

여기서 정적 파일은 HTML, CSS, JS, 이미지, 영상과 같은 자료들을 뜻하며, `NGINX`나 `APACHE`가 대표적인 웹 서버다. 즉, 웹 서버에 정적 리소스를 두면 이 리소스를 HTTP 프로토콜로 서빙해주는 것이다.

<br><br>

### 웹 애플리케이션 서버(WAS - Web Application Server)

<img src="https://velog.velcdn.com/images/daydream/post/655a0e61-35e2-49f8-b7e0-86fef60e2cac/image.png" width="600">

<br>

* 웹서버와 동일하게 HTTP 기반으로 동작하고, 웹 서버에서 제공하는 기능 대부분을 제공한다.
    * (정적 리소스 제공 가능)

<br>

* 프로그램 코드를 수행해서 애플리케이션 로직을 수행한다.
    * 동적 HTML, HTTP API (JSON)
    * 서블릿, JSP, 스프링 MVC
        * `톰캣(Tomcat) Jetty`, `Undertow`

<br><br>

### 웹 서버와 WAS의 차이점

<br>

* **웹 서버는 정적 리소스(파일), WAS는 애플리케이션 로직**


* **사실 둘의 용어도 경계도 모호하다.**
    * 웹 서버도 프로그램을 실행하는 기능을 포함하기도 한다.
    * 웹 애플리케이션 서버도 웹 서버의 기능을 제공한다.


* **자바는 서블릿 컨테이너 기능을 제공하면 WAS**
    * 서블릿 없이 자바 코드를 실행하는 서버 프레임워크도 있다.


* **WAS는 애플리케이션 코드를 실행하는데 더 특화**

<br><br>

### 웹 시스템 구성 - WAS, DB

<img src="https://velog.velcdn.com/images/daydream/post/5dc41c39-3269-4551-8db0-5d7578a5f9f8/image.png" width="600">

* `WAS`, `DB`만으로 시스템 구성이 가능하다.
* WAS는 정적 리소스, 애플리케이션 로직을 모두 제공이 가능하다.

<br>

### <span style="color: darkorange">BUT</sapn>

* WAS가 너무 많은 역할을 담당해서 서버 과부하가 우려된다.
* 비용이 높은 애플리케이션 로직이 정적 리소스 때문에 수행이 어려울 수 있다.
* WAS 장애 시 오류 화면도 노출 불가능하다.

<img src="https://velog.velcdn.com/images/daydream/post/34016520-6f4d-4557-a62f-344be520f795/image.png" width="600">

<br>
<br>


### 웹 시스템 구성 - WEB, WAS, DB

<img src="https://velog.velcdn.com/images/daydream/post/6d0574da-4698-4cd5-aa6a-fef192b31466/image.png" width="600">

<br>

* 정적 리소스는 웹 서버에서 처리한다.
* 웹 서버는 애플리케이션 로직 같은 동적인 처리가 필요하면 WAS에 요청을 위임한다.
* WAS는 비용이 높은 애플리케이션 로직 처리를 전담한다.

<br>

* **효율적인 리소스 관리가 가능해진다.**

    * 정적 리소스가 많이 사용되면 Web 서버를 증설하면 된다.
    * 애플리케이션 리소스가 많이 사용되면 WAS를 증설하면 된다.

<img src="https://velog.velcdn.com/images/daydream/post/9d43d5c7-906a-4269-91e7-e4aa3a9c9ec1/image.png" width="600">

<br><br>

* 정적 리소스만 제공하는 웹 서버는 잘 죽지 않는다
* 애플리케이션 로직이 동작하는 WAS 서버는 잘 죽는다.
* WAS, DB에서 장애가 발생하면 WEB 서버에서 오류 화면을 제공할 수 있다.

<img src="https://velog.velcdn.com/images/daydream/post/fca7aceb-1450-49a9-9577-46322742a8b9/image.png" width="600">

<br><br>

## 서블릿

---

### 서블릿이란 ❓

#### 💡 서버에서 웹페이지 등을 동적으로 생성하거나 데이터 처리를 수행하기 위해 자바로 작성된 프로그램

<br>

서블릿에 대해서는 HTML Form 데이터 전송을 예시로 학습해 보자.

<br>

### HTML Form 데이터 전송
#### POST 전송 - 저장

<img src="https://velog.velcdn.com/images/daydream/post/f7168e92-17b5-45db-9656-0b3a7caca55a/image.png" width="600">

<br>

회원 이름과 나이를 Form 형태로 POST 전송하는 상황이다.

해당 HTML Form 태그를 기반으로 HTTP 메시지를 만들면 `Content-Type`은 `application/x-www-form-urlencoded`이고, 메시지 바디에 회원 이름과 나이가 전송된다.

<br><br>

### 서버에서 처리해야 하는 업무

웹 브라우저에서 전송한 HTTP 메시지를 서버에서 받은 다음 처리해서 응답까지 해줘야 하는데,
별도의 프레임워크를 사용하지 않고, 직접  WAS를 구현해야 한다면 어떤 작업들을 해야 할까 ❓

<img src="https://velog.velcdn.com/images/daydream/post/ed0d2217-1a82-4bb6-b7c7-c41181c665a7/image.png" width="750">

<br>

<span style="color: darkgreen">**1. 서버에서는 TCP/IP 연결을 대기하고 있도록 한다.**</span> <br>
<span style="color: darkgreen">**2. 웹 브라우저에서 전송한 메시지를 분석해서 파싱 해준다.**</span> <br>
<span style="color: darkgreen">**3. 분석한 데이터를 토대로 무슨 방식인지 파악한다.**</span> <br>
* **HTTP 메서드 분석을 해 GET, POST, PATCH, PUT, DELETE 어떤 방식인지 파악**
* **URL 분석을 해서 어떤 경로인지 파악**

<span style="color: darkgreen">**4. Content-Type을 분석해 메세지 바디를 파싱 해서 읽는다.**</span> <br>
<span style="color: darkgreen">**5. 위 그림에서는 /save 경로이기에 해당 경로가 매핑된 저장 프로세스가 실행된다.**</span> <br>
<span style="color: darkgreen">**6. 의미 있는 비즈니스 로직(저장 로직)인 데이터베이스에 해당 회원정보 저장을 요청한다.**</span> <br> 
<span style="color: darkgreen">**7. 로직이 끝난 다음 HTTP 응답 메시지 생성을 시작한다.**</span> <br>
* **HTTP 시작 라인 생성**
* **Header 생성**
* **메세지 바디에 HTML을 생성해서 입력**

<span style="color: darkgreen">**8. TCP/IP에 응답을 전달하고 소켓을 종료한다.**</span>

<br><br>

위 단계가 대략적인 웹 브라우저의 요청에서 응답까지 WAS에서 수행돼야 하는 과정인데, 너무 복잡하다.

그래서 `서블릿(Servlet)`이 나온 것이다.

`이 서블릿을 지원하는 WAS에서 위의 여러 번거로운 과정을 모두 자동화해서 제공해 주기 때문에 의미 있는 비즈니스 로직 실행에만 집중할 수 있다. `

<br><br>

### 서블릿을 사용한다면 ❓

<img src="https://velog.velcdn.com/images/daydream/post/cd140fd3-8e53-4544-9fd6-24a1eca08bd6/image.png" width="800">

* WebServlet 어노테이션을 이용해 urlPatterns 속성으로 해당 URL이 호출되면 서블릿 코드가 실행된다.
* HTTP 요청 정보를 편리하게 사용할 수 있는 `HttpServletRequest`
* HTTP 응답 정보를 편리하게 제공할 수 있는 `HttpServletResponse`
* HTTP 요청/응답 정보를 자바 객체화해서 제공하기에 사용이 몹시 편리하다.
* 개발자는 HTTP 스펙을 매우 편리하게 사용할 수 있다.

<br>

#### 서블릿을 사용하는 WAS와 웹 브라우저 간에는 전체적인 흐름이 어떻게 진행될까 ❓

<br><br>

### 웹 브라우저와 WAS간의 흐름

#### Servlet 사용

<img src="https://velog.velcdn.com/images/daydream/post/629ffc13-f319-4e60-8f85-82472d13a8d9/image.png" width="800">

<br>

1. **/hello라는 경로로 요청(Request)을 한다.**
2. **WAS는 Request, Response 객체를 새로 만들어 서블릿 객체를 호출한다.**
3. **개발자는 Request 객체에서 HTTP 요청 정보를 편리하게 꺼내서 사용한다.**
4. **개발자는 Response 객체에 HTTP 응답 정보를 편리하게 작성한다.**
5. **WAS는 Response 객체에 담겨있는 내용으로 HTTP 응답 정보를 생성해서 반환한다.**

<br><br>

<img src="https://velog.velcdn.com/images/daydream/post/dd6a49fc-35f7-468e-9832-bbe7307f41c4/image.png" width="600">

WAS 안에는 서블릿을 관리하는 `서블릿 컨테이너`가 있다.

컨테이너에서 선언한 서블릿 객체를 자동으로 생성, 호출하고 WAS 종료 시 서블릿을 종료하는 등 서블릿의 라이프 사이클을 관리한다.

<br>

#### 특징

* <span style="color: darkgreen">**톰캣처럼 서블릿을 지원하는 WAS를 서블릿 컨테이너라 한다.**</span>

* <span style="color: darkgreen">**서블릿 컨테이너는 서블릿 객체를 생성, 초기화, 호출, 종료하는 생명주기를 관리한다.**</span>

* <span style="color: darkgreen">**서블릿 객체는 싱글톤으로 관리한다.**</span>
    * 고객의 요청이 올 때마다 계속 객체를 생성하는 것은 비효율적이다.
    * 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용한다.
    * 모든 고객 요청은 동일한 서블릿 객체 인스턴스에 접근한다.
    * 공유 변수 사용에 주의해야 한다.
    * 서블릿 컨테이너 종료 시 함께 종료

* <span style="color: darkgreen">**JSP도 서블릿으로 변환되어 사용된다.**</span>

* <span style="color: darkgreen">**💡 동시 요청을 위한 멀티 쓰레드 처리가 지원된다.**</span>

<br><br>

## 동시 요청 - 멀티 쓰레드

---

<img src="https://velog.velcdn.com/images/daydream/post/ec6e5369-749d-428e-832d-a5db754af319/image.png" width="700">

웹 브라우저에서 요청을 보내고 WAS에서 응답을 한다는 것은 위에서 살펴봤으니 알 수 있다.

그런데 WAS에서 TCP/IP 소켓 연결을 한 뒤 서블릿을 호출해서 비즈니스 로직을 수행하게 하는 것은 어디서 해주는 것일까 ❓

### **서블릿 객체를 누가 호출하는 것인가** ❓

<br><br>

### 쓰레드

쓰레드에서 서블릿 객체를 호출해 준다.

* **애플리케이션 코드를 하나하나 순차적으로 실행하는 것은 쓰레드**
* **자바 메인 메서드를 처음 실행하면 main이라는 이름의 쓰레드가 실행된다**.
* **쓰레드가 없다면 자바 애플리케이션 실행이 불가능하다.**
* **쓰레드는 한 번에 하나의 코드 라인만 수행한다.**
* **동시 처리가 필요한 경우 쓰레드를 추가로 생성하면 된다.**

<br><br>

### ❓ 쓰레드를 하나만 사용할 경우 생기는 문제

<img src="https://velog.velcdn.com/images/daydream/post/7de83c29-e5d8-4d5c-9aa1-843378eca476/image.png" width="700">

<br>

쓰레드가 서블릿 객체를 호출해 처리하는 과정에서 `🚩 어떠한 이유`로 처리가 지연되는 상황을 가정해 보자.

`🚩 : DB 접속 타임아웃, DB 처리 지연, 비즈니스 로직의 복잡성`

<br>

이때 다른 웹 브라우저에서 요청을 하게 된다면 WAS에서는 쓰레드를 대기해야 한다. <br>
최악의 경우에는 계속 지연되기 되어 두 요청 다 응답받지 못할 수도 있다.

<br><br>

### 💡 쓰레드를 요청마다 생성

<img src="https://velog.velcdn.com/images/daydream/post/08857242-c6e6-47e8-b280-a12a29398b44/image.png" width="700">

<br>

처리가 지연되고 있지만 신규 쓰레드를 생성해서 제대로 응답받을 수 있다.

<br><br>

#### 장점

* **동시 요청을 처리할 수 있다.**
* **리소스(CPU, 메모리)가 허용할 때까지 처리가 가능하다.**
* **하나의 쓰레드가 지연되어도, 나머지 쓰레드는 정상 동작한다.**

#### 단점

* **쓰레드의 생성 비용은 매우 높다.**
    * 고객이 요청을 할 때마다 쓰레드를 생성하면, 응답 속도가 늦어진다.


* **쓰레드는 컨텍스트 스위칭 비용이 발생한다.**


* **쓰레드 생성에 제한이 없다.**
    * 고객 요청이 너무 많아지면 CPU, 메모리 임계점을 넘어서 **서버가 죽을 수 있다.**

<br>

쓰레드를 요청마다 생성하는 것도 단점이 명확하기에 이런 단점을 보완하고자 나온 것이 `쓰레드 풀`이다.

<br><br>

### 쓰레드 풀

<img src="https://velog.velcdn.com/images/daydream/post/0b5e7d8e-1261-4a0f-803e-1bfb73ddd4af/image.png" width="700">

<br>

WAS 내부에 `쓰레드 풀`이라고 일정 개수의 쓰레드를 미리 생성해두고, 요청이 올 때마다 해당 쓰레드 풀에서 쓰레드를 꺼내서 사용한다. 쓰레드 사용이 끝나면 없애는 것이 아닌 다시 쓰레드 풀에 반납을 한다.

이런 식으로 쓰레드를 매번 생성/소멸하는 것이 아닌 미리 생성한 쓰레드를 쓰레드 풀에서 필요할 때마다 꺼내서 쓴다면 생성/소멸에 필요한 비용을 줄일 수 있고, 매번 요청마다 쓰레드를 생성함으로써 생기는 단점도 해결할 수 있다.

<br>

#### 특징

* **필요한 쓰레드를 쓰레드 풀에 보관하고 관리한다.**
* **쓰레드 풀에 생성 가능한 쓰레드의 최대치를 관리한다.**
    * 톰캣은 최대 200개가 기본 설정이다. (변경 가능)

#### 사용

* **쓰레드가 필요하면, 이미 생성되어 있는 쓰레드를 풀에서 꺼내 사용한다.**
* **사용을 종료하면 쓰레드 풀에 해당 쓰레드를 반납한다.**
* **최대 쓰레드가 모두 사용 중이아서 쓰레드 풀에 쓰레드가 없다면 ❓**
    * 기다리는 요청은 거절하거나 특정 숫자만큼 대기하도록 설정이 가능하다.

#### 장점

* **쓰레드가 미리 사용되어 있기에 쓰레드 생성/소멸 비용이 절약되고 응답 시간이 빠르다.**
* **생성 가능한 쓰레드의 최대치가 있기에 너무 많은 요청이 들어와도 기본 요청은 안전하게 처리가 가능하다.**

<br><br>

### 쓰레드 풀 실무 팁

* **was의 주요 튜닝 포인트는 최대 쓰레드(max thread)**

* `값을 너무 낮게 설정할 경우 동시 요청이 많으면 서버 리소스는 여유롭지만 클라이언트는 금방 응답이 지연된다.`

    * 500개의 쓰레드 풀은 여유롭게 설정할 수 있는 서버에서 당장 사용자가 별로 없다고 maxThread를 5개로 설정해둔다면, 사용자가 5명 안팎일 때는 문제가 없겠지만 방문자가 폭증해서 500명 ~5천 명씩 접속을 한다면 서버 응답 상당히 지연될 수 있다.

    * 하지만, 이렇게 지연되는 상황에서도 CPU 사용량은 5%도 안 되는 상황인 것이고,
      이 점을 몰라서 서버를 증설하면 비용만 커진다.

<br>

* **반대로 이 값을 너무 높게 설정하면 동시 요청이 많아질 경우 CPU, 메모리 리소스 임계점 초과로 서버가 다운될 수 있다.**

* `✅ 장애가 발생한다면`

    * 클라우드면 일단 서버부터 늘리고 이후 튜닝
    * 클라우드가 아니면 열심히 튜닝을 한다.

<br>

#### ❓ 그럼 쓰레드 풀의 적정 숫자는 어떻게 정해야 할까?


애플리케이션 로직의 복잡도, CPU, 메모리, IO 리소스 상황에 따라 모두 다르다.

한 번에 최적화된 쓰레드 풀의 적정 숫자를 찾기는 힘들고 성능 테스트가 필요하다.
대표적인 툴로 `아파치 ab`, `Jmeter`, `nGrinder`가 있다.

<br><br>

### 정리

* <span style="color: darkorange">**멀티쓰레드에 대한 부분은 WAS가 처리한다.**</span>
* <span style="color: darkorange">**개발자가 멀티쓰레드 관련 코드까지 신경 쓸 필요는 없다.**</span>
* <span style="color: darkorange">**개발자는 싱글 쓰레드 프로그래밍을 하듯이 소스 코드를 개발하면 된다.**</span>
* <span style="color: darkorange">**하지만, 멀티 쓰레드 환경이기에 싱글톤 객체(서블릿, 스프링 빈)는 주의해서 사용해야 한다.**</span>

<br><br>

## HTML, HTTP API, CSR, SSR

---

웹 브라우저에서는 서버에 많은 자원을 요청한다. 이 중에는 바로 반환해 줘도 되는 자원이 있고, 서버단에서 처리가 필요한 자원들이 있다. 그럼 무엇이 정적 리소스고, 동적 리소스일까 ❓

<br>

### 정적 리소스

이름 그대로 고정되어 변화가 없는 리소스다.
서버에 있는 이미지 파일, HTML 파일, CSS, JS 파일 등이 정적이고 변화가 없는 정적 리소스이다.
그렇기에 서버에서는 요청에 따라 그냥 해당 리소스들을 응답 메시지에 담아 응답하면 된다.

<img src="https://velog.velcdn.com/images/daydream/post/54adc2dd-3073-4db3-8834-5e474b5ca8b5/image.png" width="700">

<br><br>

### HTML

HTML은 정적이라고 했다.

그러나 HTML의 경우 필요하다면 동적으로 HTML을 생성해서 반환해 줘야 한다. <br>
이때 사용되는 게 템플릿 엔진(`JSP`, `타임리프`)이다. WAS에서 동적으로 HTML을 생성해서 응답하면 웹 브라우저에서는 해당 HTML을 해석해서 렌더링 한 후 사용자에게 보여준다. 

<br>

<img src="https://velog.velcdn.com/images/daydream/post/2f729680-99fc-4bc7-bb4e-7a7b888df984/image.png" width="700">

<br><br>

### HTTP API

HTML이 아니라 데이터를 전달하는 것으로 데이터를 반환한다고 생각하면 된다. <br>
최근에는 주로 `JSON 형식`이 사용된다. <br>

데이터를 반환할 뿐이기에 다양한 시스템에서 호출이 가능하다. <br>

<img src="https://velog.velcdn.com/images/daydream/post/7f2b7744-9102-4d99-a668-53fa45371d7d/image.png" width="700">

<br><br>

다양한 시스템에서 사용된다는 말은 웹, 앱 클라이언트나 서버 간에 통신에서 사용된다는 의미로 데이터만 주고받기에 만약 UI 화면이 필요하면, 클라이언트가 별도로 처리해야 한다.

<img src="https://velog.velcdn.com/images/daydream/post/9c3dc47f-908f-408d-ae57-4cbf814a4431/image.png" width="700">

<br>

#### 정리

* JSON 형태로 데이터 통신한다.


* UI 클라이언트와의 접점
    * 앱 클라이언트 (아이폰, 안드로이드, PC 앱)
    * 웹 브라우저에서 자바스크립트를 통한 HTTP API 요청
    * React, Vue.js 같은 웹 클라이언트


* 서버 to 서버
    * 주문 서버 👉 결제 서버
    * 기업 간 데이터 통신

<br><br>

### SSR - 서버 사이드 렌더링

서버에서 최종 HTML을 생성해서 클라이언트에 전달하면 웹 브라우저에서는 해당 웹 브라우저를 그대로 렌더링 해서 사용자에게 보여준다.

<img src="https://velog.velcdn.com/images/daydream/post/e6b0a996-5612-4c95-b69f-956cfa40b0a9/image.png" width="700">

* **HTML 최종 결과를 서버에서 구성한 뒤 웹 브라우저에 전달**
* **주로 정적인 화면에 사용**
* **관련 기술 : JSP, 타임리프 👉 백엔드 개발자**

<br><br>

### CSR - 클라이언트 사이드 렌더링

<img src="https://velog.velcdn.com/images/daydream/post/4047fa3b-9ee4-4bce-97a6-1a47eb231658/image.png" width="700">

* **HTML 결과를 자바스립트를 사용해서 웹 브라우저에서 동적으로 생성해서 적용한다.**
* **주로 동적인 화면에 사용하고, 웹 환경을 마치 앱처럼 필요한 부분을 변경 가능하다.**
* **예) 구글 지도, Gmail, 구글 캘린더**
* **관련 기술: React, Vue.js 👉 웹 프론트엔드 개발자**

<br><br>

## 자바 백엔드 웹 기술 역사

---

### 과거 기술

* <span style="color: darkorange">**서블릿 - 1997**</span>
    * HTML 생성이 어려움


* <span style="color: darkorange">**JSP - 1999**</span>
    * HTML 생성은 편리하지만, 비즈니스 로직까지 너무 많은 역할을 담당하게 된다.


* <span style="color: darkorange">**서블릿, JSP 조합 MVC 패턴 사용**</span>
    * 모델, 뷰, 컨트롤러로 역할을 나누어 개발
    * 비즈니스 로직 부분과 VIEW 부분을 분리했다는 점에 의의


* <span style="color: darkorange">**MVC 프레임워크 춘추 전국시대 - 2000년 초 ~ 2010년 초**</span>
    * MVC 패턴 자동화, 복잡한 웹 기술을 편리하게 사용할 수 있는 다양한 기능 지원
    * 스트럿츠, 웹워크, 스프링 MVC (구버전)


<br><br>

### 현재 사용 기술

* <span style="color: darkorange">**어노테이션 기반의 스프링 MVC 등장**</span>
    * @Controller
    * MVC 프레임워크의 춘추 전국 시대 마무리


* <span style="color: darkorange">**스프링 부트의 등장**</span>
    * 스프링 부트는 서버를 내장
    * 과거에는 서버에 WAS를 직접 설치하고, 소스는 War 파일을 만들어 설치한 WAS에 배포
    * 스프링 부트는 빌드 결과(Jar)에 WAS 서버 포함 👉 빌드와 배포가 단순화됨

<br><br>

### 자바 뷰 템플릿 역사

* <span style="color: darkorange">**JSP**</span>
    * 속도가 느리고 기능이 부족하다


* <span style="color: darkorange">**프리마커(Freemarker), Velocity**</span>
    * 속도 문제 해결, 다양한 기능


* <span style="color: darkorange">**타임리프(Thymeleaf)**</span>
    * 내추럴 템플릿 : HTML의 모양을 유지하면서 뷰 템플릿 적용이 가능하다
    * 스프링 MVC와 강력한 기능 통합
    * 최선의 선택, 단 성능만 보면 프리마커타 벨로시티가 더 빠르다.

