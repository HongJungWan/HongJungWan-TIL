# Vue.js 사람이 되어가는 중 #2

<br>

* 단순 클론 코딩은 남는 게 없다.
* [공식 문서](https://ko.vuejs.org/guide/essentials/list.html)

<br>

## 데이터 바인딩

```vue
<span>메세지: {{ msg }}</span>
```

<br>

* 이중 중괄호 태그 내 msg는 해당 컴포넌트 인스턴스의 msg 속성의 값으로 대체.


* msg 속성이 변경될 때마다 업데이트.

<br><br>

‼ 이중 중괄호는 HTML 속성(attribute) 내에서 사용할 수 없다. -> `v-bind` 디렉티브 사용

```vue
<div v-bind:id="dynamicId"></div>
```

<br>

‼ `v-bind` 단축 문법

```vue
<div :id="dynamicId"></div>
```

<br><br><br>

## 조건부 렌더링, `v-if`

* v-if 디렉티브는 조건부로 블록을 렌더링하는 데 사용.

<br>

```vue
<button @click="awesome = !awesome">전환</button>

<h1 v-if="awesome">Vue는 정말 멋지죠!</h1>
<h1 v-else>아닌가요? 😢</h1>
```

<br><br>

### v-show

* 엘리먼트를 조건부로 표시하는 다른 옵션은 `v-show` 디렉티브.

<br>

```vue
<h1 v-show="ok">안녕!</h1>
```

* 차이점은 `v-show`가 있는 엘리먼트는 항상 렌더링되고 DOM에 남아 있다.
* v-show는 `<template`> 엘리먼트를 지원하지 않으며 `v-else`와 상호작용하지 않는다.

<br><br><br>

## 리스트 렌더링, `v-for`

* `v-for` 디렉티브를 사용하여 배열을 리스트로 렌더링할 수 있다.


* `v-for` 디렉티브는 item in items 형식의 특별한 문법이 필요하다.

<br>

```vue
data() {
  return {
    items: [{ message: 'Foo' }, { message: 'Bar' }]
  }
}
```

```vue
<li v-for="item in items">
  {{ item.message }}
</li>
```

<br><br>

* `v-for`는 현재 아이템의 인덱스를 가리키는 두 번째 별칭도 지원한다.

```vue
<li v-for="(item, index) in items">
  {{ index }}, {{ item.message }}
</li>
```

<br><br><br>

### 객체에 v-for 사용

* `v-for`를 객체의 속성을 반복하는 데 사용할 수 있다.

<br/>

```object
data() {
  return {
    myObject: {
      title: 'Vue에서 목록을 작성하는 방법',
      author: '홍길동',
      publishedAt: '2016-04-10'
    }
  }
}
```

<br/>

```vue
<ul>
  <li v-for="value in myObject">
    {{ value }}
  </li>
</ul>
```

<br/><br/>

속성명을 가리키는 두 번째 별칭를 사용할 수도 있다.

```vue
<li v-for="(value, key) in myObject">
  {{ key }}: {{ value }}
</li>
```

<br/>

인덱스를 가리키는 세 번째 별칭를 사용할 수도 있다.

```vue
<li v-for="(value, key, index) in myObject">
  {{ index }}. {{ key }}: {{ value }}
</li>
```

<br/><br/>

### 숫자 범위에 v-for 사용

* `v-for`는 정수를 사용할 수도 있다.

<br/>

```vue
<span v-for="n in 10">{{ n }}</span>
```

<br/><br/>

### `<template>`에서 v-for 사용

* `v-if`와 유사하게 `<template>` 태그에 `v-for`를 사용할 수 있다.

<br/>

```vue
<ul>
  <template v-for="item in items">
    <li>{{ item.msg }}</li>
    <li class="divider" role="presentation"></li>
  </template>
</ul>
```

<br/><br/>

### key를 통한 상태유지

<br/><br/>

### 컴포넌트에 v-for 사용

<br><br><br>

## Form 입력 바인딩, input (v-model)

<br><br><br>

## import/export

<br><br><br>

## component

* 컴포넌트를 사용하면 UI를 재사용 가능한 일부분으로 분할하고, 각 부분을 개별적으로 다룰 수 있다.

<br>



<br><br><br>

## props

<br><br><br>

## custom event

<br><br><br>

## watcher

