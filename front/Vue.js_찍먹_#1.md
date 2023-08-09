# Vue.js 찍먹 #1 - 간단한 원룸 사이트 UI

<br>

## 목차

- DOM 요소

- 데이터 바인딩

- v-for 디렉티브 사용하기

- v-if 디렉티브 사용하기

- @click 이벤트 사용하기

- import/export 구문 이용하기

- Vue.js - Code

<br><br>

## DOM(Document Object Model) 요소

Vue.js의 DOM 요소는 브라우저가 해석한 HTML을 표현하며, Vue 인스턴스와 데이터 바인딩을 통해 상호작용. <br>

Vue.js는 실제 DOM을 직접 변경하는 대신, 효율적인 성능을 위해 가상 DOM 개념을 사용하여 변경사항을 적용. <br>

데이터를 DOM 요소에 바인딩하는 방식을 사용하여 애플리케이션 상태 관리를 쉽게 하고, 코드를 명확하게 만들 수 있다. <br>

Vue.js의 지시자를 통해 DOM 요소의 속성이나 이벤트에 데이터를 연결할 수 있다.

<br><br>

## 데이터 바인딩

데이터 바인딩은 화면에 표시되는 데이터와 Vue 인스턴스의 데이터를 서로 연결시키는 과정. <br>
이를 통해 인스턴스의 데이터가 변경되면 자동으로 화면에도 변경사항이 반영된다. <br>

{{ item }}와 같이 중괄호를 사용하여 데이터를 DOM에 바인딩할 수 있고, v-bind 또는 그 축약형 :를 사용하여 속성값을 바인딩할 수 있다.

<br><br>

## v-for 디렉티브 사용하기

v-for 디렉티브를 사용하면 배열이나 객체의 요소들을 순회하면서 동적으로 DOM 요소를 생성할 수 있다. <br>

<br><br>

## v-if 디렉티브 사용하기

v-if 디렉티브는 주어진 조건이 참일 경우에만 해당 DOM 요소를 렌더링. <br>

<br><br>

## @click 이벤트 사용하기

Vue.js에서는 @click과 같은 문법을 사용하여 DOM 요소에 클릭 이벤트를 바인딩할 수 있다. <br>

<br><br>

## import/export 구문 이용하기

ES6 모듈 시스템의 import와 export 구문을 이용하여 코드를 모듈화하고 관리할 수 있다. <br>
이 코드에서는 원룸 데이터를 외부 파일에서 가져오기 위해 import 구문을 사용하였고, <br>
생성한 Vue 컴포넌트를 export 구문을 통해 내보내어 다른 파일에서 사용.

<br><br>

## Vue.js - Code

```vue

<template>
  <div class="black-bg" v-if="isModalOpen">
    <div class="white-bg">
      <h4>상세페이지</h4>
      <p>상세페이지내용임</p>
      <button @click="isModalOpen = false">닫기</button>
    </div>
  </div>

  <div class="menu">
    <a v-for="(item, index) in menuItems" :key="index">{{ item }}</a>
  </div>

  <div v-for="(room, index) in rooms" :key="room.id">
    <img :src="room.image" class="room-img">
    <h4 @click="isModalOpen = true">{{ room.title }}</h4>
    <p>{{ room.price }} 만원</p>
    <button @click="reportCount[index]++">허위매물신고</button>
    <span>신고수 : {{ reportCount[index] }}</span>
  </div>
</template>

<script>
import roomData from './oneroom.js'

export default {
  name: 'App',

  data() {
    return {
      reportCount: [0, 0, 0, 0, 0, 0],
      menuItems: ['Home', 'Shop', 'About'],
      products: ['역삼동 원룸', '천호동 원룸', '마포구 원룸'],
      prices: ['60 만원', '50 만원', '40 만원'],
      isModalOpen: false,
      rooms: roomData,
    }
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.menu {
  background: darkslateblue;
  padding: 15px;
  border-radius: 5px;
}

.menu a {
  color: white;
  padding: 10px;
}

body {
  margin: 0;
}

div {
  box-sizing: border-box;
}

.black-bg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  padding: 20px;
}

.white-bg {
  width: 100%;
  background: white;
  border-radius: 8px;
  padding: 20px;
}

.room-img {
  width: 100%;
  margin-top: 40px;
}
</style>
```