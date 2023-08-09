## HTTP 메서드 활용

<br>

### 💡 클라이언트에서 서버로 데이터 전송

---

### `데이터 전달 방식`

<br>

#### 쿼리 파라미터를 통한 데이터 전송
* `GET`에서 많이 사용하고 `정렬 필터`나 `검색어`를 사용할 때는 `쿼리 파라미터`를 많이 사용한다.

<br>

#### 메시지 바디를 통한 데이터 전송
* `POST`, `PUT`, `PATCH`에서 사용되며 `회원 가입`, `상품 주문`과 같이 `리소스를 등록`하거나 `변경`하는 데에 사용한다.

<br><br>

### `클라이언트에서 서버로 데이터를 전송하는 4가지 상황`

<br>

#### 1. 정적 데이터 조회

* `이미지`, `정적 텍스트 문서`
  * 쿼리 파라미터 미 사용
  * 정적 데이터는 일반적으로 쿼리 파라미터 없이 `리소스 경로로 단순하게 조회가 가능`하다.

<br>

<img src="https://velog.velcdn.com/images/daydream/post/cb7651d2-d61c-4a8b-86cc-d2b4a3b49f20/image.png" width="700">

<br><br>

#### 2. 동적 데이터 조회

* 주로 `검색`, 게시판 목록에서 `정렬 필터`
  * `쿼리 파라미터를 사용`한다.
  * 서버에서는 쿼리 파라미터를 기반으로 필터나 검색 결과 등 처리 결과를 응답해 준다.


* 조회는 `GET`을 사용한다.
* `GET`은 쿼리 파라미터를 사용해서 데이터를 전달한다.

<br>

<img src="https://velog.velcdn.com/images/daydream/post/edf852ec-a7da-47e8-bcd0-f0c99c600ad7/image.png" width="700">

<br><br>

#### 3. HTML Form을 통한 데이터 전송

* 회원 가입, 상품 주문, 데이터 변경

<br>

<img src="https://velog.velcdn.com/images/daydream/post/3967d240-c7c1-4dbc-89b0-76e9e21df1ae/image.png" width="700">

<br>

* 웹 브라우저는 submit 버튼을 누르면 Form 태그의 정보를 취합해 **action에 작성된 경로로 해당 method 타입의 요청**을 하게 되는데, Content-Type을 보면 x-www-form-urlencoded라고 되어있다.


* 쿼리 파라미터와 거의 동일한 방식으로 서버에 전송된다.
    * `username=kim&age=20`

<br>

* urlencoded로 돼있어서 body에 담긴 내용에서 한글처럼 담긴 내용이 `인코딩`되어 넘어간다.

<br>

<img src="https://velog.velcdn.com/images/daydream/post/3b5c09ec-5605-4423-9535-2fa3ee6684b7/image.png" width="700">

* `만약 method 타입이 get으로 되어있다면 메시지 바디에 담겨있는 내용이 query string으로 path 뒤에 조합된다`.

<br><br>

#### 💡 파일이나 이미지 같은 자료는 어떻게 전송되는가 ❓

<img src="https://velog.velcdn.com/images/daydream/post/44cad041-0b0b-4ad2-b448-73a9aa9b0623/image.png" width="700">

* `enctype`을 `multipart/form-data`로 작성해 해당 폼에 파일이 있다는 것을 표시한다.
<br><br>
  * `multipart/form-data` 형식이라면 HTTP 메시지에 임의의 구분자(boundary=——-XXX) 가 Form 데이터간 구분을 지어준다.
    <br><br>
  * 바이너리 데이터 전송 시 사용한다.

<br>
* HTML Form 전송은 `GET`, `POST`만 지원한다.

<br><br>

#### 4. HTTP API를 통한 데이터 전송

<img src="https://velog.velcdn.com/images/daydream/post/30035b49-cf7d-4d29-a1f6-bd84cb0845e1/image.png" width="700">

* 회원 가입, 상품 주문, 데이터 변경을 할 수 있는데 서버 to 서버, 앱 / 웹 클라이언트(Ajax)에서 사용된다.

<br>

* 서버 to 서버
    * 백엔드 시스템 통신


* 앱 클라이언트
    * 아이폰, 안드로이드


* 웹 클라이언트
    * HTML에서 Form 전송 대신 자바 스크립트를 통한 통신에 사용한다. (Ajax)

<br>

* `POST`, `PUT`, `PATCH` : 메시지 바디를 통해 데이터 전송
* `GET` : 조회, 쿼리 파라미터로 데이터 전달
* `Content-Type` : `application/json`을 주로 사용 (사실상 표준)
    * TEXT, XML, JSON 등등

<br><br>

## 💡 HTTP API 설계 예시

---

회원 관리를 예시로 API를 다양한 방법으로 설계해 보자.

* **HTTP API - 컬렉션**
    * POST 기반 등록
    * 회원 관리 API 제공

<br>

* **HTTP API - 스토어**
    * PUT 기반 등록
    * 정적 컨텐츠 관리, 원격 파일 관리

<br>

* **HTML FORM 사용**
    * 웹 페이지 회원 관리
    * GET, POST만 지원

<br><br>

### ① API 설계 - POST 기반 등록

* **회원 목록 /members → GET**
    * 회원 정렬 조건이나 검색 조건이 필요하면 쿼리 파라미터를 설계하면 된다.


* **회원 등록 /members → POST**


* **회원 조회 /members/{id} → GET**
    * 계층적 구조로 되어있고 컬렉션 안의 특정 아이디를 조회할 수 있어 가독성이 높다.


* **회원 수정 /members/{id} → PATCH, PUT, POST**
    * PUT은 덮어쓰기고 PATCH는 부분적 업데이트이기에 회원 정보 수정은 PATCH가 좋다.
      하지만, 전부 다 변경해 줘야 하는 경우(ex: 게시판 글 수정하기)에는 PUT을 쓰는 게 좋을 수도 있다.


* **회원 삭제 /members/{id} → DELETE**

<br><br>

### POST - 신규 자원 등록 특징

* 클라이언트는 등록될 리소스의 URI를 모른다.
    * 회원 등록 /members → POST
    * POST /members


* 서버가 새로 등록된 리소스 URI를 생성해 준다.
    * HTTP/1.1 201 Created
      Location: `/members/100`


* 컬렉션(Collection)
    * 서버가 관리하는 리소스 디렉터리
    * 서버가 리소스의 URI를 생성하고 관리
    * 여기서 컬렉션은 /members

<br>

---

<br>

### ② API 설계 - PUT 기반 등록

* 파일 목록 /files → GET
* 파일 조회 /files/{filename} → GET
* 파일 등록 /files/{filename} → PUT
* 파일 삭제 /files/{filename} → DELETE
* 파일 대량 등록 /files → POST

<br><br>

### PUT - 신규 자원 등록 특징

* 클라이언트가 리소스 URI를 알고 있어야 한다.
    * 파일 등록 /files/{filename} → PUT
    * PUT **/files/star.jpg**


* 클라이언트가 직접 리소스의 URI를 지정한다.


* 스토어(Store)
    * 클라이언트가 관리하는 리소스 저장소
    * 클라이언트가 리소스의 URI를 알고 관리
    * 여기서 스토어는 /files

<br>

#### 그럼 위 두 방식 중 무엇을 많이 쓸까 ❓

* **대부분 실무에서는 POST 기반의 컬렉션을 사용**하고, `PUT 방식`은 `파일 관리`같은 특수한 경우에만 사용된다.

<br>

---

<br>

### HTML FORM 사용

* HTML FORM은 GET, POST만 지원한다.
* AJAX 같은 기술을 가용해서 해결 가능 → 회원 API 참고
* 하지만 순수하게 HTML, HTML FORM에서는 GET, POST만 사용 가능
* 그렇기에 제약이 있다.

<br><br>

### ③ HTML FORM 설계

* **회원 목록 `/members` → GET**

<br>

* **회원 등록 폼 `/members/new` → GET**

    * 회원 등록 버튼을 누르면 회원 등록 폼 페이지로 이동

<br>

* **회원 등록 `/members/new`, `/members` → POST**
<br><br>
    * `/members/new`로 페이지 이동과 회원 등록 시 URL 경로를 맞추고 전송 방식만 다르게 하는 방식과, 폼을 가져올 때는 `/new`를 통해 가져오지만 회원 등록은 `/members`으로 하는 방식도 있다.
      <br><br>
    * 전자(경로를 맞추는 상황)로 사용을 하면 Validation 작업으로 새로고침되어 동일 페이지에 가야 하는 경우 간단하게 이전(동일) 페이지로 이동할 수 있다.

<br>

* **회원 조회 `/members/{id}` → GET**


* **회원 수정 폼 `/members/{id}/edit` → GET**


* **회원 수정 `/members/{id}/edit`, `/members/{id}` → POST**

<br>

* **회원 삭제 `/members/{id}/delete` → POST**

    * 삭제인데 POST 메서드를 사용한다. HTML FORM에서는 GET, POST만 사용할 수 있다는 제약 때문에 어쩔 수 없이 URI 경로에 삭제하겠다는 행위를 작성함으로써 기능을 구현한다.
      이런 URI를 `컨트롤 URI`라 한다.

<br><br>

## 참고하면 좋은 URI 설계 개념

---

#### 문서(document)
* 단일 개념(파일 하나, 객체 인스턴스, 데이터베이스 row)
    * 예) `/members/100`, `/file/star.jpg`

<br>

#### 컬렉션(collection)
* 서버가 관리하는 리소스 디렉터리
* 서버가 리소스의 URI를 생성하고 관리
    * 예) `/members`

<br>

#### 스토어(store)
* 클라이언트가 관리하는 자원 저장소
* 클라이언트가 리소스의 URI를 알고 관리
    * 예) `/files`

<br>

#### 컨트롤러(controller), 컨트롤 URI
* 문서, 컬렉션, 스토어로 해결하기 어려운 추가 프로세스 실행
* 동사를 사용한다.
    * 예) `/members/{id}/delete`

