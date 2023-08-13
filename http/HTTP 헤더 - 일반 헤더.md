## HTTP 헤더 - 일반 헤더

<br>

## HTTP 헤더 개요

<br/>

#### 💡 header-field = `field-name`":"OWS `field-value` OWS (OWS: 띄어쓰기 허용)

<img src="https://velog.velcdn.com/images/daydream/post/26ccdd41-8e2d-4085-8607-d0be1db4b236/image.png" width="650">

<br/><br/>

### HTTP 응답 일반적인 - 헤더 예시

```text
Content-Length: 1234
```

<br/>

* `header-field`: HTTP 헤더의 필드를 의미.

<br/>

* `field-name`: HTTP 헤더 필드의 이름.

<br/>

* `":"`: 콜론 (:) 문자로, 필드 이름과 필드 값을 구분.

<br/>

* `OWS`: Optional White Space (띄어쓰기 허용). HTTP/1.1 헤더에 있어서 헤더 필드의 이름과 값 사이, 또는 값 뒤에 공백 문자(예: 스페이스, 탭)가 올 수 있다. 이것은 선택적이므로 존재하지 않을 수도 있다.

<br/>

* `field-value`: HTTP 헤더 필드의 값.

<br/>

### 용도

* HTTP 전송에 필요한 모든 부가정보를 넣는다.
    * 메시지 바디의 내용, 바디의 크기, 압축, 인증, 요청 클라이언트, 서버 정보, 캐시 관리 정보 등


* 필요하다면 임의의 헤더 추가도 가능하다.
    * helloworld: hihi

<br/><br/>

### 분류

과거에는 어떻게 분류를 했을까 ❓

<img src="https://velog.velcdn.com/images/daydream/post/b8e8ee2d-4a90-4a14-afde-f9a70b1ca3c1/image.png" width="650">

* General 헤더 : 요청 메시지 / 응답 메시지 구분 없이 메시지 전체에 적용되는 정보


* Request 헤더 : 요청을 보낼 때 들어가는 헤더
    * User-Agent : Mozilla/5.0(Macintosh;..)


* Response 헤더 : 응답할 때 들어가는 헤더
    * Server : Apache


* <span style="color: darkorange">**Entity 헤더 : 엔티티 바디 정보로 컨텐츠 타입이나 길이 같은 메시지 바디에 들어가는 내용에 관련된 헤더가 들어가는 헤더**</span>

<br/><br/>

### HTTP BODY

**messagaae body - RFC2616(과거)**

<img src="https://velog.velcdn.com/images/daydream/post/11ee266f-bdae-43cd-a4a9-b21b4dcbd6b0/image.png" width="450">

* **메시지 본문(message body)은 엔티티 본문(entity body)를 전달하는 데 사용한다.**
    * **엔티티 본문은 요청이나 응답에서 전달할 실제 데이터를 의미한다.**


* **엔티티 헤더는 엔티티 본문의 데이터를 해석할 수 있는 정보를 제공한다.**
    * 데이터 유형(html, json), 데이터 길이, 압축 정보 등

<br/>

### BUT

이런 엔티티 헤더 스펙은 1999년 RFC2616 스펙에서 나온 스펙이다.

이 스펙은 2014년 RFC7230~7235가 등장하면서 폐기된다.

<br/>

`엔티티(Entity) → 표현(Representation)`

`Representation` = `representation Metadata` + `Representation Data`

<br/>

즉, 표현 메타데이터와 표현 데이터를 합쳐 표현이라 부른다.

그럼 이제 최신 스펙에서는 메시지 바디가 어떻게 돼있는지 살펴보자.

<br/>

### Message Body - RFC7230(최신)

<img src="https://velog.velcdn.com/images/daydream/post/39b76e85-3ce8-44cb-8685-eecabab7e625/image.png" width="450">

* 메시지 본문을 통해 표현 데이터 전달
* 메시지 본문 = Payload
* 표현은 요청이나 응답에서 전달할 실제 데이터


* 표현 헤더는 표현 데이터를 해석할 수 있는 정보를 제공한다.
    * 데이터 유형(html, json), 데이터 길이, 압축 정보 등


* 참고 : 표현 헤더는 표현 메타데이터와, 페이로드 메시지를 구분해야 하지만, 너무 복잡해지기에 생략

<br/>

### 정리

어째서 엔티티를 표현이라고 바꿔서 말하는 것일까 ❓
예를 들어 회원 조회 내역을 응답할 때 이를 HTML로 표현할 수도 있고, JSON으로 표현해 전달할 수도 있다. 그래서 이렇게 실제 전달하는 것을 표현이라고 용어를 정의했다.

표현(Representation)에 대해 더 알아보자.

<br/><br/>

## 표현



<img src="https://velog.velcdn.com/images/daydream/post/2f627d1c-87e8-48c8-b196-28afe5b2b61d/image.png" width="350">

클라이언트와 서버간에 송/수신할 때, 이 리소스를 무엇으로 표현할지 알려주고, 표현한다.

ex) 회원이라는 리소스가 있을 때 이를 HTML 혹은 JSON으로 전달할 거야


* `Content-Type` : 표현 데이터의 형식
* `Content-Encoding` : 표현 데이터의 압축 방식
* `Content-Language` : 표현 데이터의 자연 언어
* `Content-Length` : 표현 데이터의 길이


* 표현 헤더라기보다는 Payload 헤더라고 부르는 게 명확하지만 심플하게 표현 헤더로 생각해도 된다.

* 표현 헤더는 전송, 응답 둘 다 사용한다.

<br/><br/>

### Content-Type

* 표현 데이터의 형식 설명

<img src="https://velog.velcdn.com/images/daydream/post/25dc44cc-a185-4d80-bb78-be0160b3629d/image.png" width="350">

* 미디어 타입, 문자 인코딩
    * text/html; charset=utf-8
    * application/json (기본이 utf-8)
    * image/png

<br/><br/>

### Content-Encoding

* 표현 데이터 인코딩

<img src="https://velog.velcdn.com/images/daydream/post/f7c09a69-3663-45bb-8a5e-28c8247cec09/image.png" width="350">

* 표현 데이터를 압축하기 위해 사용한다.
* 데이터를 전달하는 곳에서 압축 후 인코딩 헤더 추가
* 데이터를 읽는 쪽에서 인코딩 헤더 정보로 압축 해제
    * gzip
    * deflate
    * identity (압축을 하지 않는다는 의미)

<br/><br/>

### Content-Language

* 표현 데이터의 자연 언어

<img src="https://velog.velcdn.com/images/daydream/post/f588e769-ae01-4ca5-be80-f6fac5f717c8/image.png" width="350">

* 표현 데이터의 자연 언어를 표현
    * ko
    * en
    * en-US


* 해당 헤더를 통해 국가별로 맞는 언어로(지원한다는 가정하에) 응답을 받을 수 있다.

<br/><br/>

### Content-Length

* 표현 데이터의 길이

<img src="https://velog.velcdn.com/images/daydream/post/ad176b6c-f84b-4ed0-aca5-af39ae41645a/image.png" width="350">

* 바이트 단위

* Transfer-Encoding(전송 코딩)을 사용하면 Content-Length를 사용하면 안 된다.

<br/><br/>

## 협상(콘텐츠 네고시에이션)



클라이언트가 선호하는 표현 요청

* **Accept : 클라이언트가 선호하는 미디어 타입 전달**
* **Accept_Charset : 클라이언트가 선호하는 문자 인코딩**
* **Accept-Encoding : 클라이언트가 선호하는 압축 인코딩**
* **Accept-Language : 클라이언트가 선호하는 자연 언어**


* 협상 헤더는 요청 시에만 사용

<br/>

### 콘텐츠 협상 예시

#### Accept-Language 적용 전

<img src="https://velog.velcdn.com/images/daydream/post/1bf22863-f434-4b21-bb63-b8bf8fb28db2/image.png" width="750">

* 한국어 브라우저에서 특정 웹사이트에 접속했을 때 콘텐츠 협상(Accept-Language)이 안 돼있을 경우

* 서버에서는 딱히 우선순위 같은 게 없기에 기본 언어로 설정된 영어로 응답한다.

<br/><br/>

#### Accept-Language 적용 후

<img src="https://velog.velcdn.com/images/daydream/post/ebc3d45b-6f91-4440-9129-7b3c5cb18a03/image.png" width="750">

* 클라이언트에서 Accept-Language로 KO를 작성해 요청하면 서버에서는 해당 우선순위 언어를 지원할 수 있기 때문에 해당 언어인 한국어로 된 응답을 작성해 반환해 준다.

<br/><br/>

#### Accept-Language 복잡한 예시

위처럼 지원하는 언어를 요청하는 단순한 경우라면 문제가 없다.
하지만, 서버에서 지원하는 언어가 여러 개인데 내가 최우선으로 선호하는 언어는 적용되지 않는다면 어떻게 해야 하는가 ❓

<img src="https://velog.velcdn.com/images/daydream/post/ed74d26e-927d-4d37-acc7-e9d137d35de9/image.png" width="750">

* 클라이언트에서는 한국어를 선호하기에 Accept-Language에 한국어를 요청했다.
* 하지만 서버에서는 한국어를 지원하지 않는 상황이고 기본 언어는 독일어로 되어있다.
* 클라이언트에서는 독일어는 너무 어렵기 때문에 한국어가 안되면 영어라도 나오길 바란다면 ❓
    * 우선순위를 사용해야 한다.

<br/><br/>

### 협상과 우선순위 ①

#### Quality Values(q)

<img src="https://velog.velcdn.com/images/daydream/post/da855af7-1ebc-4528-870e-476ac83f0514/image.png" width="450">

* Quality Values(q) 값 사용
* 0~1, 1에 가까울수록(클수록) 높은 우선순위를 가진다.
* 생략하면 1
* Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
    1. ko-KR;q=1(q생략)
    2. ko;q=0.9
    3. en-US;q=0.8
    4. en:q=0.7

<br/>

#### 우선순위 적용 후

<img src="https://velog.velcdn.com/images/daydream/post/bde480c7-33a1-426a-860e-565b351ab4a1/image.png" width="750">

* 1 순위인 한국어를 서버에서는 지원하지 않는다.
* 2 순위인 영어를 서버에서는 지원한다.
* 서버에서는 우선순위에 있는 영어를 독일어보다 선호하기에 영어로 응답한다.

<br/><br/>

### 협상과 우선순위 ②

#### Quality Values(q)

<img src="https://velog.velcdn.com/images/daydream/post/3475dded-a589-4661-9d41-0c55edd3afce/image.png" width="450">

* 구체적인 것을 우선한다.

    1. text/plain;format=flowed
    2. text/plain
    3. text/*
    4. */*

<br/><br/>

### 협상과 우선순위 ③

#### Quality Values(q)

* 구체적인 것을 기준으로 미디어 타입을 맞춘다.

<img src="https://velog.velcdn.com/images/daydream/post/f236fdfe-3072-4416-b20e-40c24943efc3/image.png" width="450">

<img src="https://velog.velcdn.com/images/daydream/post/3af10fdc-6881-4c2e-972b-44fff59445b2/image.png" width="250">

* text/plain은 매칭되는 것은 없지만 text/*과는 매칭되기에 0.3으로 생각하면 된다.

<br/><br/>

## 전송 방식



전송 방식은 단순하게 4개로 분리할 수 있다.

* **단순 전송**
* **압축 전송**
* **분할 전송**
* **범위 전송**

<br/>

### 단순 전송

<img src="https://velog.velcdn.com/images/daydream/post/e988b325-8489-4c90-9f1b-9750f6d70cf7/image.png" width="650">

* 요청을 하면 응답을 할 때 메시지 바디에 대한 `Content-Length`를 지정하는 것.
* 메시지 바디의 길이를 다 알고 있을 때 사용.
* 한 번에 요청하고 응답한다.

<br/>

### 압축 전송

<img src="https://velog.velcdn.com/images/daydream/post/d5fb5125-a293-4a02-8a2a-dbde5ddbdc7c/image.png" width="650">

* 서버에서 메시지 바디를 `gzip`같은 방식을 이용해 압축해서 전달하는 방식
* Content-Encoding이라는 항목을 헤더에 넣어서 어떻게 압축했는지 알려줘야 한다.

<br/>

### 분할 전송

<img src="https://velog.velcdn.com/images/daydream/post/f7e0ac96-fb49-4872-9a75-94dd6699302c/image.png" width="700">

* `Transfer-Encoding` : `chunked`라고 덩어리로 쪼개서 보낸다는 의미
* 서버에서 클라이언트로 응답 메시지를 특정 단위로 나눠서 보낸다.
* 용량이 매우 큰 응답을 할 때 분할 전송으로 전송되는 대로 표현을 하는 방식이다.
* 이때는 Content-Length를 넣으면 안 된다.(길이를 예측할 수 없기 때문)

<br/>

### 범위 전송

* Range, Content-Range

<img src="https://velog.velcdn.com/images/daydream/post/ca5d0e61-5209-4a1e-b0d8-6f52443b19c8/image.png" width="700">

* 이미지와 같이 용량이 큰 데이터를 받을 때 중간에 전송이 끊겼을 경우 범위를 지정해서 요청하면 매번 끊길 때마다 새로 받을 필요 없이 특정 범위부터 응답해서 속도를 높일 수 있다.

<br/><br/>

## 일반 정보



### From

* 유저 에이전트의 이메일 정보
* 일반적으로 잘 사용되지는 않는다.
* 검색 엔진 같은 곳에서, 주로 사용
* 요청(Request)에서 사용한다.

<br/>

### Referer

이전 웹 페이지 주소

* 현재 요청된 페이지의 이전 웹 페이지 주소
* A 페이지에서 B 페이지로 이동하면 B를 요청할 때 `Referer: A`를 포함해서 요청한다.
* Referer를 사용해서 유입 경로 분석이 가능하다.
* 요청(Request)에서 사용한다.
* ✅ referer는 단어 referrer의 오타지만 이미 너무 많은 곳에서 referer로 사용하고 있기에 그냥 사용하고 있다.

<br/>

### User-Agent

* 유저 에이전트 애플리케이션 정보

<img src="https://velog.velcdn.com/images/daydream/post/5e1131ba-0064-4612-9b0b-3f8a271c8df0/image.png" width="700">

* 클라이언트의 애플리케이션 정보 (웹 브라우저 정보 등)
* 통계 정보
* 어떤 종류의 브라우저에서 장애가 발생하는지 파악 가능하다.
* 요청(Request)에서 사용한다.

<br/>

### Server

* 요청을 처리하는 ORIGIN 서버의 소프트웨어 정보

#### 💡 <span style="color: darkorange"> HTTP 요청을 보내면 여러 프록시 서버를 거치게 되는데, 실제로 내 요청을 받고 응답을 해주는 엔드 포인트 서버를 ORIGIN 서버라 한다. </span>

* Server: Apache/2.2.22(Debian)
* server: nginx
* 응답(Response)에서 사용한다.

<br/>

### Date

메시지가 발생한 날짜와 시간

* Date: Tue, 15 Nov 1994 08:12:31 GMT
* 응답(Response)에서 사용한다.

<br/><br/>

## 특별한 정보



### Host

* 요청한 호스트 정보 (도메인)

<img src="https://velog.velcdn.com/images/daydream/post/31504d10-94ce-403b-8855-1ff99816f178/image.png" width="400">

* 요청(Request)에서 사용한다.
* 필수 헤더
* 하나의 서버가 여러 도메인을 처리할 때 사용
* 하나의 IP 주소에 여러 도메인이 적용되어 있을 때

<br/><br/>

<img src="https://velog.velcdn.com/images/daydream/post/29dad036-daef-439a-9639-24e0e97b2c0c/image.png" width="400" >

가상 호스트를 통해 여러 도메인을 한 번에 처리 가능한 서버가 있어서 실제 애플리케이션이 여러 개 구동이 가능하다. 그래서 위 이미지처럼 200.200.200.2라는 아이피를 가진 서버에서 `aaa.com`, `bbb.com`, `ccc.com`이라는 여러 도메인으로 운영을 하고 있다.  근데 여기서 HOST가 없이 요청을 하게 되면 어떻게 될까?

<br/><br/>

### HOST가 없을 경우 생기는 문제

<img src="https://velog.velcdn.com/images/daydream/post/737af527-f875-4dbd-9741-feb4f11ec171/image.png" width="700" >

* 클라이언트에서 /hello라는 경로로 요청을 하는 상황이다.
* 해당 요청은 서버에서 어떤 도메인으로 들어가야 하는지 어떻게 알 수 있을까?
* 해당 요청을 서버에서 어느 도메인으로 들어가야 할지 알 수가 없기에 문제가 된다.

<br/><br/>

### Location

페이지 리다이렉션

* 웹 브라우저는 3xx 응답 결과에 Location 헤더가 있으면, Location 위치로 자동 이동한다
* 응답 코드 3xx에서 설명했다.
* `201(Created)` : Location 값은 요청에 의해 생성된 리소스 URI이다
* `3xx(Redirection)` : Location 값은 요청을 자동으로 리디렉션 하기 위한 대상 리소스를 가리킨다

<br/><br/>

### Allow

허용 가능한 HTTP 메서드

경로는 존재하지만 지원하는 HTTP 메서드가 한정적이기에 해당 헤더를 넣어주면 클라이언트에서는 서버에서 해당 경로가 지원하는 HTTP 메서드가 무엇인지 알 수 있다.
(**실제로 사용되는 빈도수는 높지 않다.**)

* 405(Method Not Allowd)에서 응답에 포함해야 한다.
* Allow : GET, HEAD, PUT

<br/><br/>

### Retry-After

유저 에이전트가 다음 요청을 하기까지 기다려야 하는 시간
사용하기가 쉽지가 않다. (예측하기 힘듦)

* 503(Service Unavailable): 서비스가 언제까지 불가능한지 알려줄 수 있다.
* Retry-After : Fri, 31 Dec 1999 23:59:59 GMT (날짜 표기)
* Retry-After : 120 (초 단위 표기)

<br/><br/>

## 인증



### Authorization

클라이언트 인증 정보를 서버에 전달

* **Authorization : BASIC xxxxxxxxxxxxxxxxxx**
    * 인증 방식은 OAuth, OAuth2, SNS 로그인 등 다양한데, 방식 별로 들어가야 하는 값이 다르다.
    * 인증 메커니즘과는 상관없이 헤더를 제공하는 것으로 인증과 관련된 값을 넣어주면 된다.

<br/>

### WWW-Authentication

리소스 접근 시 필요한 인증 방법 정의

* 401 Unauthorized 응답과 함께 사용한다.

```WW-Authentication: Newauth realm="apps", type=1,title="Login to \"apps\"", Basic * realm="simple"```

* 위와 같은 방식으로 어떻게 인증을 해야 할지를 정의해 알려준다.

<br/><br/>

## 쿠키



### 쿠키란?

: HTTP 쿠키(웹 쿠키, 브라우저 쿠키)는 서버가 사용자의 웹 브라우저에 전송하는 작은 데이터 조각으로 브라우저는 이 데이터 조각들을 저장해 놓았다가 동일한 서버에 재 요청 시 저장된 데이터를 함께 전송한다.

* **Set-Cookie : 서버에서 클라이언트로 쿠키 전달(응답)**
* **Cookie : 클라이언트가 서버에서 받은 쿠키를 저장하고, HTTP 요청 시 서버로 전달한다.**

<br/>

### 주사용 목적


* 세션 관리(Session Management)
    * 서버에 저장해야 할 로그인, 장바구니, 게임 스코어등의 정보 관리


* 개인화(Personalization)
    * 사용자 선호, 테마 등의 세팅


* 트래킹(Tracking)
    * 사용자 행동을 기록하고 분석하는 용도

<br/><br/>

### 쿠키를 사용하지 않았을 때의 불편함

#### 로그인

<img src="https://velog.velcdn.com/images/daydream/post/03709a60-5182-40be-82e3-9e2c52e20dd7/image.png" width="650">

<br/>

#### 로그인 이후 /welcome

<img src="https://velog.velcdn.com/images/daydream/post/5c8f0288-28c1-4f7c-a188-107c8b945309/image.png" width="650">

* 서버 입장에서는 이게 로그인한 유저가 보낸 요청인지 알 수 없다.
* 그렇기에 게스트(비회원)로 판단해서 응답을 할 수밖에 없다.
* HTTP는 무상태 프로토콜(Stateless Protocol)이라는 점을 기억해야 한다.

<br/>

### Stateless

* 클라이언트와 서버가 요청과 응답을 주고받으면 연결이 끊어진다.
* 클라이언트가 다시 요청하면 서버는 이전 요청을 기억하지 못한다.
* 클라이언트와 서버는 서로 상태를 유지하지 않는다.

<br/><br/>

### 대안은 ❓

#### 1. 모든 요청에 사용자 정보를 포함

<img src="https://velog.velcdn.com/images/daydream/post/f75e703b-4d68-4f27-8f59-178fc8145a17/image.png" width="650">

* 매번 요청할 때마다 로그인한 유저의 정보를 담아서 보내주면 서버 측에서는 해당 정보를 분석해 로그인 상태를 확인한다.

* 모든 요청과 링크에 사용자 정보를 포함해야 하기에 비용 소모가 크다.

<br/>

<img src="https://velog.velcdn.com/images/daydream/post/ed38aefc-5e2b-4d4b-aaa7-9ccbe06348e6/image.png" width="450">

``` 모든 요청에 로그인 정보를 포함한다. ```

* 브라우저를 완전히 종료 후 다시 열면 정보를 포함할 수 없다.

<br/><br/>

#### 2. 쿠키를 사용한다.

<img src="https://velog.velcdn.com/images/daydream/post/d17face9-6db4-4d9f-915f-befae281e0d8/image.png" width="550">

* 클라이언트에서 로그인을 요청하며 데이터를 보내면 서버에서는 Set-Cookie로 로그인 정보를 담아 응답한다.

* 웹브라우저는 내장된 쿠키 저장소에 Set-Cookie에 있는 정보를 저장한다.

* 그래서 로그인 이후 welcome 페이지에 접근하면 쿠키를 조회해서 쿠키 값을 Cookie에 담아서 보낸다.

<br/>

<img src="https://velog.velcdn.com/images/daydream/post/bcf56f24-0874-433d-af5a-13470fbc190d/image.png" width="550">

* 하지만 이렇게 매번 쿠키를 담아 보내는 것도 문제가 있다.

<br/><br/>

### 쿠키의 사용처 및 사용 시 주의점

* **사용처**
    * 사용자 로그인 세션 관리
    * 광고 정보 트래킹


* **쿠키 정보는 항상 서버에 전송된다.**
    * 네트워크 트래픽을 추가적으로 유발한다.
    * 최소한의 정보만 사용해야 한다. (세션, id, 인증 토큰)
    * 서버에 전송하지 않고 웹 브라우저 내부에 데이터를 저장하고 싶은 경우 웹 스토리지를 사용


* **주의점**
    * 보안에 민감한 데이터는 저장하면 안 된다.
        * 주민번호, 신용카드 번호 등

<br/><br/>

### 쿠키의 생명주기(LifeCycle)

Expires, max-age

* Set-Cookie : expires=Sat, 26-Dec-2020 04:39:21 GMT
    * 만료일이 되면 쿠키는 삭제된다.


* Set-Cookie : max-age=3600(3600초)
    * 0이나 음수로 지정할 경우 쿠키는 삭제된다.


* 세션 쿠키 : 만료 날짜를 생략하면 브라우저 종료 시까지만 유지된다.
* 영속 쿠키 : 만료 날짜를 입력하면 해당 날짜까지 유지된다.

<br/>

### 쿠키 도메인 지정

#### 💡 domain=example.org

* **명시** : 명시한 문서 기준 도메인 + 서브 도메인 포함
    * domain=example.org로 지정해서 쿠키를 생성하면
        * `example.org` → 쿠키 접근 가능
        * `dev.example.org` → 쿠키 접근 가능

<br/>

* **생략** : 현재 문서 기준 도메인만 적용
    * `example.org`에서 쿠키를 생성하고 domain 지정을 생략하면
        * `example.org`에서만 쿠키 접근이 가능하다.
        * `dev.example.org`는 쿠키 접근이 불가하다.

<br/>

### 쿠키 경로 지정

#### 💡 path=/home

* 작성한 경로를 포함한 하위 경로 페이지에서만 쿠키 접근이 가능하다.
* 일반적으로 path=/ 루트로 지정한다
    * 해당 도메인의 하위 경로 모두 쿠키를 사용하기를 바라기 때문
    * 예)
        * path=/home 지정
        * /home/level1 접근 가능
        * /home/level1/level2 접근 가능
        * /hello 불가능

<br/>

### 쿠키 보안 `Secure`, `HttpOnly`, `SameSite`

* `Secure`
    * 쿠키는 http, https를 구분하지 않고 전송한다.
    * Secure를 적용하면 https인 경우에만 전송한다.


* `HttpOnly`
    * XSS 공격 방지
    * 자바스크립트에서 접근 불가 (document.cookie)
    * HTTP 전송에만 사용한다.


* `SameSite`
    * XSRF 공격 방지
    * 요청 도메인과 쿠키에 설정된 도메인이 같은 경우에만 쿠키를 전송한다.
