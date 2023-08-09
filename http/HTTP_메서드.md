## HTTP 메서드

<br>

### HTTP API를 만들어보자.

---

회원 정보 관리 API를 만들어보자.

### 요구사항

* 회원 목록 조회
* 회원 조회
* 회원 등록
* 회원 수정
* 회원 삭제

<br>

#### 💡 위와 같은 요구사항을 API로 만들려고 하면 어떻게 만들어야 할까 ❓

<br>

### API URI 설계

* 회원 목록 조회 `/read-member-list`
* 회원 조회 `/read-member-by-id`
* 회원 등록 `/create-member`
* 회원 수정 `/update-member`
* 회원 삭제 `/delete-member`

<br>

단순하게 생각하면 위와 같이 설계를 할 수 있다. 하지만, 이런 API URI 설계는 가독성은 좋을지 모르지만
좋은 URI 설계는 아니다.


**설계에서 가장 중요한 것**은 <span style="color: #ff4040">`리소스 식별`</span>이다.

<br>

### 리소스의 의미는 뭘까 ❓

* 회원을 등록하고 수정하고 조회하는 **행위를 리소스라 하지 않는다.**

    * **`회원이라는 개념 자체`가 리소스다.**

<br>

### 리소스 식별, URI 계층 구조를 활용한 설계

* 회원 목록 조회 : /members
* 회원 조회 : /members/{id}
* 회원 등록 : /members/{id}
* 회원 수정 : /members/{id}
* 회원 삭제 : /members/{id}

#### 💡 계층 구조상 상위를 컬렉션으로 보고 복수를 사용하기를 권장한다. (member → members)

<br><br>

### 리소스와 행위를 분리

* URI는 리소스만 식별한다.
* 리소스와 해당 리소스를 대상으로 하는 행위를 분리한다.
    * 리소스 : 회원
    * 행위 : 조회, 등록, 삭제, 변경
    * `리소스는 명사`, `행위는 동사`


* 행위(메서드)는 어떻게 구분하는가 ❓
    * HTTP 메서드를 통해 구분 ❗

<br><br>

### HTTP 메서드 종류

#### 💡 주요 메서드

**`GET`** : 리소스 조회

**`POST`** : 요청 데이터 처리, 주로 등록에 사용

**`PUT`** : 리소스를 대체하며 해당 리소스가 없으면 생성

**`PATCH`** : 리소스 부분 변경

**`DELETE`** : 리소스 삭제

<br>

#### 기타 메서드

HEAD : GET과 동일하지만 메시지 부분을 제외하고, 상태 줄과 헤더만 반환

OPTIONS : 대상 리소스에 대한 통신 가능 옵션(메서드)을 설명. (주로 CORS에서 사용한다)

CONNECT : 대상 자원으로 식별되는 서버에 대한 터널을 설정

TRACE : 대상 리소스에 대한 경로에 따라 메시지 루프백 테스트를 수행

<br><br>

## HTTP 메서드 - GET, POST

---

### GET

<img src="https://velog.velcdn.com/images/daydream/post/81de3583-7bb3-4130-bfa1-cc5072c01773/image.png" width="350">

* 주로 리소스 조회에 사용된다.
* 서버에 전달하고 싶은 데이터는 query(쿼리 파라미터, 쿼리 스트링)를 통해 전달한다.
* 메세지 바디를 사용할 수는 있지만 지원하지 않는 곳이 더 많아 권장하지 않는다.

<br>

### 리소스 조회

|메시지 전달|서버 도착|응답 데이터|
|---|---|---|
|<img src="https://velog.velcdn.com/images/daydream/post/07f887e3-8933-4c66-b647-b8cd90f862d4/image.png" width="300">|<img src="https://velog.velcdn.com/images/daydream/post/970921fb-393c-47f1-bd4a-77071fd27229/image.png" width="300">|<img src="https://velog.velcdn.com/images/daydream/post/a109348d-36b1-4da3-a100-2d739bbbec7b/image.png" width="300">|

<br>

1. 클라이언트에서 /members/100으로 100번 유저를 조회해서 정보를 달라고 GET 요청을 한다.


2. 서버에서는 받은 메시지를 분석해 내부의 유저정보를 조회한 뒤 결과 Response를 만든다.


3. 응답 메시지를 받았고, 정상적으로 받았기에 200 OK status를 가지고 회원정보도 담겨있다.
   위 예시에서는 JSON이지만 실제로는 HTML 일 수도 있고 다양하다.

<br><br>

### POST

<img src="https://velog.velcdn.com/images/daydream/post/41ed0a48-57dc-4060-877b-bb86ad095891/image.png" width="350">

* 요청 데이터 처리


* 메시지 바디를 통해 서버로 요청 데이터를 전달한다.


* 서버는 요청 데이터를 처리한다.
    * 메세지 바디를 통해 들어온 데이터를 처리하는 모든 기능을 수행한다.


* 주로 전달된 데이터로 신규 리소스 등록, 프로세스 처리에 사용된다.

<br>

### 리소스 등록

|메시지 전달|신규 리소스 생성|응답 데이터|
|---|---|---|
|<img src="https://velog.velcdn.com/images/daydream/post/ceb9f981-dd9b-4ed3-bd72-e0c0b95dedff/image.png" width="350">|<img src="https://velog.velcdn.com/images/daydream/post/552541a9-4c1c-4eae-8e19-3950639cb762/image.png" width="350">|<img src="https://velog.velcdn.com/images/daydream/post/04d730c8-76f4-426b-83fd-34486beea6f9/image.png" width="350">|

<br>

1. 클라이언트는 메시지 바디에 등록할 회원 정보를 JSON 형태로 만들어 담는다.


2. 그리고 해당 정보를 서버로 전송한다.


3. 서버에서는 받은 메시지를 분석해 데이터베이스에 등록한다. 이때 신규 아이디도 생성된다.


4. Location이라는 헤더 정보로 회원이 생성된 경로를 첨부한다.


5. 신규 회원에 대한 데이터를 바디에 담아서 보내준다.

<br><br>

### POST는 요청 데이터를 어떻게 처리하는가?

* 스펙 : POST 메서드는 대상 리소스의 고유한 의미 체계에 따라 포함된 표현을 처리하도록 요청한다.
    <br><br>
    * HTML 양식에 입력된 필드와 같은 데이터 블록을 데이터 처리 프로세스에 제공.
    * 게시판, 뉴스 그룹, 블로그 또는 유사한 기사 그룹에 메시지 게시
    * 서버가 아직 식별하지 않은 새 리소스 생성
    * 기존 자원에 데이터 추가


* 즉, 리소스 URI에 POST 요청이 오면 요청 데이터를 어떻게 처리할지 리소스마다 따로 정해야 하기에 따로 정해진 것은 없다.

<br><br>

### 정리

<br>

#### 1. 새 리소스 생성 (등록)

* 서버가 아직 식별하지 않은 새 리소스를 생성, ex) 회원 아이디

<br>

#### 2. 요청 데이터 처리

* 단순히 데이터를 생성하거나 변경하는 것을 넘어 프로세스를 처리해야 하는 경우


* POST의 결과로 새로운 리소스가 생성되지 않을 수도 있다.
    * `POST` `/order/{orderId}/start-delivery` (컨트롤 URI)
      리소스 단위 설계가 힘든 경우 위처럼 행위가 포함된 URI를 설계할 수도 있다.
  
    * 이 경우를 `컨트롤 URI`라 한다.

<br>

#### 3. 다른 메서드로 처리하기 애매한 경우

* JSON으로 조회 데이터를 넘겨야 하는데, GET 메서드를 사용하기 애매한 경우 POST를 사용한다.

<br><br>

## HTTP 메서드 - PUT, PATCH, DELETE

---

### PUT

* 리소스가 있을 경우 대체하고 없을 경우 생성

<br>

#### ✅ 그럼 POST와 무슨 차이점이 있을까?

* **`클라이언트가 리소스를 식별한다.`**
    * PUT /members/100 HTTP/1.1 이런 식으로 Location을 알고 있어야 한다.

<br>

* 쉽게 이야기해서 내용을 덮어버린다.

|||
|---|---|
|<img src="https://velog.velcdn.com/images/daydream/post/4f3ad48a-f7f8-452e-a152-245ac7bdcd31/image.png" width="450">|<img src="https://velog.velcdn.com/images/daydream/post/df7cf3df-65e9-468e-826f-282f1e42e14d/image.png" width="450">|

<br><br>

### PATCH

* 리소스 부분 변경

PUT과 보내는 양식은 비슷하지만, 서버에서는 PATCH로 전송된 경우 대체가 아닌 필요한 부분만 업데이트하는 방식이다.

<br>

|||
|---|---|
|<img src="https://velog.velcdn.com/images/daydream/post/0ff12aa2-2f06-48d0-9dbb-f54524a0615c/image.png" width="450">|<img src="https://velog.velcdn.com/images/daydream/post/b75987b5-7b38-4193-971d-673516c1251c/image.png" width="450">|

<br><br>

### DELETE

* 리소스 제거

<br>

|||
|---|---|
|<img src="https://velog.velcdn.com/images/daydream/post/99862b71-c932-43f3-8a44-38bf21747066/image.png" width="450">|<img src="https://velog.velcdn.com/images/daydream/post/be13e2ac-7188-4566-9429-dd8ccc559342/image.png" width="450">|

<br><br>

## HTTP 메서드 속성

---

### 안전(Safe Methods)

* 호출해도 리소스가 변경되지 않는다.
    * `GET` 같은 경우에 조회만 해서 리소스를 변경하지 않기에 안전하다.
    * `POST`, `PUT`, `DELETE`, `PATCH` 같은 경우 리소스를 변경하기에 안전하지 않다.


* **안전의 범위는 해당 리소스만 고려**하기에, 외적인 요소까지 고려하지 않는다.
    * 예를 들어 리소스 호출 시, 로그가 남는다 하더라도 리소스에 대한 영향은 아니기에 고려하지 않는다.

<br><br>

### 멱등(Idempotent Methods)

#### 💡 ```f(f(x)) = f(x)```

* 메서드를 다 회차 호출하더라도 결과가 동일해야 한다.


* 멱등 메서드 <br><br>
    * `GET` : 몇 번을 조회하더라도 같은 결과가 조회된다.
        * 회원 정보를 몇 번을 조회한다고 정보가 달라지지 않는다.
          <br><br>
    * `PUT` : 결과를 대체한다. 따라서 같은 요청을 여러 번 해도 최종 결과는 같다.
      <br><br>
    * `DELETE` : 결과를 삭제한다. 같은 요청을 여러 번 해도 삭제된 결과는 같다.
      <br><br>  
    * `POST` : 멱등이 아니다. 두 번 호출하면 에러가 발생할 수 있다.
        * POST로 주문을 두 번 호출하면 결제가 중복될 수 있다.

<br>

### 어떻게 활용할까 ❓

* 자동 복구 메커니즘


* 서버가 TIMEOUT 등으로 정상 응답을 하지 못했을 때 클라이언트에서 같은 요청을 해도 되는가에 대한 판단 근거가 된다.
<br><br>
    * Socket.IO를 통해 소켓 통신을 할 때 접속 실패를 하게 되면 다시 접속 시도를 할 수 있다.
      <br><br>
    * webRTC에서 두 클라이언트 간에 SDP 관계 수립 후 영상을 송/수신해야 할 때 제대로 데이터가 오지 않는 경우 다시 negotiation을 시도한다.

<br>

* 멱등은 외부 요인으로 인해 리소스가 변경되는 것은 고려하지 않는다. 내가 호출하는 것에 한정한다.
  <br><br>
    * 내가 회원정보를 조회를 다시 할 때 그 사이에 다른 클라이언트의 요청으로 해당 회원정보가 변경돼서 조회 결과가 달라지는 것은 고려하지 않는다.

<br><br>

### 캐시 가능(Cacheable Methods)

* 응답 결과 리소스를 캐시 해서 사용해도 될까?


* GET, HEAD, POST, PATCH는 캐시가 가능하다.


* 하지만, 실제로는 GET, HEAD 정도만 캐시로 사용한다.
    * POST, PATCH는 본문 내용까지 캐시 키로 고려해야 하는데 구현이 쉽지 않다.
    * GET은 URL만 캐시 키로 관리하면 돼서 구현이 쉽기에 사용이 편하다.

