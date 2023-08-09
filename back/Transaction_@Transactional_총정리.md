## Transaction과 @Transactional 총 정리

Transaction의 기본적인 개념부터 알아야 @Transactional을 더 잘 이해할 수 있으므로, <br/>
데이터 베이스상에서의 Transaction의 개념을 먼저 알아보자.

<br/><br/>

## Transaction의 정의

하나의 작업을 수행하기 위해 필요한 데이터베이스의 연산들을 모아놓은 것, <br/>
데이터베이스에서 논리적인 작업의 단위이며 장애가 발생했을 때 데이터를 복구하는 작업의 단위이다.

<br/><br/>

## Transaction의 특징

Transaction의 특징은 크게 4가지로 나뉜다.

* 원자성 (Atomicity)
* 일관성 (Consistency)
* 독립성 (Isolation)
* 지속성 (Durability)

<br/><br/>

#### 원자성

* Transaction의 연산은 데이터베이스에 모두 반영되던지 아니면 모두 반영되지 않아야 한다.

<br/>

#### 일관성

* 시스템이 가지고 있는 고정요소는 Transaction 수행 전과 Transaction 수행 완료 후의 상태가 같아야 한다.

<br/>

#### 독립성

* 둘 이상의 Transaction이 동시에 실행되는 경우 다른 Transaction의 연산이 끼어들 수 없다.

<br/>

#### 지속성

* 성공적으로 완료된 Transaction의 결과는 영구적으로 반영되어야 한다.

<br/><br/>

## Transaction 연산 - Commit && Rollback

Commit : 하나의 Transaction이 성공적으로 끝났으며, Transaction이 마무리되었다는 것을 알리기 위한 연산. <br/>

Rollback : Transaction 처리가 비정상적으로 종료된 경우, Transaction을 다시 시작하거나, Transaction의 부분적으로 연산한 결과를 취소.

<br/><br/>

## Spring에서 Transaction 적용 방법

Spring에서는 @Transactional 어노테이션을 사용해 트랜잭션을 선언적으로 처리한다. <br/>
이 어노테이션을 클래스나 메서드에 붙이면, 트랜잭션 관리를 위한 프록시 객체가 생성되어 Commit 혹은 Rollback 작업을 수행한다. <br/>
이 외에도 트랜잭션 스크립트 방식을 사용하여 트랜잭션을 처리할 수 있다.

<br/><br/>

## Transaction Manager 종류

스프링 트랜잭션 추상화의 핵심 인터페이스는 PlatformTransactionManager다. <br/>
모든 스프링의 트랜잭션 기능과 코드는 이 인터페이스를 통해서 로우 레벨의 트랜잭션 서비스를 이용할 수 있다. <br/>
PlatformTransactionManager 인터페이스는 아래와 같다.

<br/>

    package org.springframework.transaction;

    import org.springframework.lang.Nullable;
    
    public interface PlatformTransactionManager extends TransactionManager {
    
        TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;
    
        void commit(TransactionStatus status) throws TransactionException;
        void rollback(TransactionStatus status) throws TransactionException;
    
    }

<br/>

트랜잭션을 시작한다는 의미의 getTransaction()이 있다. <br/>
getTransaction()은 트랜잭션 속성에 따라서 새로 시작하거나, 진행 중인 트랜잭션에 참여하거나, 진행 중인 트랜잭션을 무시하고 새로운
트랜잭션을 만드는 식으로 상황에 따라 다르게 동작한다.

<br/>

TransactionDefinition은 트랜잭션의 속성을 나타내는 인터페이스다. <br/>
TransactionStatus는 현재 참여하고 있는 트랜잭션의 ID와 구분정보를 담고 있다. 커밋 또는 롤백 시에 사용한다.

<br/><br/>

## Transaction 속성

모든 트랜잭션이 같은 방식으로 동작하는 것은 아니다. <br/>
작업 전체가 실패하거나 성공하는 하나의 작업으로 묶는다는 점에서는 다를 바 없겠지만, 세밀히 따져보면 몇 가지 차이점이 있다. <br/>

스프링은 트랜잭션의 경계를 설정할 때 여러 개의 속성을 제공한다.

<br/><br/>

## Transaction propagation

트랜잭션을 시작하거나, 기존 트랜잭션에 참여하는 방법을 결정하는 속성이다. <br/>
트랜잭션 경계의 시작 지점에서 트랜잭션 전파 속성을 참조해서 해당 범위의 트랜잭션을 어떤 식으로 진행시킬지 결정할 수 있다.

스프링이 지원하는 트랜잭션 전파 속성은 6개가 있으며, 모든 속성이 모든 종류의 트랜잭션 매니저와 데이터 액세스 기술에서 다 지원되진 않을 수 있으므로 주의하여 사용해야 한다.

<br/>

#### REQUIRED <br/>

Defualt 속성이며, 모든 트랜잭션 매니저가 지원한다. 보통 이 속성으로 많이 사용한다. <br/>
미리 시작된 트랜잭션이 있으면 참여하고, 없으면 트랜잭션을 생성한다.

<br/>

#### SUPPORTS <br/>

이미 시작된 트랜잭션이 있으면 참여하고 없으면 트랜잭션 없이 진행한다. <br/>
트랜잭션이 없긴 하지만 해당 경계 안에서 Connection이나 Hibernate Session 등을 공유할 수 있다.

<br/>

#### MANDATORY <br/>

REQUIRED와 비슷하며, 이미 시작된 트랜잭션이 있으면 참여한다. <br/>
하지만 트랜잭션이 없다면 생성하는 것이 아니라 예외를 발생시킨다. 혼자서 독립적으로 트랜잭션을 실행하면 안 되는 경우에 사용한다.

<br/>

#### REQUIRES NEW <br/>

항상 새로운 트랜잭션을 시작한다. 이미 진행 중인 트랜잭션이 있다면, 트랜잭션을 보류시킨다.

<br/>

#### NOT SUPPORTED <br/>

트랜잭션을 사용하지 않게 한다. 이미 진행 중인 트랜잭션이 있으면 보류시킨다.

<br/>

#### NEVER <br/>

트랜잭션을 사용하지 않도록 강제한다. 이미 진행 중인 트랜잭션도 존재하면 안 되며, 트랜잭션이 있다면 예외를 발생시킨다.

<br/>

#### NESTED <br/>

이미 진행 중인 트랜잭션이 있으면 중첩 트랜잭션을 시작한다. <br/>
중첩 트랜잭션은 말 그대로 트랜잭션 안에 트랜잭션을 만드는 것이다. 독립적인 트랜잭션을 만드는 REQUIRES NEW와는 다르다.

중첩된 트랜잭션은 먼저 시작된 부모 트랜잭션의 커밋과 롤백에는 영향을 받지만, 자신의 커밋과 롤백은 부모 트랜잭션에게 영향을 주지 않는다.

<br/>

### REQUIRES NEW와 NESTED 차이 <br/>

예를 들면, 로그를 찍는 기능은 부가 기능이다. 만약 부가 기능에 문제가 생겨서 트랜잭션을 롤백 해야 하는 상황이다. <br/>

로그를 찍는 기능에 문제가 생겼다고 해서, 핵심 기능에 영향을 끼치면 안 된다. <br/>
따라서 부가 기능의 트랜잭션은 부모 트랜잭션 안에 영속되지만, 부모 트랜잭션에 영향을 줄 수 없다.

<br/><br/>

## Transaction isolation

트랜잭션 격리 수준은 동시에 여러 트랜잭션이 진행될 때에 트랜잭션의 작업 결과를 다른 트랜잭션에게 어떻게 노출할 것인지를 결정하는 기준이다. <br/>
스프링은 다음 다섯 가지 격리 수준 속성을 지원한다.

<br/>

#### DEFAULT <br/>

사용하는 데이터 액세스 기술 또는 DB 드라이버의 디폴트 설정을 따른다. <br/>
보통 드라이버의 격리 수준은 DB의 격리 수준을 따르는게 일반적이다. 대부분의 DB는 READ COMMITTED를 기본 격리 수준으로 설정하고 있다.

<br/>

#### READ UNCOMMITTED <br/>

가장 낮은 격리 수준이다. 하나의 트랜잭션이 커밋 되기 전에 그 변화가 다른 트랜잭션에 그대로 노출되는 문제가 있다. <br/>
격리 수준과 속도는 반비례하기 때문에 속도는 가장 빠르다.

<br/>

#### READ COMMITED <br/>

실제로 가장 많이 사용되는 격리 수준이다. 만약 이 격리 수준을 사용하고 싶다면 DEFAULT로 설정해둬도 될 것이다. <br/>
READ UNCOMMITTED와 달리 다른 트랜잭션이 커밋 하지 않은 정보는 읽을 수 없다.

하지만 하나의 트랜잭션이 읽은 로우를 다른 트랜잭션이 수정할 수 있다. <br/>
때문에 처음 트랜잭션이 같은 로우를 다시 읽을 경우 다른 내용이 조회될 수 있다.

<br/>

#### REPEATABLE READ <br/>

하나의 트랜잭션이 읽은 로우를 다른 트랜잭션이 수정하는 것을 막아준다. 하지만 새로운 로우를 추가하는 것은 제한하지 않는다. <br/>
예를 들어 데이터 1, 2, 3이 있을 때 이를 수정하는 것은 허용하지 않지만, 4, 5, 6을 추가하는 것은 허용하기에 이 또한 데이터 일관성을 해칠 수 있다.

그래도 READ COMMITED보다는 격리 수준이 높은 만큼 덜 일어나는 현상이다.

<br/>

#### SERIALIZABLE <br/>

가장 강력한 트랜잭션 격리 수준이다. 이름 그대로 트랜잭션을 순차적으로 진행시켜주기 때문에 여러 트랜잭션이 동시에 같은 테이블의 정보를 액세스하지 못한다. <br/>
가장 안전한 격리 수준이지만 가장 성능이 떨어지기 때문에 극단적으로 안전한 작업이 필요한 경우가 아니라면 자주 사용되지 않는다.

<br/><br/>

## Transaction timeout

timeout 속성을 사용하면 트랜잭션에 제한 시간을 지정할 수 있다. 단위는 초(second) 단위로 지정한다. <br/>
default는 트랜잭션 시스템의 제한 시간을 따른다.

<br/><br/>

## Transaction readOnly

트랜잭션을 읽기 전용으로 설정할 수 있다. <br/>
성능을 최적화하기 위해 사용할 수도 있고, 특정 트랜잭션 작업 안에서 쓰기 작업이 일어나는 것을 의도적으로 방지하기 위해 사용할 수도 있다.

<br/>

    트랜잭션을 읽기 전용으로 설정하면 왜 성능이 최적화 될까?
    변경감지를 위한 스냅샷을 유지하지 않아도 되고, 영속성 컨텍스트를 Flush하지 않아도 되기 때문이다.

<br/>

aop/tx 스키마로 트랜잭션 선언 시 이름 패턴을 이용해 읽기 전용 속성으로 만드는 경우가 많다. <br/>
보통 get 또는 find 같은 이름의 메서드를 읽기 전용으로 만들어서 사용하면 편하다.

선언적 트랜잭션의 경우 각 어노테이션마다 일일이 읽기 전용을 지정해 줘야 한다.

<br/><br/>

## Transaction rollbackFor

선언적 트랜잭션에서는 런타임 예외가 발생하면 롤백 한다. 반면에 예외가 전혀 발생하지 않거나 체크 예외가 발생하면 커밋 한다. <br/>
체크 예외를 커밋 대상으로 삼은 이유는 체크 예외가 예외적인 상황에서 사용되기보다는 리턴 값을 대신해서 비즈니스적인 의미를 담은 결과를 돌려주는 용도로 많이 사용되기 때문이다.

<br/>

    @Transactional(propagation = Propagation.NESTED, rollbackFor = NotFoundAuthIdException.class)
    public void save(String message, String token) {
        String authority = jwtUtil.extractAuthorityFromToken(token);
        ...
    }

<br/><br/>

## Transaction noRollbackFor

rollbackFor 속성과는 반대로 기본적으로 롤백 대상인 런타임 예외를 트랜잭션 커밋 대상으로 지정한다.
