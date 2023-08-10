# Deep Dive SQL #1

<br>

### 데이터베이스 모델링 특징

| 특징  | 설명                                 |
|-----|------------------------------------|
|추상화(Abstraction)| 현실 세계를 간략하게 표현한다.                  |
|단순화(Simple)| 누구나 쉽게 이해할 수 있도록 표현한다.             |
|명확성(Clarity)| 명확하게 의미가 해석되어야 하고 한 가지 의미를 가져야 한다. |

<br><br><br>

### 식별자 관계 vs 비식별자 관계

| --                | 식별자 관계                                                                     | 비식별자 관계                                                                           |
|-------------------|----------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| 목적                | 부모 테이블의 기본 키 또는 유니크 키를 자식 테이블이 자신의 기본 키로 사용하는 관계 <br><br><br> **강한 연결 관계** | 부모 테이블의 기본 키 또는 유니크 키를 자신의 기본 키로 사용하지 않고, 외래 키로 사용하는 관계 <br><br><br> **약한 연결 관계** |
| 기본 키(Primary key) | 부모 릴레이션의 기본 키가 자식 릴레이션의 기본 키로 사용                                           | 부모 릴레이션의 기본 키가 자식 릴레이션의 일반 속성으로 사용                                                |
| 표기법               | 실선 표현                                                                      | 점선 표현                                                                             |

<br><br><br>

### 성능을 고려한 데이터 모델링 순서

| 순서                                        |
|-------------------------------------------|
| 1. 데이터 모델링을 할 때 정규화를 정확하게 수행              |
| 2. 데이터베이스 용량 산정 수행                        |
| 3. 데이터베이스에 발생되는 트랜잭션 유형 파악                |
| 4. 용량과 트랜잭션의 유형에 따라 반정규화 수행               |
| 5. 이력 모델의 조정, PK/FK 조정, 슈퍼 타입/서브 타입 조정 수행 |
| 6. 성능 관점에서 데이터 모델 검증                      |

<br><br><br>

### Join 기법

| 방법                            | 설명                                                                                                                                                                     |
|-------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 중첩 반복 조인(Nested Loop Join)  | - 좁은 범위에 유리 <br/> - 순차적으로 처리하며, Random Access 위주 <br/> - 후행(Driven) 테이블에 인덱스가 있으면 더 효율적                                                                                |
| 정렬 합병 조인(Sort Merge Join)   | - 테이블을 정렬(Sort) 한 후에 정렬된 테이블을 병합(Merge) 하면서 조인을 실행한다.<br/> - 조인 연결고리의 비교 연산자가 범위 연산( >, < )인 경우 Nested Loop 조인보다 유리 <br/> - 두 결과 집합의 크기가 차이가 많이 나는 경우에는 비효율적           |
| 해시 조인(Hash Join)             | - 해시(Hash) 함수를 사용하여 두 테이블의 자료를 결합하는 조인 방식 <br/> - 일부 상황에서 Nested Loop 조인과 Sort Merge 조인보다 효율적으로 동작 <br/> - 대용량 데이터 처리는 큰 hash area를 필요로 함으로, 메모리의 지나친 사용으로 오버헤드 발생 가능성 |

<br><br><br>

### GROUPING, ROLLUP, CUBE

* GROUPING은 기본적인 집계, ROLLUP은 계층적 집계, CUBE는 다차원 집계를 수행

<br>

### GROUPING
```sql
SELECT column1, column2, AGG_FUNCTION(column3)
FROM table
GROUP BY column1, column2;
```

<br>

### ROLLUP
```sql
SELECT column1, column2, AGG_FUNCTION(column3)
FROM table
GROUP BY ROLLUP(column1, column2);
```

* column1과 column2에 따른 집계
* column1에 따른 집계
* 전체에 대한 집계

<br>

### CUBE
```sql
SELECT column1, column2, AGG_FUNCTION(column3)
FROM table
GROUP BY CUBE(column1, column2);
```

* column1과 column2에 따른 집계
* column1에 따른 집계
* column2에 따른 집계
* 전체에 대한 집계

<br><br><br>

### MERGE INTO

* MERGE INTO 문은 특정 키에 대해서 레코드가 있을 때에 수정사항에 대해서 UPDATE를 하고, 레코드가 없으면 새롭게 INSERT를 할 수 있는 구문이다.

<br>

```text
[TEST1]
COL1  COL2  COL3
------------------
A      X      1
B      Y      2
C      Z      3

[TEST2]
COL1  COL2  COL3
------------------
A      X      1
B      Y      2
C      Z      3
D      가      4
E      나      5
```

<br>

```sql
MERGE INTO TEST1
USING TEST2
    ON (TEST1.COL1 = TEST2.COL1)
WHEN MATCHED THEN
    UPDATE
        SET TEST1.COL3 = 4
        WHERE TEST1.COL3 = 2
    DELETE
        WHERE TEST1.COL3 <= 2
WHEN NOT MATCHED THEN
    INSERT(TEST1.COL1, TEST1.COL2, TEST1.COL3)
    VALUES(TEST2.COL1, TEST2.COL2, TEST2.COL3);
```
