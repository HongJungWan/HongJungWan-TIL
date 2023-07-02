## Transaction과 @Transactional 총 정리

<br/>

Transaction의 기본적인 개념부터 알아야 @Transactional을 더 잘 이해할 수 있으므로, <br/>
데이터베이스상에서의 Transaction의 개념을 먼저 알아보자.

<br/>

## Transaction의 정의

하나의 작업을 수행하기 위해 필요한 데이터베이스의 연산들을 모아놓은 것, <br/>
데이터베이스에서 논리적인 작업의 단위이며 장애가 발생했을 때 데이터를 복구하는 작업의 단위이다.

<br/>

## Transaction의 특징

Transaction의 특징은 크게 4가지로 나뉜다.

* 원자성 (Atomicity)
* 일관성 (Consistency)
* 독립성 (Isolation)
* 지속성 (Durability)

<br/><br/>

1.1 원자성

* Transaction의 연산은 데이터베이스에 모두 반영되던지 아니면 모두 반영되지 않아야한다.

<br/>

1.2 일관성

* 시스템이 가지고 있는 고정요소는 Transaction 수행 전과 Transaction 수행 완료 후의 상태가 같아야 한다.

<br/>

1.3 독립성

* 둘 이상의 Transaction이 동시에 실행되는 경우 다른 Transaction의 연산이 끼어들 수 없다.

<br/>

1.4 지속성

* 성공적으로 완료된 Transaction의 결과는 영구적으로 반영되어야 한다.

<br/>

## Transaction 연산 - Commit && Rollback

Commit : 하나의 Transaction이 성공적으로 끝났으며, Transaction이 마무리되었다는 것을 알리기 위한 연산. <br/>

Rollback : Transaction 처리가 비정상적으로 종료된 경우, Transaction을 다시 시작하거나, Transaction의 부분적으로 연산한 결과를 취소.

<br/>

## Spring에서 Transaction 적용 방법

Spring에서는 @Transactional 어노테이션을 사용해 트랜잭션을 선언적으로 처리한다. <br/>
이 어노테이션을 클래스나 메서드에 붙이면, 트랜잭션 관리를 위한 프록시 객체가 생성되어 Commit 혹은 Rollback 작업을 수행한다. <br/>
이 외에도 트랜잭션 스크립트 방식을 사용하여 트랜잭션을 처리할 수 있다.

<br/>

## Transaction Manager의 종류


