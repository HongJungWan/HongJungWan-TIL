# Oracle - SQL #2

<br>

### TO_DATE

- ORACLE에서 **DATE 타입의 컬럼에 특정 날짜값을 넣어줘야 하는 경우**에는 TO_DATE를 사용해야 한다.


- **TO_DATE('날짜값', 'FORMAT');**

<br>

```sql
INSERT INTO TEST(DATE_COLUMN) 
VALUES(TO_DATE('2019/10/15 21:31:10', 'YYYY/MM/DD HH24:MI:SS'));

---

SELECT TO_DATE(DATE_COLUMN,'HH24:MI:SS') 
FROM TB1;
```

<br><br><br>

### TO_CHAR

- ORACLE에서 TO_CHAR는 숫자나 날짜를 원하는 형태의 데이터로 만들 수 있다.

| YYYY | 4자리 년도를 표시 |
| --- | --- |
| MM | 월을 2자리 숫자로 표시, 예: 01, 12 |
| DD | 일을 2자리 숫자로 표시 |
| HH24 | 24시간 형식의 시간을 표시 |
| MI | 분을 표시 |
| SS | 초를 표시 |

<br>

```sql
TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'); -- 2023-10-03 15:45:30

--- 숫자를 문자로 변경
TO_CHAR(값);

--- 값이 오전/오후인지 표시
TO_CHAR(SYSDATE, 'HH:MI AM');
TO_CHAR(SYSDATE, 'HH:MI PM');

--- 값이 무슨 요일인지
TO_CHAR(SYSDATE, 'DAY'); -- WEDNESDAY

--- 값을 년월일, 요일까지 표시
TO_CHAR(SYSDATE, 'DL');

--- 999를 입력한 자리수까지 표시
TO_CHAR(값, '999')

--- 따옴표(,) 소수점 표시
TO_CHAR(값, '999,999');
TO_CHAR(값, '999.99');

--- S대신 양수면 +, 음수면 -가 나옴
TO_CHAR(값, 'S999');
```

<br>

```sql
SELECT TO_CHAR(order_date, 'YYYY-MM-DD HH24:MI:SS') AS formatted_date
FROM orders;

---

SELECT TO_CHAR(price, '999,999.99') AS formatted_price
FROM products;
```

<br><br><br>

### TO_CHAR vs TO_DATE

<br>

| TO_DATE vs TO_CHAR        |
|---------------------------|
||
| TO_DATE는 문자열을 날짜 값으로 변환.  |
| TO_CHAR는 날짜나 숫자를 문자열로 변환. |

<br><br><br>

### DECODE

- if - else랑 비슷

```sql
SELECT DECODE(column_name, '조건 1', '조건 1 일때 값') AS decode_result
FROM TB1;


SELECT DECODE(
    column_name,
    '조건 1', '조건 1 일때 값',
    '조건 2', '조건 2 일때 값', 
    '조건에 맞는 값이 없을 때'
) AS decode_result
FROM TB1;


SELECT DECODE(
    job_id, 
    'MANAGER', '관리자', 
    'EMPLOYEE', '직원', 
    '알 수 없음'
) AS job_description
FROM employees;
```

<br><br><br>

### CASE WHEN THEN

- **조건에 맞을 경우에는 THEN으로 그렇지 않을 경우에는 ELSE로 출력**


- **ELSE를 작성하지 않을 경우에는 조건에 맞지 않는 값들이 모두 NULL로 출력**

```sql
SELECT 
    CASE 
        WHEN TEXT = 'A' THEN 'A입니다'
        WHEN TEXT = 'B' THEN 'B입니다'
        WHEN TEXT = 'C' THEN 'C입니다'
        ELSE 'A,B,C가 아닙니다'
    END AS CASE_WHEN_RESULT
FROM CASEWHENTB;
```

<br><br><br>

### **NVL, NVL2**

- **NVL 함수**: 첫 번째 인자가 NULL인 경우 두 번째 인자를 반환.


- **NVL2 함수**: 첫 번째 인자가 NULL인 경우 세 번째 인자를 반환.

```sql
SELECT NVL(employee_name, 'No Name') AS employee_name
FROM employees;

---

SELECT NVL2(email, 'Has Email', 'No Email') AS email_status
FROM employees;
```

<br><br><br>

### BETWEEN A AND B

- 오라클에서 **BETWEEN A AND B**는 특정 범위의 날짜를 조건으로 조회할 때 주로 사용.


- WHERE 절에서 컬럼명 **BETWEEN A AND B** 이렇게 사용.

```sql
SELECT DATECOL
FROM (SELECT TO_DATE('20190713') AS DATECOL FROM DUAL
      UNION ALL 
      SELECT TO_DATE('20190113') AS DATECOL FROM DUAL
      UNION ALL
      SELECT TO_DATE('20201210') AS DATECOL FROM DUAL)
WHERE DATE1 BETWEEN '20190101' AND '20191231'
```

<br><br><br>

### EXISTS, NOT EXISTS

- **EXISTS**는 조회 결과가 있을때만 조회.


- **NOT EXISTS**는 조회 결과가 없을때만 조회.

```sql
SELECT * 
FROM EMP
WHERE EXISTS (
    SELECT EMPNO 
    FROM EMP 
    WHERE EMPNO = '7369'
)

---

SELECT * 
FROM EMP
WHERE NOT EXISTS (
    SELECT EMPNO 
    FROM EMP 
    WHERE EMPNO = '73691'
)
```

<br><br><br>

### CONCAT, ||

- 오라클에서 `CONCAT, ||`를 사용해 문자열을 합칠 수 있다.


- **CONCAT(값, 값)**


- 2개 이상을 합칠 경우에는 ||를 사용 → **값 || 값 || 값**

```sql
SELECT 
    CONCAT('A', 'B') CONCAT_ATTACH1,
    CONCAT(1, 2) CONCAT_ATTACH2,
    'A' || 'B' || 'C' ATTACH3,
    1 || 2 || 3 || 4 ATTACH4
FROM DUAL

---

출력 값:
AB
12
ABC
1234
```

<br><br><br>

### WITH

- 오라클에서 WITH는 가상의 테이블을 만들 때 사용
    - **WITH 테이블명 AS ( 테이블명에 들어갈 값 SQL )**


- WITH 안에서는 UNION, UNION ALL을 사용해 타 WITH 테이블과도 합치기 가능.

```sql
WITH TBL1 AS 
            (
              SELECT 'A' AS COL1 FROM DUAL
              UNION ALL SELECT 'B' FROM DUAL
              UNION ALL SELECT 'C' FROM DUAL
              UNION ALL SELECT 'D' FROM DUAL
            ),
     TBL2 AS 
            (
              SELECT 'E' AS COL1 FROM DUAL
              UNION ALL
              SELECT COL1 FROM TBL1 -- 타 WITH와 합치기 가능
            )

SELECT * FROM TBL2;

---

출력 값:

|COL1|
----
|E|
|A|
|B|
|C|
|D|
```

<br><br><br>

### **ROLLUP**, **CUBE**, **GROUPING**

* 집계 데이터를 생성하고 구별하기 위해 사용

<br>

### ROLLUP

```sql
SELECT department_id, job_id, SUM(salary) AS total_salary
FROM employees
GROUP BY ROLLUP(department_id, job_id);

---

DEPARTMENT_ID | JOB_ID | TOTAL_SALARY
------------------------------
10            | SALES  | 12000
10            | HR     | 6000
10            | NULL   | 18000  -- 10번 부서의 총급여
20            | HR     | 7000
20            | NULL   | 7000   -- 20번 부서의 총급여
NULL          | NULL   | 25000  -- 모든 부서의 총급여
```

<br>

### CUBE

```sql
SELECT department_id, job_id, SUM(salary) AS total_salary
FROM employees
GROUP BY CUBE(department_id, job_id);

---

DEPARTMENT_ID | JOB_ID | TOTAL_SALARY
------------------------------
10            | SALES  | 12000
10            | HR     | 6000
20            | HR     | 7000
10            | NULL   | 18000   -- 10번 부서의 총급여
20            | NULL   | 7000    -- 20번 부서의 총급여
NULL          | HR     | 13000   -- HR 직무의 총급여
NULL          | SALES  | 12000   -- SALES 직무의 총급여
NULL          | NULL   | 25000   -- 모든 부서와 직무의 총급여
```

<br>

### GROUPING

```sql
GROUPING 함수는 CUBE와 ROLLUP 연산에서 사용될 때,
해당 컬럼이 집계에 포함되지 않았을 때 1을 반환하고, 원래 값이 사용되었을 때는 0을 반환

---

SELECT department_id, job_id, SUM(salary) AS total_salary,
       GROUPING(department_id) AS grp_department,
       GROUPING(job_id) AS grp_job
FROM employees
GROUP BY CUBE(department_id, job_id);

---

DEPARTMENT_ID | JOB_ID | TOTAL_SALARY | GRP_DEPARTMENT | GRP_JOB
--------------------------------------------------------------
10            | SALES  | 12000        | 0              | 0
10            | HR     | 6000         | 0              | 0
20            | HR     | 7000         | 0              | 0
10            | NULL   | 18000        | 0              | 1
20            | NULL   | 7000         | 0              | 1
NULL          | HR     | 13000        | 1              | 0
NULL          | SALES  | 12000        | 1              | 0
NULL          | NULL   | 25000        | 1              | 1
```

<br><br>