<br/><br/>

#### 💡 `Mock` : 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체

#### 💡 `Mockito` : Mock 객체를 쉽게 만들고 검증할 수 있는 방법을 제공해 주는 프레임워크

<br/>

협업 혹은 외부 API 사용 시, 해당 도메인 API가 아직 구현되지 않았거나 외부 API 도입이 늦춰지는 경우가 있다. 이럴 때 적절한 값을 반환하는 인터페이스를 만든 뒤 프록시를 이용해 해결할 수 있다.

<br/>

그럼, 테스트 케이스를 작성할 때는 어떻게 해야 할까 ❓

이 경우 `Mock 프레임워크`를 이용해 요청을 `Mocking`해 실제 응답 결과와 비슷한 결과를 작성해서 응답하도록 할 수 있다.

또한 개발 중 DB가 아직 구축되어 있지 않을 시, `Mockito`와 같은 `Mock 프레임워크`를 이용하면 안전하고 독립적인 테스트가 가능해진다.

<br/><br/>

### ✅ 단위 테스트를 바라보는 시각

개발자마다 테스트 고립성 수준에 대해 생각이 다르다.
모든 의존성을 끊어야 하기 때문에 모든 의존성에 대해서 `mocking`을 해야 한다는 의견도 있고,
단위를 생각할 때 단위를 어떠한 `행동의 단위`로 생각하는 경우도 있다.

행위를 단위로 보기 때문에 행위에 연관된 객체들은 같이 테스트가 진행돼도 괜찮다고 생각한다.
정답은 없기에 개발을 시작할 때 단위 테스트에서 단위의 범위와 Mock을 어디까지 해야 할지에 대해 논의하고 진행하자.

<br/><br/>

## Maven 설정

스프링 부트 환경에서는 Mockito는 기본으로 `spring-boot-starter-test` 의존성에 같이 포함되어 있다. 만약 `spring-boot-starter-test`가 없다면 `mockito-junit-jupiter`와 `mockito-core` 라이브러리를 추가해 주면 된다.

```java
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>3.1.0</version>
    <scope>test</scope>
</dependency>


<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>3.1.0</version>
    <scope>test</scope>
</dependency>
```

<br/>

[Mockito 레퍼런스](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

<br/><br/>

## Mock 객체

`Mock 객체`를 만드는 방법은 메서드를 통한 방법과 어노테이션을 이용하는 방법이 있다.


### mock() 메서드 이용

```java
MemberService memberService = Mockito.mock(MemberService.class);
StudyRepository studyRepository = mock(StudyRepository.class);
```

* `Mockito 클래스`는 static 하게 선언해서 생략 가능

<br/><br/>

### @Mock 어노테이션 이용

* `JUnit 5` 확장 모델로 `MockitoExtension`을 사용하도록 한다.

* `MockitoExtension`을 확장 모델로 사용하지 않으면 `@Mock` 어노테이션 사용이 불가능하다.

```java
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
		...
}
```

<br/>

* 필요한 곳에 `@Mock` 어노테이션 사용

```java
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
		@Mock MemberService memberService;
		...
    @Test
    void createStudyService(memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }
}
```

* 원하는 곳에 `@Mock` 어노테이션을 사용하여 `Mock 객체`를 만들어줄 수 있다.
* 매개변수로 받는 곳에 `@Mock` 어노테이션을 붙이면 `Mock 객체`가 생성되어 주입된다.

<br/><br/>

## Mock 객체 Stubbing

`Mock 객체`를 생성만 한다면 빈 껍데기이기 때문에 제대로 동작을 하지 않는다.
모든 `Mock 객체`의 기본적인 행동은 다음과 같다.

* Null을 반환한다.
* `Primitive 타입`은 기본 Primitive 값을 반환한다.
* 컬렉션은 비어있는 컬렉션을 반환한다.
* void 메서드는 예외를 던지지 않고 아무런 일도 발생하지 않는다.

<br/>

#### ✅ `Method Stub`

기존 코드를 흉내 내어 임시로 대치하는 역할을 수행함으로써 **아직 구현되지 않았거나**,
**독립적인 테스트**를 수행해야 하는 경우 이점을 가질 수 있다.

<br/><br/>

#### Mock 객체 Stubbing 예제

```java
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Test
    void createStudyService(@Mock MemberService memberService,
                            @Mock StudyRepository studyRepository) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("catsbi@email.com");

        when(memberService.findById(any())).thenReturn(Optional.of(member));
        doThrow(new IllegalArgumentException()).when(memberService).validate(2L);

        Optional<Member> optional = memberService.findById(1L);
        assertNotNull(optional.get());
        assertEquals(optional.get(), member);


        assertThrows(IllegalArgumentException.class, () -> memberService.validate(2L));
        
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> findById = memberService.findById(1L);
        assertEquals(findById.get(), member);
        assertThrows(RuntimeException.class, ()-> memberService.findById(1L));
        assertEquals(Optional.empty(), memberService.findById(1L));
    }

}
```

<br/>

#### <span style="color: #EF820D">when(memberService.findById(any())).thenReturn(Optional.of(member));</span>

* `memberService.findById` 메서드를 호출할 때 안에 어떤 값을 넣어도 뒤에 체이닝된 `thenResult` 메서드에 인자 값으로 전달한 `Optional.of(member)`가 반환된다.

여기서 `any()`는 `ArgumentMatchers`에서 제공하는 메서드, 만약 명시적으로 `1L`이나 `2L`같은 값을 넣으면 해당 값을 넣을 때만 선언한 결과 반환

<br/>

#### <span style="color: #EF820D">doThrow(new IllegalArgumentException()).when(memberService).validate(2L);</span>

* `memberService`에서 `validate` 메서드에 `2L`을 인자로 전달해 호출할 때 `new IllegalArgumentException` 예외가 발생한다는 것으로 주로 `void`와 같이 반환 타입이 없는 메서드에서 특정 예외를 발생시키고자 할 때 사용한다.

만약 반환 타입이 있는 메서드에서 예외를 발생시키고자 한다면 `thenThrow` 메서드를 사용하면 된다.

<br/>

#### <span style="color: #EF820D">when(memberService.findById(any()).thenReturn(...).thenThrow(...).thenReturn(...);</span>

* 동일한 메서드를 반복해서 호출할 때 각각 다르게 행동하도록 조작할 수도 있다. 위 코드에서는 첫 번째 호출할 때는 정상적으로 조회가 되고 두 번째에는 `RuntimeException` 예외 발생, 그리고 세 번째 호출 시에는 `Optional.empty()`가 반환되도록 했다.

<br/>

[Argument matchers](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#3)

* Method Stub을 할 때 메서드의 특정 인자 값에 반응하게 하는 게 아니라 좀 더 유연하게 인자 값을 설정하고 싶을 때 `Argument matchers`를 사용하면 다양한 인자 값에 대처할 수 있다.

<br/><br/>

## Mock 객체 확인

스터디 비즈니스 로직이 있는 StudyService 객체가 있고, 스터디를 저장하고 특정 회원을 해당 강의 강사로 등록하는 createNewStudy 메서드가 있다고 하자.

<br/>

`Mock 객체`의 임의의 메서드(ex: notify)가 수행되는 것을 `횟수`, `시점` 등을 확인하고 싶다면 어떻게 해야 할까 ❓

`Mockito`에서는 `verify`라는 메서드를 통해 `Mock 객체`의 메서드 호출을 확인할 수 있다.

`verify`라는 메서드를 이용해 단순히 Mock 객체의 동작 여부뿐 아니라 `순서나 시간 내에 실행되었는지`, 그리고 심지어 `특정 메서드 실행 이후 Mock이 실행되지 않았는지`도 확인을 할 수 있다.

<br/>

### StudyService.createNewStudy()

```java
public Study createNewStudy(Long memberId, Study study) {
    Optional<Member> member = memberService.findById(memberId);
    if (member.isPresent()) {
        study.setOwnerId(memberId);
    } else {
        throw new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'");
    }
    Study newstudy = repository.save(study);
    memberService.notify(newstudy);
    memberService.notify(member.get());
    return newstudy;
}
```

<br/>

### StudyServiceTest - 기본적인 Mock 객체 동작 확인 테스트

```java
@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    @DisplayName("스터디 만들기")
    @Test
    void createNewStudy_test(@Mock StudyRepository studyRepository,
                             @Mock MemberService memberService) {
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setEmail("catsbi@email.com");
        member.setId(1L);

        Study study = new Study(10, "수학");
        study.setId(2L);

        when(memberService.findById(anyLong())).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertEquals(member.getId(), study.getOwnerId());

        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(anyLong());
    }
}
```

<br/>

#### <span style="color: #EF820D">verify(memberService, times(1)).notify(study);</span>
* memberService의 notify 메서드에 study 매개변수가 전달되어 호출되는 게 1번이어야 한다는 의미

#### <span style="color: #EF820D">verify(memberService, never()).validate(anyLong());</span>
* memberService의 validate 메서드가 수행되지 않는지 확인하는 메서드

<br/>

### StudyServiceTest - Mock 객체 동작, 순서 확인 테스트

```java
@ExtendWith(MockitoExtension.class)
public class StudyServiceTestForPosting {

    @DisplayName("스터디 만들기")
    @Test
    void createNewStudy_test(@Mock StudyRepository studyRepository,
                             @Mock MemberService memberService) {
        
				...
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService, times(1)).notify(study);
        inOrder.verify(memberService, times(1)).notify(member);

        verifyNoMoreInteractions(memberService);
    }
}
```

#### <span style="color: #EF820D">InOrder inOrder = inOrder(memberService);</span>
* memberService의 동작 확인을 순서에 맞춰 하기 위해 inOrder 메서드를 사용한다.
  몇몇 동작에 대해 순서를 확인할 수 있다.

#### <span style="color: #EF820D">inOrder.verify(memberService, times(1)).notify(study);</span>
* memberService의 notify(study) 메서드가 처음 수행돼야 한다.

#### <span style="color: #EF820D">inOrder.verify(memberService, times(1)).notify(member);</span>
* memberService의 notify(member) 메서드가 그다음으로

#### <span style="color: #EF820D">verifyNoMoreInteractions(memberService);</span>
* 위에서 확인한 동작 이후로 memberService에 어떠한 동작도 있어서는 안 된다.

<br/><br/>

## Mockito BDD Style API

### BDD(Behavior Driven Development)

애플리케이션이 어떻게 "행동" 해야 하는지에 대해서 공통된 이해를 구성하는 방법.

즉, **행위 기반 테스트**라고 할 수 있다.

Mockito 프레임워크에서는 BddMockito라는 클래스를 통해 BDD 스타일의 API도 제공하기 때문에 개발자가 BDD 스타일의 테스트를 작성하고 싶다면 편하게 사용할 수 있다.

<br/>

### 행동(behavior)에 대한 스펙

* Title
* Narrative
    * As a / I want / so that
* Acceptance criteria
    * Given / When / Then

<br/>

### 어떻게 변하는가 ❓

```java
💡
기존 : when(memberService.findById(1L)).thenReturn(Optional.of(member));
변경 : given(memberService.findById(1L)).willReturn(Optional.of(member));
```

```java
💡
기존 : verify(memberService, times(1)).notify(study);
변경 : then(memberService).should(times(1)).notify(study);
```

<br/>

```java

@ExtendWith(MockitoExtension.class)
public class StudyServiceTestForPosting {
    @Mock StudyRepository studyRepository;
    @Mock MemberService memberService;
    
    @DisplayName("스터디 만들기")
    @Test
    void createNewStudy_test() {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setEmail("catsbi@email.com");
        member.setId(1L);

        Study study = new Study(10, "수학");
        study.setId(2L);

        given(memberService.findById(anyLong())).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        //when
        studyService.createNewStudy(1L, study);

        //then
        assertEquals(member.getId(), study.getOwnerId());
        then(memberService).should(times(1)).notify(study);
        then(memberService).should(times(1)).notify(member);
        then(memberService).shouldHaveNoMoreInteractions();

    }
}
```