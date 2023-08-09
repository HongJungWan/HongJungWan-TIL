# ES6(ECMAScript 6) 첫 발자국 & `Vue.js` 한 숟가락

<br>

`ECMAScript 6 (ES6)`, 또는 `ECMAScript 2015`라는 이름으로도 알려져 있으며, `ECMAScript`의 여섯 번째 주요 버전. 

<br>

### 꼬꼬무

* `ECMAScript`는 `JavaScript`라는 프로그래밍 언어의 표준 사양. 


* `JavaScript`는 웹 브라우저에서 작동하는 가장 널리 사용되는 스크립팅 언어이며, 이를 표준화하기 위해 `ECMAScript`가 만들어졌다.


* `ECMAScript`는 `Ecma International`이라는 비영리 표준화 기구에서 관리하며, 이름은 이 기구의 이름을 따왔다.

<br><br>

## `Vue.js`에서 ES6 기능들을 어떻게 활용할까 ⁉

<br>

## 모듈 (Modules)

* `Vue.js`에서는 ES6 모듈 시스템을 사용하여 코드를 재사용 가능한 덩어리로 나눈다. 


* 예를 들어, 아래와 같이 컴포넌트를 import/export 할 수 있습니다.

<br>

```vue
// MyComponent.vue
export default {
// component details
}

// App.vue
import MyComponent from './MyComponent.vue'

export default {
    components: {
        MyComponent
    }
}
```

<br><br>

## 화살표 함수 (Arrow Functions)

* `Vue.js`에서 화살표 함수는 비동기 작업이나 콜백 함수에서 유용하게 사용. 


* 화살표 함수는 this를 자신을 생성한 컨텍스트에 바인딩 하므로, `Vue.js` 컴포넌트의 메서드나 생명주기 훅에서는 사용하지 않아야 한다. 

<br>

```vue
methods: {
    fetchData() {
        axios.get('/api/data')
        .then(response => {
            this.data = response.data
        })
    }
}
```

<br><br>

## 템플릿 리터럴 (Template Literals)

* 템플릿 리터럴은 문자열 내에서 변수를 간단히 삽입할 수 있게 해준다.

<br>

```vue
computed: {
    welcomeMessage() {
        return `Hello, ${this.username}!`
    }
}
```

<br><br>

## let과 const

* let과 const는 `Vue.js` 코드 내에서 변수의 범위와 변경 가능성을 관리. 


* let은 재할당이 필요한 변수, const는 재할당이 필요하지 않은 상수에 사용.

<br>

```vue
const MAX_COUNT = 10; // 상수
let count = 0; // 재할당이 필요한 변수
```

<br><br>

## 배열과 객체의 해체 할당 (Destructuring Assignment)

* `Vue.js`에서는 props, data 등의 값을 해체 할당을 통해 간결하게 가져올 수 있다.

<br>

```vue
props: ['title', 'author'],

created() {
    const { title, author } = this.$props;
    console.log(title, author); // props의 title, author를 가져옵니다.
}
```

<br><br>

## Promises

* `Vue.js`에서는 비동기 작업을 처리하기 위해 Promise를 사용한다.

<br>

```vue
actions: {
    fetchData({ commit }) {
        return axios.get('/api/data') // Promise 반환
            .then(response => {
                commit('SET_DATA', response.data)
        })
    }
}
```

<br><br>

## Promises - 상세

* `ES6 (ECMAScript 2015)`에서 도입된 `Promise`는 `JavaScript`에서 비동기 작업을 더 효과적으로 다루는 데 사용된다. 

<br>

* Promise 객체는 다음 중 하나의 상태를 가진다.

<br>

* `Pending (대기)`: 초기 상태로, 이행 또는 거부되지 않은 상태.
* `Fulfilled (이행)`: 연산이 성공적으로 완료된 상태.
* `Rejected (거부)`: 연산이 실패하고 에러가 발생한 상태.

<br>

`Promise`는 생성될 때 `executor`라는 함수를 인자로 받는다. 

`executor`는 `resolve`와 `reject`라는 두 개의 콜백 함수를 인자로 받으며, 비동기 작업을 수행하고 그 결과에 따라 이들 중 하나를 호출한다.

<br>

```vue
let promise = new Promise((resolve, reject) => {
    // 비동기 작업을 수행.

    if (/* 비동기 작업이 성공적으로 완료됐다면 */) {
        resolve("작업 결과");
    } else {
        reject(Error("실패 이유"));
    }
});
```

<br><br>

`Promise`가 이행되면 `then 메서드`를 사용해 이행 시, 실행할 콜백 함수를 등록할 수 있다.

```vue
promise.then((result) => {
    console.log(result); // "작업 결과"
}, (error) => {
    console.log(error);
});
```

<br><br>

또한, `Promise`가 거부되면 `catch 메서드`를 사용해 거부 시 실행할 콜백 함수를 등록할 수 있다.

```vue
promise.catch((error) => {
    console.log(error);
});
```

<br><br>

`Promise.all 메서드`를 사용하면 여러 개의 `Promise`를 병렬로 실행하고, 모든 `Promise`가 이행됐을 때의 결과를 받아올 수 있다. 


`Promise.race 메서드`를 사용하면 여러 개의 `Promise` 중에서 가장 먼저 이행되거나 거부된 `Promise`의 결과를 받아올 수 있다.