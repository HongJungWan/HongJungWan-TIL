# Oracle - SQL #1

<br>

### **ALIAS**

- **컬럼 별칭: AS 키워드**는 선택적이다.


- **테이블 별칭:** **테이블명 ALIAS명**

```sql
SELECT TB1.NAME AS N,  -- 컬럼에 ALIAS를 사용할 경우
       TB1.AGE A,      -- AS를 붙이지 않아도 사용가능
       TB1.'123' AS NUM
FROM TB1


SELECT A.NAME
FROM TB1 A, TB2 B      -- 테이블에 ALIAS를 사용할 경우
WHERE A.NAME = B.NAME
```

<br><br><br>

### ROWNUM

- 오라클에서 조회된 행이 몇번째 행인지 부여

```sql
-- ROWNUM은 정렬 전에 할당
SELECT ROWNUM, ENAME, JOB, SAL
FROM SCOTT.EMP


-- 정렬된 결과에 순서 부여
SELECT ROWNUM, ENAME, JOB, SAL
FROM (SELECT ENAME, JOB, SAL
      FROM SCOTT.EMP
      ORDER BY JOB)


-- ROWNUM을 역순으로 사용
SELECT ROWNUM, ENAME, JOB, SAL
FROM (SELECT ENAME, JOB, SAL
      FROM SCOTT.EMP
      ORDER BY JOB)
ORDER BY ROWNUM DESC


-- ROWNUM이 1~3인 값만 가져오기
SELECT ROWNUM, ENAME, JOB, SAL
FROM SCOTT.EMP
WHERE ROWNUM BETWEEN 1 AND 3
```

<br><br><br>

### Sub Query

- SELECT 쪽에 서브쿼리가 올 경우 스칼라 서브쿼리 **(Scalar Subqueries)**


- FROM 절에 서브쿼리가 올 경우에는 인라인 뷰 **(Inline View)**


- WHERE 절에 서브쿼리가 올 경우에는 중첩 서브쿼리 **(Nested Subqueries)**

```sql
-- 스칼라 서브쿼리
SELECT '홍정완' AS NAME,
      (SELECT AGE 
       FROM TB1
       WHERE NAME = '홍정완'
      ) AS AGE
FROM DUAL


-- 인라인 뷰
SELECT AGE
FROM (SELECT AGE 
      FROM TB1
      WHERE NAME = '홍정완')


-- 중첩 서브쿼리
SELECT NAME, AGE
FROM TB1
WHERE NAME = (SELECT NAME
              FROM TB1
              WHERE NAME = '홍정완')
```

<br><br><br>

### OVER()

- 오라클에서 OVER()를 사용하면 GROUP BY나 서브쿼리를 사용하지 않고, 분석 함수 (SUM, MAX, COUNT)와 집계 함수 (GROUP BY, ORDER BY)를 사용할 수 있다.
- 집계 함수 사용 시, 서브쿼리가 지나치게 길어짐 → **OVER()를 사용해 쿼리 길이를 획기적으로 줄일 수 있다.**

<br>

**기본 구조**

```sql
<분석함수>(<컬럼>) OVER (
    [PARTITION BY <컬럼1>, <컬럼2>, ...]
    [ORDER BY <컬럼1> [ASC|DESC], <컬럼2> [ASC|DESC], ...]
    [ROWS BETWEEN <시작점> AND <끝점>]
)
```

- **PARTITION BY**: 행들을 지정한 컬럼의 값에 따라 파티션으로 나눈다.
- **ORDER BY**: 지정된 컬럼의 값에 따라 각 파티션 내에서 행들을 정렬.
- **ROWS BETWEEN**: 특정 행을 기준으로 몇 개의 행을 포함할지를 지정.

<br>

**예시**

```sql
SELECT
    employee_id,
    department_id,
    salary,
    SUM(salary) OVER (PARTITION BY department_id) as dept_total_salary,
    AVG(salary) OVER (PARTITION BY department_id) as avg_salary,
    MAX(salary) OVER (PARTITION BY department_id) as max_salary,
    RANK() OVER (PARTITION BY department_id ORDER BY salary DESC) as rank
FROM employees;
```

<br><br><br>

### JOIN

```sql
-- INNER JOIN: TB1과 TB2에서 조건에 맞는 레코드만 반환.
SELECT * 
FROM TB1 JOIN TB2 
ON TB1.NAME = TB2.NAME;


-- LEFT OUTER JOIN: TB1의 모든 레코드와, 조건에 맞는 TB2의 레코드를 반환. 
-- TB2에 매칭되는 레코드가 없는 경우 NULL 값을 반환.
SELECT * 
FROM TB1 LEFT OUTER JOIN TB2 
ON TB1.NAME = TB2.NAME;


-- RIGHT OUTER JOIN: TB2의 모든 레코드와, 조건에 맞는 TB1의 레코드를 반환. 
-- TB1에 매칭되는 레코드가 없는 경우 NULL 값을 반환.
SELECT * 
FROM TB1 RIGHT OUTER JOIN TB2 
ON TB1.NAME = TB2.NAME;


-- FULL OUTER JOIN: TB1 또는 TB2에 있는 모든 레코드를 반환. 
-- 조건에 맞는 레코드가 없는 경우 해당 컬럼에 NULL 값을 반환.
SELECT * 
FROM TB1 FULL OUTER JOIN TB2 
ON TB1.NAME = TB2.NAME;
```

<br><br><br>

### **UNION, UNION ALL, INTERSECT, MINUS**

- **UNION**은 중복을 제거하면서 두 결과 집합을 합칠 때 사용.


- **UNION ALL**은 중복을 제거하지 않고 두 결과 집합을 합칠 때 사용.


- **INTERSECT**는 두 결과 집합의 공통 부분만을 찾을 때 사용.


- **MINUS**는 한 결과 집합에서 다른 결과 집합을 제외할 때 사용.


```sql
-- UNION은 각 결과를 합치며 중복 제거
SELECT * FROM TB1
UNION         
SELECT * FROM TB2


-- UNION ALL은 각 결과를 합치며 중복 포함
SELECT * FROM TB1
UNION ALL      
SELECT * FROM TB2


-- INTERSECT는 각 결과에서 공통적으로 존재하는 레코드 반환
SELECT * FROM TB1
INTERSECT
SELECT * FROM TB2


-- MINUS는 1번 쿼리 결과 집합에서 2번 쿼리 결과 집합을 제외한 레코드 반환
SELECT * FROM TB1
MINUS
SELECT * FROM TB2
```

<br><br><br>

### DISTINCT를 이용한 중복 제거

- 중복을 제거하고 값들을 묶는 방식은 GROUP BY도 가능.
    - 일부 상황에서 GROUP BY가 DISTINCT에 비해 빠르게 동작할 수 있다.
    - 데이터가 적을 경우에는 GROUP BY를 쓰나 DISTINCT를 쓰나 큰 차이가 없다.

```sql
SELECT DISTINCT JOB
FROM EMP

---

SELECT *
FROM EMP
GROUP BY JOB
```

<br><br><br>

### GROUP BY

- 묶을 기준이 되는 컬럼을 **GROUP BY 컬럼**을 사용해서 묶는다.
    - 묶은 후에는 묶은 값을 기준으로만 조회 가능, 따라서 대부분 묶은 후 → 합계 or 평균을 구하는 식으로 사용

```sql
SELECT AVG(SAL), JOB
FROM EMP
GROUP BY JOB
ORDER BY AVG(SAL) DESC
```

<br><br><br>

### HAVING

- GROUP BY를 사용해서 값을 묶은 뒤에는 묶은 내용에 WHERE 사용 불가 → HAVING 사용
    - GROUP BY 하단에 **HAVING 조건** 이런 식으로 사용한다.

```sql
SELECT AVG(SAL), JOB
FROM EMP
GROUP BY JOB
HAVING AVG(SAL) > 2500
ORDER BY AVG(SAL) DESC
```

<br><br><br>

### WHERE - LIKE

- 특정 문자를 포함하고 있을 경우에만 조회

```sql
SELECT * 
FROM TB1
WHERE NAME LIKE '홍%' -- '홍'으로 시작하는 경우에만

SELECT * 
FROM TB1
WHERE NAME LIKE '%정완' -- '정완'으로 끝나는 경우에만

SELECT * 
FROM TB1
WHERE NAME LIKE '%정완%' -- '정완'이 들어있을 경우
```

<br><br><br>

### WHERE - LIKE IN

- 오라클에서 SELECT 시 **WHERE 절에서 REGEXP_LIKE를 사용**하면 LIKE IN 처럼 다중 LIKE로 사용 가능


- **REGEXP_LIKE (컬럼명, '값|값')**


- RegExp - 정규표현식 (Regular Expression)

```sql
SELECT * 
FROM EMP
WHERE REGEXP_LIKE(ENAME, 'JA|AL|ING')
```

<br><br><br>

### WHERE - IF ELSE

- ORACLE에서 WHERE에 IF, ELSE IF, ELSE를 사용하려는 경우에는 MYBATIS에서 사용하는 것이 최선이지만 상황이 여의치 않을 경우에는 WHERE 절에 IF 처럼 넣어 줄 수 있다.


- **WHERE ((조건1) OR (조건2))**
    - IF 조건을 사용할 거면 ELSE를 꼭 만들거나 IF에 해당하지 않을 경우 조건을 수행하지 않도록 구현하자.

```sql
SELECT * 
FROM EMP
WHERE (
	(JOB = 'CLERK' AND SAL < 1000) OR          -- IF
	(JOB = 'PRESIDENT' AND SAL > 3000) OR      -- ELSE IF
	(1=1 AND SAL > 2900)                       -- ELSE
)
```

<br><br><br>

### WHERE - NOT, 특정 조건 제외 후 조회

```sql
SELECT *
FROM TB1
WHERE NOT(AGE = 40 AND NAME = '홍정완')
```

<br><br><br>

### WHERE - ANY, SOME, ALL

- 먼저 ANY와 SOME은 동일한 기능인데 **ANY(값, 값, 값) 중에서 조건이 하나라도 맞으면 조회한다.**
    - SAL > ANY(1000, 1500)으로 걸면 SAL이 1000 OR 1500보다 크면 조회하게 된다.

```sql
-- ANY, SOME
SELECT *
FROM TB1
WHERE SAL > ANY(1000, 1500)
ORDER BY SAL
```

<br>

- **ALL은 조건에 모두 맞을 경우에만 조회하게 된다.**
    - SAL > ALL(1000, 1500)의 경우에는 SAL이 1000, 1500보다 큰 경우에만 조회되게 된다.

<br><br>