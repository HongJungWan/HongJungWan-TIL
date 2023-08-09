## URI(Uniform Resource Identifier)



### UR<span style="color: red">I</span>? UR<span style="color: red">L</span>? UR<span style="color: red">N</span>?

* **URI는 로케이터(locator), 이름(name) 또는 둘 다 추가로 분류될 수 있다.**

<br/>

<img src="https://velog.velcdn.com/images/daydream/post/eafdd4f5-d2d8-44cf-9d8c-443fa3271b1a/image.png" width="450">

<br/>

* **U**niform : 리소스 식별하는 통일된 방식
* **R**esource : 자원, URI로 식별할 수 있는 모든 것 (제한 없음)
* **I**dentifier : 다른 항목과 구분하는데 필요한 정보

<br/><br/>

<img src="https://velog.velcdn.com/images/daydream/post/ec1009ec-2105-434b-92bc-be742dfa1d4d/image.png" width="550">

* `URL(Uniform Resource Locator)`은 우리가 흔히 웹브라우저에서 사용하는 주소

* `URN(Uniform Resource Name)`은 위 그림과 같이 이름을 부여하는 것인데,
  이름만 가지고는 주소를 찾아갈 수 없기에 실제로 사용하기는 힘들다.

<br/><br/>

### URL, URN

* `URL - Locator` : 리소스가 있는 위치를 지정한다.
* `URN - Name` : 리소스에 이름을 부여한다.
* 위치는 변할 수 있지만, 이름은 변하지 않는다.
* URN 만으로 리소스를 찾을 수 있는 방법이 보편화되지 않았다.

<br/><br/>

## URL(Uniform Resource Locator) 분석



#### 💡 scheme://[userinfo@]host[:port][/port][/path][?query][#fragment]

문법 예시 : `https://google.com/search?q=hello&hl=ko`
프로토콜 : `https`
호스트명 : `google.com`
포트 번호 : `443`
패스 : `/search`
쿼리 파라미터 : `q=hello&hl=ko`

<br/><br/>

### scheme

* 주로 프로토콜이 사용된다.
* 프로토콜이란 어떤 방식으로 자원에 접근할 것인지 정해놓은 규칙이다.
    * ex: http, https, ftp ...
* http는 80 포트, https는 443포트를 주로 사용하며 생략 가능하다.
* https는 http에 보안 사용을 추가한 것 (HTTP Secure)

<br/>

### userinfo

* URL에 사용자 정보를 포함해서 인증할 때 사용한다.
* 하지만 거의 사용하지 않는다.

<br/>

### host

* 호스트명
* 도메인명 또는 IP 주소를 직접 입력한다.

<br/>

### PORT

* 접속 포트
* 일반적으로 생략 가능하며, 생략 시 http는 80, https는 443

<br/>

### path

* 리소스 경로, 계층적 구조로 되어있다.
    * /home/file1.jpg
    * /members
    * /members/100, /item/iphone12

<br/>

### query

* `key=value` 형태
* ?로 시작하며 &로 추가가 가능하다
    * ?keyA=valueA&keyB=valueB
* `query parameter`, `query string`등으로 불린다. 웹서버에 제공하는 파라미터로 문자 형태.

<br/>

### fragment

* html 내부 북마크 등에 사용
* 서버에 전송하는 정보가 아니다.

<br/><br/>

## 웹 브라우저 요청 흐름



다음 URL을 가지고 `https://google.com/search?q=hello&hl=ko` 웹 브라우저 요청 흐름을 파악해 보자.

<br/>

<img src="https://velog.velcdn.com/images/daydream/post/60d651b6-18e8-4540-8d6f-08f37eb02bd2/image.png" width="600">

<br/>

<img src="https://velog.velcdn.com/images/daydream/post/38c2f8bc-5412-4b3e-98fc-0ec242830589/image.png" width="600">


	1. DNS 조회
	: google.com, DNS를 조회해서 IP 주소를 찾는다.

	2. HTTPS PORT 생략(`443`)

	3. HTTP 요청 메시지 생성

<img src="https://velog.velcdn.com/images/daydream/post/9eb4012d-7f1d-432d-93d8-bed17918fa13/image.png" width="300">

<br/><br/>

<img src="https://velog.velcdn.com/images/daydream/post/f604be9b-74b0-4db0-9dbe-0054005b46ec/image.png" width="600">

	1. 웹 브라우저가 HTTP 메시지를 생성

	2. SOCKET 라이브러리를 통해 TCP/IP 계층에 전달한다.
	이전 단계에서 찾은 IP와 PORT 정보를 가지고 `SYN`, `SYN+ACK`, `ACK` 과정을 통해 서버와 연결을 한다. 
	연결이 성공되면 TCP/IP 계층으로 데이터를 전달한다.

	3. TCP/IP 패킷을 생성한다. HTTP 메시지도 포함된다. 

<img src="https://velog.velcdn.com/images/daydream/post/91ae1071-6f22-4d2d-b7b8-5d1418932ddf/image.png" width="500">

	4. 서버는 패킷이 도착하면 패킷의 내부 HTTP 메서드를 해석해서 정보에 맞는 동작을 한다.
	5. 서버에서 HTTP 응답 메시지를 생성한다.

<img src="https://velog.velcdn.com/images/daydream/post/54911c12-7b6c-47b0-b4cf-05eab33faae1/image.png" width="300">

	6. 클라이언트에서는 응답 메시지를 받아 맞는 동작(ex: 렌더링)을 한다. 
