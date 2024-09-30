# 08. SQL응용

## DDL(Define)

- DB를 구축하거나 수정할 목적으로 사용하는 언어
- CREATE
    - SCHEMA, DOMAIN, TABLE, VIEW, INDEX를 정의
- ALTER
    - TABLE에 대한 정의를 변경하는 데 사용
- DROP
    - SCHEMA, DOMAIN, TABLE, VIEW, INDEX를 삭제

### CREATE DOMAIN

```sql
CREATE DOMAIN 도메인명 [AS]데이터 타입
	DEFAULT 기본값
	CONSTRAINT 제약조건명 CHECK 범위값(VALUE IN(a, b, c))
```

### CREATE TABLE

- 테이블을 정의하는 명령문
    - 테이블
        - 데이터베이스의 설계단계에선 **릴레이션**이라고도 부르고, 조작이나 검색에선 테이블이라고 부른다.

```sql
CREATE TABLE 테이블명
	속성명 데이터타입 DEFAULT 기본값 NOT NULL 
	, PRIMARY KEY(기본키, 속성명)
	, UNIQUE(대체키, 속성명)
	, FOREIGN KEY(외래키, 속성명) REFERENCES 참조테이블(기본키)
		ON DELETE 옵션
		ON UPDATE 옵션
	CONSTRAINT 제약조건명 CHECK 조건식
```

### CREATE VIEW

- 뷰를 정의하는 명령문
    - 뷰
        - 하나 이상의 테이블로부터 유도되는 이름을 갖는 가상 테이블
        - 테이블은 물리적으로 구현되지만 뷰는 구현되지 않는다.
            - 뷰를 실행하면 명령문이 시스템에 저장되어있다가 뷰 이름을 사용하면 명령문이 사용되는 방식이다
    
    ```sql
    CREATE VIEW 뷰명(속성명, 속성명...)
    AS SELECT문
    ```
    

### CREATE INDEX

- 인덱스를 정의하는 명령문
    - 인덱스
        - 검색시간을 단축하기 위한 보조적인 데이터 구조
    
    ```sql
    CREATE [UNIQUE] INDEX 인덱스명
    ON 테이블명(속성명 [ASC, DESC], 속성명 [ASC, DESC])
    [CLUSTER];
    ```
    
    - CLUSTER : 사용하면 인덱스가 클러스터드 인덱스로 설정
        - 클러스터드 인덱스
            - 인덱스 키 순서에 따라 정렬되어 저장되는 방식
            - 조회에는 유리하지만 삽입, 삭제를 할 시 재정렬을 해야한다
        - 논 클러스터드 인덱스
            - 인덱스 키값만 정렬되고 실제 값은 정렬이 안되어있는 방식
            - 조회 할 때 실제 데이터의 위치를 찾아야해서 조회할 때 느리다

### ALTER TABLE

- 테이블에 대한 정의를 변경하는 명령어

```sql
ALTER TABLE 테이블명 ADD 속성명 데이터타입 [DEFAULT 기본값]
ALTER TABLE 테이블명 ALTER 속성명 [SET DEFAULT 기본값]
ALTER TABLE 테이블명 DROP COLUMN 속성명[CASCADE]
```

### DROP

- 스키마, 도메인, 기본 테이블, 뷰, 인덱스, 제약조건 등을 제거하는 명령문

```sql
DROP [SCHEMA, DOMAIN, TABLE, VIEW, INDEX, CONSTARAINT] 해당 명[CASCADE | RESTRICT]
```

- RESTRICT : 다른 개체가 제거할 요소를 참조중일 떄는 제거를 취소함

## DCL(Control)

- 데이터의 보안, 무결성, 회복, 병행 제어 등을 정의하는데 사용하는 언어
- DBA가 데이터 관리 목적으로 사용
- COMMIT
    - 결과를 물리적 디스크로 저장하고 정상적으로 완료되었다고 알림
- ROLLBACK
    - 조작 작업이 비정상적으로 종료되었을 때 원래의 상태로 복구
- GRANT
    - 사용자에게 사용 권한을 부여
- REVOKE
    - 사용자의 사용 권한을 취소

### GRANT/REVOKE

```sql
GRANT 권한 ON 개체 TO 사용자 [WITH GRANT OPTION]
REVOKE [GRANT OPTION FOR] 권한 ON 개체 FROM 사용자 [CASCADE]
```

- WITH GRANT OPTION : 부여받은 권한을 다른 사용자에게 다시 부여할 수 있는 권한을 부여
- GRANT OPTION FOR : 다른 사용자에게 권한을 부여할 수 있는 권한을 취소

### ROLLBACK

- 변경되었으나 COMMIT되지 않은 모든 내용들을 취소하고 데이터베이스를 이전 상태로 되돌리는 명령어

### SAVEPOINT

- 트랜잭션 내에서 ROLLBACK할 위치인 저장점을 지정하는 명령

## DML(Maniqlation)

- 저장된 데이터를 실질적으로 관리하는데 사용되는 언어
- SELECT, INSERT, DELETE, UPDATE

### INSERT

- 기본 테이블에 새로운 튜플을 사용할 때 사용
    - 튜플 : 하나의 행, 하나의 데이터
    
    ```sql
    INSERT INTO 테이블명 (속성, 속성)
    VALUES (값, 값)
    ```
    

### DELETE

- 기본 테이블에서 특정 튜플을 삭제할 때 사용
    
    ```sql
    DELETE FROM 테이블명 WHERE 조건
    ```
    

### UPDATE

- 기존 테이블에서 특정 튜플을 수정할 때 사용

```sql
UPDATE 테이블명
SET 속성명 = 값
[WHERE 조건]
```

### SELECT

- 특정 튜플을 조회할 때 사용

```sql
SELECT [PREDICATE] [테이블 명] 
	속성명 [AS 별칭]
FROM 테이블명
WHERE 조건
GROUP BY 속성, 속성
HAVING 조건
ORDER BY 속성명 [ASC | DESC]
```

- PREDICATE : 검색할 튜플 수를 제한하는 명령어를 기술
    - DISTINCT : 중복된 튜플이 있으면 그 중 첫번째 한개만 표시
- GROUP BY : 특정 속성을 기준으로 그룹화하여 검색 할 때 사용. 보통 그룹함수와 함께 사용
- HAVING : GROUP BY절과 함께 사용되며 그룹에 대한 조건을 지정
- ASC : 오름차순(작은 → 큰)
- DESC : 내림차순 (큰 → 작은)

### 조건 지정 검색

- LIKE 연산자 : 대표 문자를 이용해 문자 패턴과 일치하는 튜플을 검색하기 위해 사용
    - % : 모든 문자를 대표
    - _ : 문자 하나를 대표
    - # : 숫자 하나를 대표

### 하위 질의

- 조건절에 주어진 질의를 먼저 수행하여 검색 결과를 조건절의 피연산자로 사용

### 그룹함수

- GROUP BY절에 지정된 그룹 별로 속성의 값을 집계할 때 사용
- COUNT : 갯수
- SUM : 합
- AVG : 평균
- MAX : 최댓값
- MIN : 최솟값
- STDDEV : 표준편차
- VARIANCE : 분산

## JOIN

- 연관된 튜플들을 결함하여 하나의 새로운 릴레이션을 반환

### INNER JOIN

- EQUI JOIN
    - JOIN 대상 테이블에서 공통 속성을 기준으로 = 비교에 의해 같은 값을 가지는 행을 연결하여 결과를 생성하는 INNER JOIN 방법
    - **NATURAL JOIN** : JOIN 조건이 = 일 때 동일한 속성이 두번 나타나는데, 이 중 중복된 속성을 제거하여 같은 속성을 한 번만 표기하는 방법
    - **JOIN 속성** : 연결고리가 되는 공통 속성
    - NAUTRE JOIN절을 이용한 표기
    
    ```sql
    SELECT [테이블명1.]속성명, [테이블명2.]속성명
    FROM 테이블명1 NATURAL JOIN 테이블명2
    ```
    
    - JOIN ~ USING절을 이용한 표기
    
    ```sql
    SELECT [테이블명1.]속성명, [테이블명2.]속성명
    FROM 테이블명1 JOIN 테이블명2 USING(속성명)
    ```
    

### OUTER JOIN

- 조건에 맞지 않는 튜플들도 보여주기 위한 조인
- LEFT OUTER JOIN
    - INNER JOIN의 결과를 구한 후 우측 항 릴레이션의 어떤 튜플과도 맞지 않는 좌측 항의 릴레이션에 있는 튜플들에 NULL값을 붙혀서 INNER JOIN의 결과물에 추가
    
    ```sql
    SELECT [테이블명1.]속성명, [테이블명2.]속성명
    FROM 테이블명1 LEFT OUTER JOIN 테이블명2
    ON 테이블명1.속성 = 테이블명2.속성
    ```
    
- RIGHT OUTER JOIN
    - INNER JOIN의 결과를 구한 후 좌측 항 릴레이션의 어떤 튜플과도 맞지 않는 우측항의 릴레이션에 있는 튜플들에 NULL값을 붙여서 INNER JOIN의 결과에 추가함
    
    ```sql
    SELECT [테이블명1.]속성명, [테이블명2.]속성명
    FROM 테이블명1 RIGHT OUTER JOIN 테이블명2
    ON 테이블명1.속성명 = 테이블명2.속성명
    ```
    

## 프로시저

- SQL을 사용하여 작성한 이련의 작업을 저장해두고 호출을 통해 원할 때마다 저장한 작업을 수행하도록 하는 절차형 SQL
- 시스템의 일일마감, 일괄(Batch)작업 등에 주로 사용

```sql
DECLART(필수)
BEGIN(필수)
	- CONTROL
	- SQL
	- EXCEPTION
	- TRANSCATION
END(필수)
```

- DECLART : 프로시저의 명칭, 변수, 인수, 데이터 타입을 정의하는 선언부
- BEGIN / END : 시작과 종료
- CONTROL : 조건문 또는 반복문이 삽입되어 순차적으로 처리
- SQL : DML, DCL이 삽입되어 데이터 관리를 위한 조회, 추가, 수정, 삭제 작업을 수행
- EXCEPTION : BEGIN ~ END 안의 구문 실행 시 예외가 발생하면 처리하는 방법을 정의
- TRANSACTION : 수행된 데이터 작업들을 적용할지 취소할지 결정하는 처리부

### 생성

```sql
CREATE [OR REPLACE] PROCEDURE 프로시저명(파라미터)
[지역변수 선언]
BEGIN
	BODY
END;
```

- 파라미터
    - IN : 프로그램이 프로시저에게 값을 전달할 때 지정
    - OUT : 프로시저가 프로그램에 값을 반환할 때 지정
    - INPUT : IN + OUT
    
    > 예제 : ‘사원번호’를 입력받아 해당 사원의 ‘지급방식’을 “S”로 변경하는 프로시저를 생성하시오
    > 
    
    ```sql
    CREATE OR REPLACE PROCEDURE emp_change_s(i_사원번호 IN INT)
    IS
    BEGIN
    	UPDATE 급여 SET 지급방식 = 'S' WHERE 사원번호 = i_사원번호;
    	EXCEPTION 
    		WHERE PROGRAM_ERROR THEN
    			ROLLBACK;
    	COMMIT;
    END;
    ```
    

### 실행

```sql
EXECUTE 프로시저명;
EXEC 프로시저명;
CALL 프로시저명;
```

### 제거

```sql
DROP PROCEDURE 프로시저명;
```

## 트리거

### 트리거

- 데이터베이스 시스템에서 데이터의 이벤트가 발생할 때 관련 작업이 자동으로 수행되데 하는 절차형 SQL
- 데이터베이스에 저장되며 변경및 무결성 유지, 로그 메시지 출력등의 목적으로 사용
- DCL을 사용할 수 없으며 DCL이 포함된 프로시저나 함수를 호출하는 경우 오류 발생

### 구성

```sql
DECLART(필수)
EVENT(필수)
BEGIN(필수)
	- CONTROL
	- SQL
	- EXCEPTION
	- TRANSCATION
END(필수)
```

### 생성

```sql
CREATE [OR REPLACE] TRIGGER 트리거명 [동작시기][동작옵션] ON 테이블명
REFERENCING [NEW | OLD] AS 테이블명
FOR EACH ROW
[WHERE 조건식]
BEGIN
	BODY
END;
```

- 동작시기
    - AFTER : 테이블이 변경된 후 실행
    - BEFORE : 변경전 실행
- 동작 옵션
    - INSERT, DELETE, UPDATE
- NEW | OLD : 트리거가 적용될 테이블의 별칭을 지정
    - NEW : 추가되거나 수정에 참여할 튜플들의 집합
    - OLD : 수정되거나 삭제 전 대상이 되는 튜플들의 집합

### 제거

```sql
DROP TRIGGER 트리거명;
```

## 사용자 정의 합수

### 사용자 정의 함수

- 프로시저와 유사하게 SQL을 사용하여 일련의 작업을 연속적으로 처리하지만 처리 결과로 단일값만을 반환하는 절차향 SQL
- DML문의 호출에 의해 실행
- RETURN을 통해 단일값을 반환하며 출력 파라미터가 없다

```sql
DECLART(필수)
BEGIN(필수)
	- CONTROL
	- SQL
	- EXCEPTION
	- RETURN(필수)
END(필수)
```

### 생성

```sql
CREATE [OR REPLACE] FUNCTION 함수명(파라미터)
[지역변수]
BEGIN
	함수
	RETURN 반환값;
END;
```

### 제거

```sql
DROP FUNCTION 함수
```

## 제어문

### 제어문

- 위에서 아래로 차례대로 실행되는 절차형 SQL의 진행 순서를 변경하기 위해 사용하는 명령문
- IF, LOOF등이 존재

### LOOP

```sql
LOOP
	실행할 문장
	EXIT WHEN 조건;
END LOOP;
```

## 커서(Cursor)

### 커서

- 쿼리문의 처리 결과가 저장되어 이쓴 메모리 공간을 가리키는 포인터
- 수행은 열기(Open), 패치(Fetch), 닫기(Close)의 세단계로 진행

### 묵시적 커서

- DBMS에 의해 내부에서 자동으로 생성되어 사용되는 커서

### 명시적 커서

- 사용자가 직접 정의해서 사용하는 커서

### 속성

- SQL%FOUND : 결과로 패치된 퓨플 수가 1개 이상이면 TRUE
- SQL%NOTFOUND : 결과로 패치된 튜플 수가 0개면 TRUE
- SQL%ROWCOUNT : 결과로 패치된 튜플 수를 반환
- SQL%ISOPEN : 커서가 OPEN상태면 TRUE, 묵시적 커서는 자동으로 생성 된 후 자동으로 닫히기 때문에 항상 FALSE

### DBMS 접속 기술의 종류

### DMBS 접속 기술

- DBMS에 접근하기 위해 사용하는 API 또는 API의 사용을 편리하게 도와주는 프레임워크

### DBMS 접속 기술

- JDBC
    - Java 언어로 다양한 종류의 데이터베이스에 접속할 때 사용하는 표준 API
    - 1997년 2월 썬 마이크로시스템에서 출시
    - 접속하려는 DBMS에 대한 드라이버 필요
- ODBC
    - 개발 언어에 관계없이 데이터베이스에 접근하기 위한 표준 개방형 API
    - 1992년 9월 마이크로소프트에서 출시
    - 접속하려는 DBMS의 인터페이스에 관계없이 ODBC문장을 사용하여 접속 가능
- MyBatis
    - JDBC 코드를 단순화하여 사용할 수 있는 SQL Mapping 기반 오픈 소스 접속 프레임워크
    - SQL문장을 분리하여 XML파일을 만들고 Mapping을 통해 SQL을 실행

### 동적 SQL

- 다양한 조건에 따라 SQL구문을 동적으로 변경하여 처리할 수 있는 SQL처리 방식
- 사용자로부터 SQL문의 일부 또는 전부를 입력받아 실행할 수 있다.

## SQL 테스트

### SQL테스트

- SQL이 작성 의도에 맞게 원하는 기능을 수행하는지 검증

### 단문 SQL 테스트

- DDL, DML, DCL이 포함되어 있는 SQL과 TCL을 테스트하는 것으로 직접 실행하여 결과 확인

```sql
DESC [개체명]
```

### 절차형 SQL 테스트

- 디버깅을 통해 기능의 적합성 여부를 검증하고, 실행을 통해 결과를 확인

```sql
SHOW ERRORS;
```

## ORM(Object-Relational Mapping)

### ORM

- 객체 지향 프로그래밍의 객체와 관계형 데이터베이스의 데이터를 연결하는 기술
- 객체 지향 프로그래밍에서 사용하 수 있는 가상의 객체 지향 데이터베이스를 만들어 코드와 데이터를 연결
- ORM으로 생성된 가상의 객체지향 데이터베이스는 프로그래밍 코드 또는 데이터베이스와 독립적이므로 재사용 및 유지보수가 용이하다

### 프레임워크

- Java : JPA, Hibernate, EclipseLink, DataNuceus, Ebean 등
- C++ : ODB, QxOrm 등
- Python : Django, SQLAlchemy, Storm 등
- .NET : NHibernate, DatabaseObjects, Dapper 등
- PHP : Doctrine, Propel, RedBean 등

### 쿼리 성능 최적화 용어

### APM(Application Performance Management/Monitoring)

- 애플리케이션의 성능 관리를 위해 접속자, 자원 현황, 트랜잭션 수행 내역, 장애 진단 등 다양한 모니터링을 제공하는 도구

### 옵티마이저(Optimizer)

- 작성된 SQL이 가장 효율적으로 수행되도록 최적의 경로를 찾아주는 모듈
- RBO(Rule Based Optimizer) : DBA가 사전에 정의해둔 규칙에 의거하여 경로를 찾는 규칙 기반 옵티마이저
- CBO(Cost Based Optimizer) : 입,출력 속도, cpu사용량, 블록 개수, 개체의 속성, 튜플 개수 등을 종합하여 각 DBMS마다 고유의 알고리즘에 따라 산출되는 ‘비용’으로 최적의 경로를 찾는 비용 기반 옵티마이저

### 실행 계획(Execution Plan)

- DBMS의 옵티마이저가 수립한 SQL코드의 실행 절차와 방법

### 힌트(HINT)

- SQL문에 추가되어 테이블 접근 순서를 변경하거나, 인덱스 사용을 강제하는 등의 실행 계획에 영향을 줄 수 있는 문장

### IOT(Index-Organized Table)

인덱스 안에 테이블 데이터를 직접 삽입하여 저장함으로써 주소를 얻는 과정을 생략하여 빠른 조회가 가능하도록 구성한 테이블