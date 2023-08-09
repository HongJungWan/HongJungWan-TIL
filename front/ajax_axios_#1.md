# AJAX(Asynchronous JavaScript and XML) - Axios #2

<br>

## Axios란,

* Axios는 Node.js와 브라우저를 위해 Promise API를 활용하는 HTTP 통신 라이브러리.


* 비동기 HTTP 통신을 할 수 있으며, return을 Promise 객체로 하기 때문에 response 데이터를 다루기 수월.

<br>

```vue
import axios from "axios" 

//post
axios({
  method: 'post',
  url: '.../test',
  data: {
    name: 'test',
    value: 'test'
  }
}).then((response) => console.log(response));

//get
axios.get('.../test') 
     .then((Response)=>{console.log(Response.data)}) 
     .catch((Error)=>{console.log(Error)})
```

<br><br>

## Axios의 장점

* Fetch와 다르게 response timeout 처리 방법을 갖고 있다.


* Promise 기반으로 다루기 쉽다.


* 크로스 브라우징이 우수하다.

<br>

## Axios의 단점

* 모듈 설치 팔요.

<br><br>

## Axios, Fetch API 비교
![스크린샷(12)](https://github.com/REST-API-Server/resell-back-end/assets/76596316/2b04ef81-dc2f-4cfe-9091-97878ab40488)

<br><br>

## etc. ♻

<br>

### Promise 란?

* 비동기 작업의 처리를 나타내는 객체.


* 비동기 작업 처리에 있어서 기존의 콜백(Callback) 함수 방식의 문제점을 (opens new window) 개선한 방식.

<br>

#### 👍 장점

* 콜백 지옥의(Pyramid of Doom) 해결


* 에러의 처리의 용이성

<br>

### 크로스 브라우징(Cross Browsing)이란?

* 웹 페이지의 상호 호환성을 의미.
  * 서로 다른 브라우저들끼리 서로 호환이 가능하도록 하는 것.
  * 중요한 것은 ‘크로스 브라우징’이 모든 브라우저에서 100% 똑같이 보이도록 만드는 동일성이 아닌 동등성(등가성)을 의미.

<br>

```agsl
Cross Browsing 이란 적어도 표준 웹 기술을 채용하여 다른 기종 혹은 플랫폼에 따라 
달리 구현되는 기술을 비슷하게 만듦과 동시에 어느 한쪽에 최적화되어 치우지지 않도록,
공통 요소를 사용하여 웹페이지를 제작하는 기법을 말하는 것이다. 
또한, 지원할 수 없는 다른 웹브라우저를 위한 장치를 구현하여 
모든 웹브라우저 사용자가 방문했을 때 정보로서의 소외감을 느끼지 않도록 하는 방법론적 가이드를 의미하는 것이다. 
```