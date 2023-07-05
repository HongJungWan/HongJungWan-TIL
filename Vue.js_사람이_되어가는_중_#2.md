# Vue.js 사람이 되어가는 중 #2

<br>

* 단순 클론 코딩은 남는 게 없다.
* 생각하는 걸 두려워하지 말자.
* [공식 문서](https://ko.vuejs.org/guide/essentials/list.html)

<br>

### 솔직하자. 💭 나는 무엇을 모르는가

<br><br>

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

<br><br>

## v-for

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

쭈욱 이어서 작성하기 ~ (객체에 v-for 사용하기 부터)

<br><br>

## 이벤트 핸들러 click

<br><br>

## v-if

<br><br>

## import/export

<br><br>

## component

<br><br>

## props

<br><br>

## custom event

<br><br>

## input (v-model)

<br><br>

## watcher

