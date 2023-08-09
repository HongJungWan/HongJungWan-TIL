## 💡 JUnit

#### 자바 개발자가 가장 많이 사용하는 테스팅 프레임워크

* Java 8 이상이 필요하다.
* 대체제로 TestNg, Spock등 많지만 JUnit만 알아도 문제없다.

<br/>

### JUnit 4와 JUnit 5의 차이점

|JUnit 4| JUnit 5                                   |
|---|-------------------------------------------|
|@Before<br/>@BeforeClass<br/>@After| @BeforeEach<br/>@BeforeAll<br/>@AfterEach |
|Public void ... <br/> 접근제어자 public 기본으로 사용| void ... <br/> 접근제어자 Default 사용           |

<br/><br/>

<img src="https://velog.velcdn.com/images/daydream/post/c87c2afd-24cb-4894-a875-a3d704a695d7/image.png" width="450">

**Platform** : 테스트를 실행해 주는 런처를 제공해 준다. `TestEngine API` 제공 <br/>
**Jupiter** : `TestEngine API` 구현체로 `JUnit 5`를 제공한다. <br/>
**Vintage** : `JUnit4와 3`을 지원하는 `TestEngine` 구현체이다. <br/>

<br/><br/>

## ✅ JUnit 5

### JUnit5가 제공하는 기본 어노테이션

```java
class StudyTest {

    @Test
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }

    @Test
    void create1() {
        System.out.println("create1");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }
    
}
```

### @Test
* 해당 메서드를 테스트 대상으로 지정한다.

### @BeforeAll
* 모든 테스트 시작 전에 수행되는 로직에 붙는 어노테이션으로 `static`을 붙여줘야 하며,
  접근 제어자는 `default` 이상이어야 한다.

### @AfterAll
* 모든 테스트 종료 후에 수행되는 로직에 붙는 어노테이션으로 `static`을 붙여줘야 하며,
  접근 제어자는 `default` 이상이어야 한다.

### @BeforeEach
* 모든 `@Test` 어노테이션이 붙은 테스트 대상 메서드 수행 전마다 수행된다.

### @AfterEach
* 모든 `@Test` 어노테이션이 붙은 테스트 대상 메서드 수행 종료 시마다 수행된다.

### @Disabled
* 해당 어노테이션이 붙은 메서드는 테스트 제외 대상으로 테스트를 수행하지 않는다.

<br/><br/>

## ✅ JUnit 5 테스트 이름 표시

### @DisplayName

* 테스트 이름을 보기 쉽게 표현하는 어노테이션

    * 우선순위는`@DisplayNameGeneration`보다 높다.

    * 이모지같은 이모티콘도 지원을 한다.

    * 해당 애노테이션 역시 클래스와 메서드 모두 붙여줄 수 있다.

<br/>

```java
@DisplayName("테스트 클래스")
public class TestClass {
	...
	@DisplayName("회원 생성 테스트")
	@Test
	void create_account_test(){
		...
	}
}
```

<br/><br/>

## ✅ JUnit 5 Assertion

#### 💡 실제 테스트에서 검증하고자 하는 내용을 확인

<br/>

구현한 기능이 예측한 대로 나오는지 확인하기 위해서는 이를 검증해 줄 기능이 필요하다. <br/>
`org.junit.jupiter.api.Assertions`에서는 검증을 위한 다양한 메서드를 제공해 준다.

<br/><br/>

### assertEquals(expected, actual)
* 기대한 값(expected)이 실제 값(actual)과 같은지 확인하는 메서드

```java
@DisplayName("스터디 만들기 ")
@Test
void create_new_study() {
    Study study = new Study();
    assertEquals(StudyStatus.DRAFT, study.getStatus(),()-> "스터디를 처음 만들면 DRAFT 상태다. ");
}
````

<br/>

* `assertEquals`에서는 세 번째 파라미터로 메시지를 줄 수 있다.

    * 해당 테스트가 통과하지 못했을 경우 출력된다.

    * 이때 `Supplier<_String_>` 타입의 인스턴스를 람다로 제공할 수도 있는데,
      복잡한 메시지를 생성해야 하는 경우 람다를 사용하면 **실패한 경우에만
      해당 메시지를 만들 수 있어 효율적**이다.

<br/><br/>

### assertNotNull(actual)

* 결과 값(actual)이 null 인지 아닌지 확인하는 메서드

```java
@DisplayName("스터디 만들기 ")
@Test
void create_new_study() {
    Study study = new Study();
    assertNotNull(study);   
}
```

<br/><br/>

### assertTrue(boolean)

* 다음 조건이 참인지 확인하는 메서드

```java
@DisplayName("스터디 만들기 ")
@Test
void create_new_study() {
    Study study = new Study();
    assertTrue(study.getStatus().equals(StudyStatus.DRAFT));
}
```

<br/><br/>

### assertAll(executable...)

* 모든 구문 확인 메서드

```java
@DisplayName("스터디 만들기 ")
@Test
void create_new_study() {
    Study study = new Study();
    assertNotNull(study);

    assertAll(
            ()->assertEquals(StudyStatus.DRAFT, study.getStatus(),()-> "스터디를 처음 만들면 DRAFT 상태다. "),
            ()->assertTrue(study.getLimit() > 0, ()-> "스터디 최대 참석 가능 인원은 0명 이상이어야 한다. ")
    );
}
```

<br/><br/>

### assertThrows(expectedType, executable)

* 예외 발생 확인 메서드

    * `예외 검증 후 해당 예외를 반환`해주기에 예외 메시지 검증이 가능하다.

```java
@DisplayName("스터디 만들기 ")
@Test
void create_new_study() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-1));
    assertEquals(exception.getMessage(), "limit 은 0보다 커야한다.");
}
```

<br/><br/>

### assertTimeout(duration, executable)

* 특정 시간 내에 실행 완료되는지 확인하는 메서드

```java
@DisplayName("스터디 만들기 ")
@Test
void create_new_study() {
    assertTimeout(Duration.ofMillis(100), () -> {
        new Study(10);
        Thread.sleep(300);
    });
}
```

<br/>

💡 `sleep 메서드`를 통해 300ms 대기하기에 해당 테스트는 실패한다.

<br/>

만약 300ms를 다 기다리지 않고 해당 대기시간(100ms)가 지나 테스트가 실패하자마자 테스트가 중단되도록 하려면 `assertTimeoutPreemtively()` 메서드를 사용하면 된다.

하지만, 스프링 트랜잭션이 제대로 동작을 안 할 수 있어서 **롤백 기반인 스프링 테스트에서 롤백이 안된다던가 하는 부작용**이 있기에 사용을 권장하지는 않는다.

ThreadLocal 같은 스레드와 전혀 관련이 없는 코드를 실행할 때는 사용해도 괜찮다.

<br/><br/>

## ✅ 조건에 따라 테스트 실행

* JUnit에서는 특정한 조건에 따라 테스트를 실행할 수도 있다.

    * ex) 특정한 OS, 환경 변수, 시스템 변수에 따라 테스트 실행 결정

<br/>

### org.junit.jupiter.api.Assumptions.*

<br/>

* `assumeTrue(condition)`

    * 해당 조건이 통과를 해야 아래 로직들 수행

<br/>

* `assumingThat(condition, test)`

    * 조건(condition)이 통과를 하면 두 번째 파라미터로 전달한 로직 수행

<br/>

```java
@DisplayName("스터디 만들기 ")
@Test
void create_new_study() {
    String test_evn = System.getenv("TEST_ENV");
    System.out.println(test_evn);

    assumeTrue("LOCAL".equalsIgnoreCase(test_evn));
    assumingThat("LOCAL".equalsIgnoreCase(test_evn), () -> {
        System.out.println("local");
        Study actual = new Study(100);
        assertTrue(actual.getLimit() >= 0);
    });

    assumingThat("hong".equalsIgnoreCase(test_evn), () -> {
        System.out.println("hong");
        Study actual = new Study(10);
        assertTrue(actual.getLimit() >= 0);
    });
}
```

<br/><br/>

### 어노테이션으로 조건 설정

* **@EnabledOnOs**
    * 특정 OS일 때만 테스트가 동작하게 할 수도 있다.
    * (`@EnabledOnOs(OS.MAC)`)

<br/>

* **@EnabledOnJre**
    * 특정 JRE버전일 때만 테스트가 동작하게 할 수도 있다.
    * (`@EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})`)

<br/>

* **@EnabledIfEnvironmentVariable**
    * 위에서 사용한 assumeXX 메서드는 해당 어노테이션을 통해 환경 변수 조건 설정이 가능하다.
    * (`@EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "local")`)

<br/><br/>

## ✅ 태깅과 필터링

* 여러 개의 작성한 테스트를 그룹화할 수 있다. (ex: 모듈 별, 단위/통합 구분, 기타 조건)

<br/>

### @Tag

* 하나에 테스트 메서드에 여러 태그를 사용할 수도 있다.

```java
class StudyTest {

    @DisplayName("스터디 만들기 fast")
    @Test
    @Tag("fast")
    void create_new_study() {
        Study study = new Study();
        assertEquals(StudyStatus.DRAFT, study.getStatus(), ()-> "스터디를 처음 만들면 DRAFT 상태다. ");
    }
    
    
    @DisplayName("스터디 만들기 slow")
    @Test
    @Tag("slow")
    void create_new_study_again() {
        Study study = new Study();
        assertEquals(StudyStatus.DRAFT, study.getStatus(), ()-> "스터디를 처음 만들면 DRAFT 상태다. ");
    }
}
```

스터디 객체를 생성하는 테스트 두 개를 각각 slow, fast 태그로 구분 했지만, <br/>
실행 시 둘 다 수행되는 것을 볼수 있다. 이는 필터링을 해주지 않았기 때문이다.

<br/>

### 필터링

* IntelliJ에서 특정 태그로 테스트 필터링

    * `Run/Debug Configurations`에서 Tags 항목으로 선택 후 실행할 테스트의 태그명을 적어주면 된다.

<br/>

* Maven - pom.xml 작성을 통한 필터링

```
<profiles>
    <profile>
        <id>default</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <build>
            <plugins>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <groups>fast</groups>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

* 만약 slow도 테스트를 하고 싶다면 or 문법인 `|` 연산자를 이용해서 작성을 해주면 된다.
  `<groups>fast | slow</groups>`

<br/><br/>

## ✅ 커스텀 태그



JUnit에서 제공하는 테스트용 어노테이션은 메타 어노테이션으로 사용할 수 있다.
자주 사용하는 어노테이션의 조합을 하나의 `ComposedAnnotation`으로 만들어서 사용할 수 있다.

Fast라는 임의의 어노테이션을 만들어서 메타 어노테이션을 정의해 보자.

<br/>

### 코드

#### FastTest

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("fast")
public @interface FastTest { }
```

<br/>

#### SlowTest

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("slow")
public @interface SlowTest { }
```

<br/>

#### StudyTest

```java
class StudyTest {

    @DisplayName("스터디 만들기 fast")
    @FastTest
    void create_new_study() {
        Study study = new Study();
        assertEquals(StudyStatus.DRAFT, study.getStatus(),()-> "스터디를 처음 만들면 DRAFT 상태다. ");
    }
    
   
    @DisplayName("스터디 만들기 slow")
    @SlowTest
    void create_new_study_again() {
        Study study = new Study();
        assertEquals(StudyStatus.DRAFT, study.getStatus(),()-> "스터디를 처음 만들면 DRAFT 상태다. ");
    }
    
}
```

<br/>

* 기존에 작성돼있던 @Test, @Tag("fast or slow") 어노테이션이 사라지고 임의로 작성한 어노테이션(`@FastTest`, `@SlowTest`)

* 매번 Tag를 직접 입력하면 오타가 날 확률이 있는데 메타 어노테이션으로 이를 해결한다.

<br/><br/>

## 테스트 반복하기

인자가 랜덤 값이거나, 테스트 발생 시점에 따라 달라지는 값 때문에, 테스트 내용이 반복돼야 하는 경우에는 어떻게 해야 할까 ❓

JUnit에서는 이러한 테스트 반복을 위한 어노테이션을 제공한다.

<br/>

### @RepeatedTest

* 속성을 통해 반복 횟수와 반복 테스트 이름을 설정할 수 있다.

```java
@DisplayName("반복 테스트")
@RepeatedTest(value = 10, name = "{displayName} {currentRepetition}/{totalRepetitions}")
void repeatTest(RepetitionInfo repetitionInfo) {
    System.out.println("repetitionInfo.getCurrentRepetition() = " + repetitionInfo.getCurrentRepetition());
    System.out.println("repetitionInfo.getTotalRepetitions() = " + repetitionInfo.getTotalRepetitions());
}
```

<br/>

* `RepetitionInfo 타입`의 인자를 받을 수 있다. 해당 인자에는 반복에 대한 정보를 얻을 수 있다.
* 어노테이션의 name 속성에 테스트 이름, 반복 횟수 등을 설정할 수 있다.

    * `{displayName}` : @DisplayName으로 설정한 테스트 이름
    * `{currentRepetition}` : 현재 반복 횟수 값
    * `{totalRepetition}` : 전체 반복 횟수

<br/><br/>

### @ParameterizedTest

* 테스트에 여러 다른 매개변수를 대입해가며 반복 실행한다.

```java
@ParameterizedTest(name = "{index} {displayName} 과목:{0}")
@ValueSource(strings = {"수학", "영어", "국어", "체육"})
void create_new_study(String input) {
    System.out.println("input = " + input);
}
```

<br/>

* `@ValueSource`

    * 속성에 선언해 준 파라미터를 인자 값으로 전달해 주는데,
      string 타입뿐 아니라 각 원시 타입을 모두 지원한다.

<br/>

* `@ParameterizedTest(name = "{index} {displayName} 과목:{0}")`

    * {displayName} : @DisplayName 어노테이션으로 설정한 테스트 이름
    * {index} : 현재 반복된 횟수의 인덱스
    * {arguments} : 전달되는 인덱스 전체
    * {0}, {1}... : 파라미터를 인덱스로 조회할 수 있다.

<br/><br/>

### 인자 값들의 소스

위에서 `@ValueSource`에 대해 알아봤는데 인자 값들을 제공해 주는 어노테이션은 더 있다.

<br/>

#### @NullSource, @EmptySource, @NullAndEmptySource

* Null과 공백문자를 제공해 주는 어노테이션으로 각각 제공해 줄 수도 있고,
  `@NullAndEmptySource`를 통해 둘 다 제공해 줄 수도 있다.

<br/>

#### @EnumSource

* Enum의 값을 제공해 주는 어노테이션

```java
@DisplayName("스터디 만들기")
@ParameterizedTest(name = "{index} {displayName} 과목:{arguments}")
@EnumSource(value = StudyStatus.class)
void create_new_study_again_again(StudyStatus status) {
    System.out.println("status = " + status);
}
//status = DRAFT
//status = COMPLETED
```

<br/>

#### @MethodSource

* 특정 메서드를 만들어서 인자 값을 전달받는다.

```java
@DisplayName("스터디 만들기")
@ParameterizedTest(name = "{index} {displayName} 과목:{arguments}")
@MethodSource("provideNamesAndLimit")
void create_new_study_again_again(String name, int input) {
    System.out.println("name:" + name + ", limit:" + input);
}

private static Stream<Arguments> provideNamesAndLimit() {
    return Stream.of(
            Arguments.of("수학", 10),
            Arguments.of("영어", 20),
            Arguments.of("국어", 30)
    );
}
```

<br/>

#### @CvsSource

* delimiter를 통해 속성의 value로 세팅한 값을 구분해서 인자로 전달한다.

```java
@DisplayName("스터디 만들기")
@ParameterizedTest(name = "{index} {displayName} 과목:{arguments}")
@CsvSource(value = {"수학:1", "영어:2", "국어:3", "체육:4"}, delimiter = ':')
void create_new_study_again(String input, int limit) {
    System.out.println("input = " + input);
}
```

<br/>

#### @CvsFileSource

* 파일을 읽어와서 인자로 제공하는 어노테이션

<br/>

#### @ArgumentSource

* ArugmentProvider의 구현체 클래스로부터 인자 값을 전달하는 어노테이션

```java
@DisplayName("스터디 만들기")
@ParameterizedTest(name = "{index} {displayName} 과목:{arguments}")
@ArgumentsSource(value = TestArgumentProvider.class)
void create_new_study_again(String input) {
    System.out.println("input = " + input);
}

static class TestArgumentProvider implements ArgumentsProvider{

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of("수학", 10),
                Arguments.of("영어", 20),
                Arguments.of("국어", 30)
        );
    }
}
```

<br/><br/>

## 인자 값 타입 변환

어노테이션의 속성으로 String을 값을 전달하더라도 실제로는 다른 타입으로 쓰고 싶을 수 있다. <br/>
JUnit에서는 암묵적인 변환 방법과 명시적인 변환 방법을 모두 제공한다.

<br/>

### 암묵적인 타입 변환

개발자가 따로 명시하지 않아도 코드 컨벤션에 따라 자동으로 변환이 될 수 있는데, <br/>
예를 들어 "true"라는 문자열을 제공해 줄 때 매개변수를 `boolean value`로 받는다면 boolean 타입의 true 값으로 대입된다.

그 밖에도 많은 데이터들에 대해서 암묵적으로 타입 변환을 제공하는데 다음 링크를 참고하면 된다.

[JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-implicit)

<br/><br/>

### 명시적인 타입 변환

직접 만든 객체(커스텀 한 타입)로 변환을 하려면 어떻게 해야 할까 ❓

<br/>

#### SimpleArgumentConverter을 구현해서 적용

* 보통 하나의 인자 값을 변환하고자 할 때 사용하며 static inner class 또는 다른 public class의 static 클래스

* 매개변수를 전달받는 메서드에서는 내가 변환 받고자 하는 매개변수에 `@ConvertWith`어노테이션을 붙여서 사용

```java
@DisplayName("스터디 만들기")
@ParameterizedTest(name = "{index} {displayName} 과목:{arguments}")
@ValueSource(ints = {1,2,3,4,5})
void create_new_study_again(@ConvertWith(StudyConverter.class)Study study) {
    System.out.println("study = " + study.toString());
}

static class StudyConverter extends SimpleArgumentConverter{

    @Override
    protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
        assertEquals(Study.class, aClass, "Can Only convert to Study");
        return new Study(Integer.parseInt(o.toString()));
    }
}
```

<br/><br/>

#### ArgumentsAccessor을 매개변수로 받아 사용

* 하나 이상의 인자 값을 받고 싶을 때 해당 매개변수를 받아 사용할 수 있다.

* 매개변수로 받은 accessor를 이용해 인덱스를 활용한 getter로 값을 꺼내 사용할 수 있다.

```java
@DisplayName("스터디 만들기")
@ParameterizedTest(name = "{index} {displayName} 과목:{arguments}")
@CsvSource({"10,'수학'", "20,스프링"})
void create_new_study_again_with_csvsource(ArgumentsAccessor accessor) {
    System.out.println(accessor.getInteger(0));
    System.out.println(accessor.getString(1));

}
```

<br/><br/>

#### ArgumentsAggregator를 사용해 커스텀 타입 변환

* accessor를 사용하지 않고 해당 인터페이스를 구현해 객체를 사전에 생성해서 전달할 수도 있다.

* 코드 내부적으로는 `accessor`를 이용해 객체를 생성을 `Aggregator`에서 해주는 것뿐이다.

* 매개변수로 받을 부분에서 `@AggregateWith` 어노테이션을 사용해 해당 `Aggregator`를 사용한다.

```java
@DisplayName("스터디 만들기")
@ParameterizedTest(name = "{index} {displayName} 과목:{arguments}")
@CsvSource({"10,'수학'", "20,스프링"})
void create_new_study_again_with_aggregator(@AggregateWith(StudyAggregator.class) Study study) {
    System.out.println("study = " + study);
}

static class StudyAggregator implements ArgumentsAggregator {
    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        return new Study(accessor.getString(1), accessor.getInteger(0));
    }

}
```

<br/><br/>

## 테스트 인스턴스

JUnit은 테스트 메서드 별로 테스트 인스턴스를 새로 만들며 이것이 기본 전략이다.

이처럼 각각의 메서드를 독립적으로 실행할 경우 다른 메서드로부터 영향을 받지 않기에 예상치 못한 다른 테스트로부터의 영향을 받을 일이 줄어든다. <br/>
하지만, JUnit5에서는 상황에 따라 이러한 전략을 바꿔줄 수도 있다.

<br/>

### @TestInstance(Lifecycle.PER_CLASS)

* 테스트를 클래스당 인스턴스를 하나만 만들어서 사용한다.

* 경우에 따라 테스트 간에 공유하는 모든 상태를 `@BeforeEach` 또는 `@AfterEach`에서 초기화할 필요가 있다.

* `@BeforeAll`과 `@AfterAll`을 인스턴스 메서드 또는 인터페이스에 정의한 default 메서드로 정의할 수 있다.

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudyTest {
    
         @BeforeAll
	  void beforeAll() {
	      System.out.println("beforeAll");
	  }
	  
	  @AfterAll
	  void afterAll() {
	      System.out.println("afterAll");
	  }
		...
}
```

<br/>

`@BeforeAll`, `@AfterAll` 어노테이션은 테스트 인스턴스가 `PER_CLASS`로 클래스당 하나의 인스턴스만 생성하도록 되면 `static` 키워드를 제거하여 인스턴스 메서드로 선언할 수 있다.

<br/><br/>

## 테스트 순서



테스트 메서드는 특정한 순서에 의해 실행이 되지만 그 순서가 어떻게 정해지는지에 대해서는 의도적으로 밝히고 있지 않는다. 그 이유는 하나의 단위(테스트)는 독립적으로 실행되며 다른 메서드에 영향을 주면 안 된다.

<br/>

하지만, 특정 순서대로 테스트를 실행해야 하는 경우(Functional Test, Acceptance Test...)가 있다면 어노테이션을 통해 이 테스트 메서드의 실행 순서를 제어할 수 있다.

<br/>

### @MethodOrderer

* `기본 구현체`
    * MethodOrderer.OrderAnnotation.class
    * MethodOrderer.Alphanumeric.class
    * MethodOrderer.Random.class

<br/>

✅ `@TestInstance(Lifecycle.PER_CLASS)`와 함께 사용을 하지 않아도 된다.

테스트 순서 어노테이션들은 작동을 한다. 다만, 해당 애노테이션으로 클래스 단위로 테스트가 수행된다면, 상태가 공유되는 stateful 한 테스트를 클래스 단위로 실행할 때 유용하다.

<br/><br/>

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {
    
    @Order(0)
    @DisplayName("반복테스트")
    @RepeatedTest(value = 10, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println(this);
        System.out.println("repetitionInfo.getCurrentRepetition() = " + repetitionInfo.getCurrentRepetition());
        System.out.println("repetitionInfo.getTotalRepetitions() = " + repetitionInfo.getTotalRepetitions());
    }

    @Order(1)
    @DisplayName("스터디 만들기 1")
    @ParameterizedTest(name = "{index} {displayName} 과목:{arguments}")
    @ValueSource(strings = {"수학", "영어", "국어", "체육"})
    void create_new_study(String input) {
        System.out.println(this);
        System.out.println("input = " + input);
    }

    @Order(2)
    @DisplayName("스터디 만들기 2")
    @ParameterizedTest(name = "{index} {displayName} 과목:{arguments}")
    @ValueSource(ints = {1,2,3,4,5})
    void create_new_study_again(@ConvertWith(StudyConverter.class)Study study) {
        System.out.println(this);
        System.out.println("study = " + study.toString());
    }
}
```

<br/><br/>

* `OrderAnnotation` 기준으로 실행이 된다.
* 각각의 테스트 메서드들이 `@Order`가 있는데 안에 작성한 `value` 값에 따라 순서대로 수행된다
* 만약 `@Order` 내부의 값이 같을 경우 자체적인 순서에 따라 수행된다.

<br/><br/>

## junit-platform.properties

JUnit은 설정 파일(properties)을 이용해 클래스 패스 루트(`src/test/resources/`)에 넣어두면 적용된다.

```java
// 테스트 인스턴스 라이프 사이클 설정
junit.jupiter.testinstance.lifecycle-default=per_class

// 확장팩 자동 감지 기능
junit.jupiter.extensions.autodetection.enabled=true

// @Disabled 무시하고 실행하기
junit.jupiter.conditions.deactivate=org.junit.*DisabledCondition

// 테스트 이름 표기 전략 설정
junit.jupiter.displayname.generator.default=org.junit.jupiter.api.DisplayNameGenerator$ReplaceUnderscores
```

<br/>

테스트 클래스가 수십, 수백 개가 되는 경우 외부 설정 파일을 통해 통합해서 관리해 줄 수 있다. 
