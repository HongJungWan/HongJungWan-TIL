## HTTP 헤더 - 캐시와 조건부 일치

<br>

## 캐시 기본 동작



* 캐시가 어떻게 동작하는지 기본 동작을 통해 알아보자

<br>

<img src="https://velog.velcdn.com/images/daydream/post/7a5d6d5a-733d-4c6e-9652-c38f6e7d5c84/image.png" width="650">

* 클라이언트에서 star.jpg 이미지를 요청한다.


* 서버에서는 해당 이미지가 있으면 응답을 줘야 하는데,
  이미지의 HTTP 헤더+바디를 합쳐 대략 1.1M 정도 용량의 데이터를 응답한다.


* 클라이언트에서는 해당 이미지를 응답받아 사용한다.

<br><br>

<img src="https://velog.velcdn.com/images/daydream/post/534fb3df-6e14-4ea4-9283-11ca85304307/image.png" width="650">

* 클라이언트에서는 star.jpg 이미지를 다시 한번 요청한다.
* 서버에서는 동일한 이미지를 다시 1.1M 정도 용량의 데이터를 응답해 준다.
* 클라이언트에서는 해당 이미지를 응답받아 사용한다.


* <span style="color: darkorange">동일한 이미지를 요청하는 데 네트워크를 통해 같은 데이터를 또 다운로드해야 한다.</span>
* <span style="color: darkorange">용량이 클수록 비용이 커지고 브라우저의 로딩 속도가 느려진다.</span>

<br><br>

<img src="https://velog.velcdn.com/images/daydream/post/6137193d-d351-4681-a263-b9e7b1dea3cf/image.png" width="650">

* 헤더에 `cache-controll` 속성을 넣어주어 캐시가 유효한 시간을 넣어준다. <br>
  위에서는 60초로 설정해 60초 동안은 해당 캐시가 유효하다는 의미다.


* 그럼 브라우저 캐시에 응답 결과를 저장하며 60초간 유효하다.

<img src="https://velog.velcdn.com/images/daydream/post/e7a9cc8b-acf6-423e-aa6d-8b56208d8929/image.png" width="350">

<br><br>

### 캐시를 적용한 두 번째 요청

|||
|---|---|
|<img src="https://velog.velcdn.com/images/daydream/post/810d8413-a5f1-4ffd-90d3-98b37664d34b/image.png" width="300">|<img src="https://velog.velcdn.com/images/daydream/post/db154307-a9fb-4564-a5ad-1d7ea58ddf76/image.png" width="300">|

* 두 번째 요청할 때는 우선 캐시를 조회한다.
* 캐시가 존재하고 아직 60초 이내이기에 유효한 캐시가 있어서 해당 캐시에서 자료를 가져온다.


* 유효시간이 초과한다면 어떻게 될까?
    * 다시 서버에 요청을 하고 60초간 유효한 star.jpg 이미지를 응답받아서 캐시를 업데이트해준다.
    * 이때 다시 네트워크 다운로드가 발생한다.

<br><br>

### 캐시 적용

* 캐시 덕분에 캐시 가능 시간 동안 네트워크를 사용하지 않아도 된다.
* 비싼 네트워크 사용량을 줄일 수 있다.
* 브라우저 로딩 속도가 매우 빠르다.
* 사용자는 매우 빠른 경험을 할 수 있다.

<br><br>

### ❓ 캐시 유효시간이 지나서 다시 요청을 해야 하는 경우

특정 자료가 필요해 요청을 해야하는 경우 캐시 유효시간이 지났지만,
변경이 없기 때문에 해당 데이터를 써도 되는 상황이라면 어떻게 해야 할까 ❓

이를 검증 헤더와 조건부 요청에서 알아본다.

<br><br>

## 검증 헤더와 조건부 요청 ①



* 캐시 유효시간 초과 후, 서버에 다시 요청을 하면 다음과 같은 상황이 생긴다.

<img src="https://velog.velcdn.com/images/daydream/post/8bbb1f9f-0651-4250-920c-6d658409cf61/image.png" width="500">

<br><br>

### 캐시 시간 초과

캐시 만료 후에도 서버에서 데이터를 변경하지 않은 경우 서버에서 동일한 데이터를 요청해서 응답받는 것은 여러모로 비용 낭비다.

이럴 때는 저장해 둔 캐시를 재사용 할 수 있으면 좋은데, 어떻게 클라이언트의 데이터와 서버의 데이터가 동일하다는 것을 알 수 있는지에 대해 알아야 한다.

<span style="color: darkorange">그래서 검증 헤더가 들어가는 것이다.</span>

<img src="https://velog.velcdn.com/images/daydream/post/2d41a1e8-c518-47e5-b647-7e89ea55e635/image.png" width="700">

<br><br>

<img src="https://velog.velcdn.com/images/daydream/post/edc15938-df5f-44bd-84d4-4ecf79e7ac6d/image.png" width="700">

* 데이터가 마지막으로 수정된 시간 정보를 헤더에 작성해 준다
    * `Last-Modified` : 2020년 11월 10일 10:00:00
    * UTC 표기법으로 적어준다.

<br>

* 응답 결과를 캐시에 저장할 때 데이터 최종 수정일도 저장된다.

<img src="https://velog.velcdn.com/images/daydream/post/6c0141b3-80f8-4f60-bc87-d67acbb92d52/image.png" width="350">

<br><br>

<img src="https://velog.velcdn.com/images/daydream/post/40cadf16-5446-4d66-999d-cf9b5eada25c/image.png" width="700">

* 캐시 시간이 초과해서 다시 요청을 해야 하는데, 캐시에 최종 수정일 정보(Last-Modified)가 있다면 요청 헤더에 `if-modified-since`에 해당 날짜를 담아서 서버에 보낸다.


* 서버의 해당 자료의 최종 수정일과 비교해서 데이터가 수정이 안되었을 경우 응답 메시지에 이를 담아서 알려준다.

<br>

<img src="https://velog.velcdn.com/images/daydream/post/01d28afb-0f0b-47a6-a88a-6108e74d3502/image.png" width="700">

* HTTP Body는 응답 데이터에 없다.
* 상태 코드는 304 Not Modified로 변경된 것이 없다는 것을 알린다.
* 그래서 전송 데이터는 바디가 빠졌기에 헤더만 포함된 0.1M만 전송된다.
* 클라이언트에서는 해당 응답을 받은 뒤 캐시를 갱신해 주고 다시 일정 시간(60초) 유효하게 된다.

<br><br>

### 정리

* 캐시 유효 시간이 초과해도, 서버의 데이터가 갱신되지 않으면
* 304 Not Modified + 헤더 메타 정보만 응답한다.
* 클라이언트는 서버가 보낸 응답 헤더 정보로 캐시의 메타 정보를 갱신한다.
* 클라이언트는 캐시에 저장되어 있는 데이터 재활용
* 결과적으로 네트워크 다운로드가 발생하지만 용량이 작은 헤더 정보만 다운로드하면 된다.


매우 실용적인 해결책

<br><br>

## 검증 헤더와 조건부 요청 ②



검증 헤더란 캐시 데이터와 서버 데이터가 같은지 검증하는 데이터로 위에서는 `Last-Modified`를 사용해 보았다. 이런 헤더를 이용해서 클라이언트에서 서버로 요청할 때 검증부 요청 헤더를 만들어서 보내면 된다.

이번에는 `ETag`를 알아보자.

<br>

* 검증 헤더로 조건에 따른 분기
* `If-Modified-Since` : `Last-Modified` 사용
* `If-None-Matach` : `ETag` 사용
* 조건이 만족하면 `200 OK`
* 조건이 만족하지 않으면 `304 Not Modified`

<br><br>

### If-Modified-Since 이후에 데이터가 수정이 되었다면?

<br>

* **데이터 미변경인 경우**
    * 캐시 2020년 11월 10일 10:00:00 👉 서버 2020년 11월 10일 10:00:00
    * **`304 Not Modified, 헤더 데이터만 전송 (Body 없음)`**
    * 전송 용량 0.1M (헤더 0.1M, 바디 1.0M)

<br>

* **데이터가 변경된 경우**
    * 캐시 2020년 11월 10일 10:00:00 👉 서버 2020년 11월 10일 `11:00:00`
    * **`200 OK, 모든 데이터 전송(Body 포함)`**
    * 전송 용량 1.1 (헤더 0.1M, 바디 1.0M)

<br><br>

### Last-Modified, If-Modified-Since의 단점

* **1초 미만(0.x초) 단위로 캐시 조정이 불가능하다.**
* **날짜 기반의 로직을 사용한다.**


* **데이터를 수정해 날짜가 다르지만, 데이터 결과가 똑같은 경우**
    * test.txt 파일의 내용을 A 👉 B로 수정했지만, 다시 B 👉 A로 수정한 경우


* **서버에서 별도의 캐시 로직을 관리하고 싶은 경우**
    * ex) 스페이스나 주석처럼 크게 영향이 없는 변경에서 캐시를 유지하고 싶은 경우

<br><br>

### ETag, If-None-Match

서버에서 완전히 캐시를 컨트롤하고 싶은 경우 `ETag`를 사용하면 된다.

<br>

* **ETag(Entity Tag)**
* 캐시용 데이터에 임의의 고유한 버전 이름을 달아둔다.
    * `ETag : "v1.0"`, `ETag : "a2jiodwjekjl3"`


* 데이터가 변경되면 이 이름을 바꾸어서 변경한다. (Hash를 다시 생성)
    * `ETag : "aaaa"` → `ETag : "bbbb"`


* 단순하게 `ETag`만 보내서 같으면 유지하고 다르면 다시 받는다.

<br><br>

### ETag를 사용한 첫 번째 요청

<img src="https://velog.velcdn.com/images/daydream/post/583313d7-d734-4154-90b4-e721cf1c9d17/image.png" width="650">

* 헤더에 `ETag`를 작성해서 응답해 준다.
* 클라이언트의 캐시에선 `ETag` 값을 저장한다.

<img src="https://velog.velcdn.com/images/daydream/post/8e1b4c62-95dd-4dac-8019-7ba7ef5d3854/image.png" width="350">

<br><br>

### ETag를 사용한 두 번째 요청

#### 캐시 시간 초과

<img src="https://velog.velcdn.com/images/daydream/post/08319bec-e526-45d6-90c7-769218248022/image.png" width="650">

* 캐시 시간이 초과돼서 다시 요청을 해야 하는 경우
* 이때 `If-None-Match`를 요청 헤더에 작성해서 보낸다.

<br><br>

<img src="https://velog.velcdn.com/images/daydream/post/18f13d42-bbe4-4922-8ab1-872758e3ea13/image.png" width="650">

* 서버에서 데이터가 변경되지 않았을 경우 `ETag`는 동일하다. 그래서 `If-None-Match`는 실패다.
* 이 경우 서버에서는 `304 Not Modified`를 응답하며 이때 역시 `HTTP Body`는 없다.
* 브라우저 캐시에서는 응답 결과를 재사용하고 헤더 데이터를 갱신한다.

<img src="https://velog.velcdn.com/images/daydream/post/ece228c7-0653-4197-bf61-4906fcf9c187/image.png" width="350">

<br>

### 정리

* `ETag`만 서버에 보내 동일하면 유지하고 다르면 다시 받는다.
* `캐시 제어 로직을 서버에서 관리`한다.
* `클라이언트`는 단순하게 이 값을 서버에 제공한다
    * **`캐시 메커니즘을 알 필요가 없다.`**


* 예)
    * 서버는 베타 오픈 기간 3일간 파일이 변경되어도 `ETag`를 동일하게 유지
    * 애플리케이션 배포 주기에 맞춰서 `ETag`를 모두 갱신

<br><br>

## 캐시와 조건부 요청 헤더



캐시와 관련된 헤더들과 조건부 요청 헤더를 정리하는 시간을 가져보자.

<br>

### 캐시 제어 헤더

캐시 지시어(directives)

* <span style="color: dark orange">**Cache-Control : max-age**</span>
    * 캐시 유효 시간이고 초 단위이다.
* <span style="color: dark orange">**Cache-Control : no-cache**</span>
    * 데이터는 캐시 해도 되지만, 항상 원(origin) 서버에 검증하고 사용해야 한다.
* <span style="color: dark orange">**Cache-Control : no-store**</span>
    * 데이터에 민감한 정보가 있기에 저장하면 안 된다.
      (메모리에서 사용하고 최대한 빨리 삭제)

<br>

### Pragma

캐시 제어(하위 호환)

* Pragma : no-cache
* HTTP 1.0 하위 호환
* 현재는 거의 사용되지 않는다.

<br>

### Expires

캐시 만료일 지정(하위 호환)

* expires : Mon, 01 Jan 1990 00:00:00 GMT
* 캐시 만료일을 정확한 날짜로 지정
* HTTP 1.0부터 사용
* **지금은 더 유용한 max-age를 권장한다.**
* **그래서 max-age와 동시에 사용되면 Expires는 무시된다.**

<br><br>

## 프록시 캐시



<img src="https://velog.velcdn.com/images/daydream/post/7ed0dfee-e870-4190-a577-e55eab8336e7/image.png" width="650">

<br>

한국에 있는 클라이언트에서 별 이미지가 필요한 상황이고, 이미지의 원서버가 미국에 있다고 가정해 보자. 한국에서 미국까지 직접 접근하여 이미지를 가져오는데 0.5초 걸린다고하면 클라이언트는 모두 0.5초를 기다려야 해당 이미지를 받을 수 있다.

이 같은 상황에서 소요되는 시간을 줄이기 위해 프록시 캐시가 도입되었다.

<br><br>

### 프록시 캐시 도입 - 첫 번째 요청

<img src="https://velog.velcdn.com/images/daydream/post/8a4e82c1-ec39-43c8-9186-d9f649d45295/image.png" width="650">

<br>

* 프록시 캐시서버를 두고, 클라이언트는 프록시 캐시서버를 통해 자료를 가져온다.


* 여러 사람이 찾은 자료일수록 이미 캐시에 등록되어 있기에 빠른 속도로 자료를 가져올 수 있다.
    * 같은 국내에 있기에 원서버에 접근하는 것보다 훨씬 빠른 속도에 자료를 가져올 수 있다.
    * ex) 유튜브에서 고용량의 영상도 빨리 볼 수 있는 이유.


* 클라이언트에서 사용되고 저장되는 캐시를 private 캐시라 하고 프록시 캐시서버의 캐시를 public 캐시라 한다.

<br><br>

### Cache-Control

#### 캐시 지시어(directives) - 기타

* **Cache-Control : public**
    * 응답이 public 캐시에 저장되어도 된다.


* **Cache-Control : private**
    * 응답이 해당 사용자만을 위한 것으로 private 캐시에 저장해야 한다.(`default`)


* **Cache-Control : s-maxage**
    * 프록시 캐시에만 적용되는 max-age


* **Age : 60(HTTP 헤더)**
    * 오리진 서버에서 응답 프록시 캐시 내에 머문 시간(초)

<br><br>

## 캐시 무효화



```
Cache-Control: no-cache, no-store, must-revalidate
Pragma: no-cache
```

정말 캐시 무효화를 확실하게 해야 하는 경우 위와 같이 적용하면 된다.

<br><br>

### 캐시 지시어(directives) - 확실한 캐시 무효화

* <span style="color: darkorange">**Cache-Control : no-cache**</span>
    * **데이터는 캐시 해도 되지만, 항상 원 서버에 검증하고 사용해야 한다.**
    * 이름 때문에 캐시를 사용 안 한다고 생각할 수 있으니 주의하자.


* <span style="color: darkorange">**Cache-Control : no-store**</span>
    * 데이터에 민감한 정보가 있기에 저장하면 안 되니 메모리에서 사용하고 최대한 빨리 삭제


* <span style="color: darkorange">**Cache-Control : must-revalidate**</span>
    * **캐시 만료 후 최초 조회 시 원 서버에 검증해야 한다.**
    * 원 서버 접근 실패 시 반드시 오류가 발생해야 한다. - `504(Gateway Timeout)`
    * must-revalidate는 캐시 유효시간이라면 캐시를 사용한다.


* <span style="color: darkorange">**Pragma: no-cache**</span>
    * HTTP 1.0 하위 호환

<br>

### no-cache와 must-revalidate를 왜 같이 써야 하는가 ❓

둘 다 원 서버에 검증해야 하는데 어째서 혼용해서 써야 할까?

<br>

#### no-cache 기본 동작

<img src="https://velog.velcdn.com/images/daydream/post/93575922-f569-4c39-9231-f1c7900f522f/image.png" width="750">

<br>

* 캐시 서버 요청 후, 프록시 캐시 서버에 도착하면 no-cache인 경우 원 서버에 요청을 한다.
  그리고 원 서버에서 검증 후 응답을 한다.

<br>

* 하지만, **프록시 캐시 서버와 원 서버 간 네트워크 연결이 단절되어 접근이 불가능하다면**,
  no-cache에서는 응답으로 오류가 아닌 오래된 데이터라도 보여주자는 개념으로 `200OK`으로 응답을 한다.

<img src="https://velog.velcdn.com/images/daydream/post/29fbcb47-bd18-45dc-83ab-f8acbabfcd5f/image.png" width="750">

<br><br>

#### must-revalidate 기본 동작

<img src="https://velog.velcdn.com/images/daydream/post/94b88e1d-65d0-433d-971d-646146820c17/image.png" width="750">

* 캐시 서버 요청 후, 프록시 캐시 서버와 원 서버 간의 접근이 불가능하다면 `must-revalidate`에서는 항상 오류가 발생해야 한다. (`504 Gateway Timeout`)

<br><br>

### 정리

`no-cache`로 무조건 원 서버에서 검증을 하고,
`must-revalidate`로 원 서버와 검증이 안되면 오류가 발생하도록 한다.

마지막으로 Pragma를 사용해서 혹시 모를 1.0 이하 버전의 하위 호환도 적용해 준다. 
