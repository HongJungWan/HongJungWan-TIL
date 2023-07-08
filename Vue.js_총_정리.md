# Vue.js 문법 총 정리

<br>

* 단순 클론 코딩은 남는 게 없다.
* [공식 문서](https://ko.vuejs.org/guide/essentials/list.html)

<br>

| 항목              | 설명                                                    |
|-------------------|---------------------------------------------------------|
| 데이터 바인딩     | Vue.js에서 데이터를 HTML 요소에 연결하는 방법. `v-bind` 디렉티브를 사용. |
| 조건부 렌더링     | `v-if`, `v-else`, `v-else-if` 등의 디렉티브를 사용하여 조건에 따라 요소를 렌더링. |
| 리스트 렌더링     | `v-for` 디렉티브를 사용하여 배열 내의 항목에 대해 요소를 반복적으로 렌더링합니다. |
| Form 입력 바인딩 | `v-model` 디렉티브를 사용하여 양방향 데이터 바인딩을 설정하고, 입력과 상태를 동기화. |
| import / export | ES6 모듈 시스템에서 값을 가져오거나 내보내는 방법.           |
| component      | 독립적이고 재사용 가능한 Vue.js의 빌딩 블록.    |
| props           | 부모 컴포넌트에서 자식 컴포넌트로 데이터를 전달하는 방법. |

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

<br>

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

<br>

```vue
<ul>
  <li v-for="value in myObject">
    {{ value }}
  </li>
</ul>
```

<br><br>

속성명을 가리키는 두 번째 별칭를 사용할 수도 있다.

```vue
<li v-for="(value, key) in myObject">
  {{ key }}: {{ value }}
</li>
```

<br>

인덱스를 가리키는 세 번째 별칭를 사용할 수도 있다.

```vue
<li v-for="(value, key, index) in myObject">
  {{ index }}. {{ key }}: {{ value }}
</li>
```

<br><br>

### 숫자 범위에 v-for 사용

* `v-for`는 정수를 사용할 수도 있다.

<br>

```vue
<span v-for="n in 10">{{ n }}</span>
```

<br><br>

### `<template>`에서 v-for 사용

* `v-if`와 유사하게 `<template>` 태그에 `v-for`를 사용할 수 있다.

<br>

```vue
<ul>
  <template v-for="item in items">
    <li>{{ item.msg }}</li>
    <li class="divider" role="presentation"></li>
  </template>
</ul>
```

<br><br>

### key를 통한 상태유지

* Vue가 각 노드의 ID를 추적하고 기존 엘리먼트를 재사용하고 재정렬할 수 있도록, <br> 각 항목에 대해 고유한 key 속성을 제공해야 한다.

<br>

```vue
<div v-for="item in items" :key="item.id">
  <!-- 내용 -->
</div>
```

<br>

* 기본 리스트 렌더링 동작을 통해 성능 향샹을 꾀하는 경우가 아니라면, 
  * `v-for`는 key 속성과 함께 사용하는 것을 권장한다.

<br><br>

### 컴포넌트에 v-for 사용

* 컴포넌트에는 자체적으로 구분된 범위가 있기 때문에 데이터를 자동으로 전달하지 않는다. 
  * 반복된 데이터를 컴포넌트에 전달하려면 props를 사용해야 한다.

<br/>

```vue
<MyComponent
  v-for="(item, index) in items"
  :item="item"
  :index="index"
  :key="item.id"
/>
```

<br><br><br>

## Form 입력 바인딩, input (`v-model`)

<br>

### 텍스트
```vue
<p>메세지: {{ message }}</p>
<input v-model="message" placeholder="메세지 입력하기" />
```

<br>

### 여러 줄 텍스트

```vue
<span>여러 줄 메세지:</span>
<p style="white-space: pre-line;">{{ message }}</p>
<textarea v-model="message" placeholder="여러 줄을 추가해보세요"></textarea>
```

<br>

### 체크박스

```vue
<input type="checkbox" id="checkbox" v-model="checked" />
<label for="checkbox">{{ checked }}</label>
```

<br>

### 라디오

```vue
<div>선택한 것: {{ picked }}</div>

<input type="radio" id="one" value="하나" v-model="picked" />
<label for="one">하나</label>

<input type="radio" id="two" value="둘" v-model="picked" />
<label for="two">둘</label>
```

<br>

### 셀렉트

```vue
<div>선택됨: {{ selected }}</div>

<select v-model="selected">
  <option disabled value="">다음 중 하나를 선택하세요</option>
  <option>가</option>
  <option>나</option>
  <option>다</option>
</select>
```

<br><br><br>

## import / export

1. 어떤 data를 갖고 있는 .js 파일이 있다고 가정해보자.
2. export default를 사용하면 해당 변수가 export 가능해진다.
3. 다른 .js or App.vue 파일을 보면 import 해서 사용할 수 있다.


* 여러개를 export 할 수 도 있다.
* {}를 사용해서 import 해서 쓰면된다.

<br>

```vue
var apple = 10;

export default apple
```

```vue
import apple from './assets/post.js;

apple...;
```

<br>

```vue
export {apple, apple2}
```

```vue
import {apple, apple2} from './assets/post.js';

apple2...;
```

<br><br><br>

## component

* 컴포넌트를 사용하면 UI를 재사용 가능한 일부분으로 분할하고, 각 부분을 개별적으로 다룰 수 있다.

<br><br>

### 1. `컴포넌트 정의`

* 빌드 방식을 사용할 때 일반적으로 싱글 파일 컴포넌트(줄여서 SFC)라고 하는 <br> `.vue 확장자`를 사용하는 전용 파일에 각 Vue 컴포넌트를 정의

<br>

ButtonCounter.vue
```vue
<script>
export default {
  data() {
    return {
      count: 0
    }
  }
}
</script>

<template>
  <button @click="count++">당신은 {{ count }} 번 클릭했습니다.</button>
</template>
```

<br><br>

### 2. `컴포넌트 사용`

* 자식 컴포넌트를 사용하려면 부모 컴포넌트에서 import 해야 한다.

<br>

```vue
<script>
import ButtonCounter from './ButtonCounter.vue'

export default {
  components: {
    ButtonCounter
  }
}
</script>

<template>
  <h1>아래에 자식 컴포넌트가 있습니다.</h1>
  <ButtonCounter/>
</template>
```

<br><br><br>

## props

* 부모 컴포넌트에서 자식 컴포넌트로 데이터를 전달하는 방법

<br>

자식 컴포넌트
```vue
<!-- BlogPost.vue -->
<script>
export default {
  props: ['title']
}
</script>

<template>
  <h4>{{ title }}</h4>
</template>
```

<br>

부모 컴포넌트
```vue
<BlogPost title="Vue와 함께한 나의 여행" />
<BlogPost title="Vue로 블로깅하기" />
<BlogPost title="Vue가 재미있는 이유" />
```

<br><br><br>

## custom event

<br><br><br>

## watcher

