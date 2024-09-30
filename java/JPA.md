# JPA

- JpaRepository를 상속하는 인터페이스에 메서드 이름만 적어놓으면 알아서 다 처리(구현체 생성, 쿼리문 구현 등)해주는 좋은 ORM(Object-Relational Mapping)

### `@ColumnDefault("value")`  : defalut 값을 지정해 줄 수 있다.

### `@Query(”쿼리문”)`  :  쿼리문을 직접 작성해줄 수 있다

- JPQL을 사용

```java
@Query("SELECT u FROM User u WHERE u.id = :id")
Optional<User> findByCustomeId(@Param("id") Long id);
```

- INSERT, UPDATE, DELETE 과 같이 SELECT가 아닌 DML들은 @Modifying과 함께 써야한다. (추가로 @Transaction을 쓸 수 있다)

```java
@Transactional
@Modifying
@Query("update User u set u.name = :name where u.id = :id")
void updateName(@Param("id") Long id , @Param("name") String name);
```

### 메서드

- 저장: `save(entity)`
- 조회: `findById(id)`, `findAll()`
- 삭제: `delete(entity)`, `deleteById(id)`
- 개수 조회: `count()`

```java
// service
public User createUser(User user) {
    return userRepository.save(user);
}

public User getUserById(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
}

public List<User> getAllUsers() {
    return userRepository.findAll();
}

public User updateUser(User user) {
    return userRepository.save(user);
}

public void deleteUser(Long id) {
    userRepository.deleteById(id);
}

public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
}

public List<User> searchUsersByUsername(String username) {
    return userRepository.findByUsernameContaining(username);
}
```

### optional

- null 체크를 명시적으로 하지 않아도 됩니다.
- 메소드의 반환 타입만으로도 값이 없을 수 있다는 것을 명확히 알 수 있습니다.
- 값이 없는 경우의 처리를 강제하여 개발자의 실수를 줄일 수 있습니다.
- 함수형 프로그래밍 스타일의 코드를 작성할 수 있습니다.
- 메소드
    - **`of(value)`**: 주어진 값으로 **`Optional`** 객체를 생성한다.
        - 값이 **`null`**이면 **`NullPointerException`**이 발생한다
        
        ```java
        Optional<String> nonNullableOptional = Optional.of("Hello");
        ```
        
    - **`ofNullable(value)`**: 주어진 값으로 **`Optional`** 객체를 생성한다
        - 값이 **`null`**이어도 예외가 발생하지 않는다
        
        ```java
        Optional<String> nullableOptional = Optional.ofNullable(null);
        ```
        
    - **`isPresent()`**:
        - 값이 존재하는지 여부를 판단한다
        - 값이 존재하면 **`true`**, 그렇지 않으면 **`false`**를 반환한다.
        
        ```java
        Optional<String> optional = Optional.of("Hello");
        if (optional.isPresent()) {
          // 값이 존재하는 경우 처리
        }
        ```
        
    - **`ifPresent(consumer)`**:
        - 값이 존재하는 경우에만 주어진 동작을 수행한다
        - **`Consumer`** 함수형 인터페이스를 인자로 받는다
        
        ```java
        Optional<String> optional = Optional.of("Hello");
        optional.ifPresent(value -> System.out.println("Value : " + value));
        // Value : Hello
        ```
        
    - **`get()`**:
        - 값이 존재하면 해당 값을 반환하고, 그렇지 않으면 **`NoSuchElementException`**이 발생한다
        - 일반적으로 **`isPresent()`**를 함께 사용하는 것이 권장된다
    
    ```java
    Optional<String> optional = Optional.of("Hello");
    String value = optional.get();
    // value = "Hello"
    ```
    
    - **`orElse(other)`**: 값이 존재하면 해당 값을 반환하고, 그렇지 않으면 주어진 기본값을 반환한다
    - **`orElseGet(supplier)`**: 값이 존재하면 해당 값을 반환하고, 그렇지 않으면 **`Supplier`**로 제공된 값을 반환한다
    - **`orElseThrow(exceptionSupplier)`**: 값이 존재하면 해당 값을 반환하고, 그렇지 않으면 주어진 예외를 발생시킨다
    
    ```java
    Optional<String> optional = Optional.of("Hello");
    String result1 = optional.orElse("Default");
    String result2 = optional.orElseGet(() -> getDefaultValue()); // 값이 없더라도 기본 값을 넣어주는 것이다.
    String result3 = optional.orElseThrow(() -> new NoSuchElementException("No value present"));
    ```
    
    - **`filter(predicate)`**
        - 값이 주어진 조건(predicate)과 일치하면 현재 **`Optional`**을 반환하고, 그렇지 않으면 빈 **`Optional`**을 반환한다
            
            ```java
            Optional<String> optional = Optional.of("Hello");
            Optional<String> filteredOptional = optional.filter(value -> value.length() > 5);
            ```
            
    - **`map(mapper)`**: 값이 존재하면 주어진 매핑 함수를 적용하고, 결과를 포함하는 **`Optional`**을 반환한다
        
        ```java
        @Transactional
        public Optional<User> updateUser(Long id, User userDetails) {
            return userRepository.findById(id)
            // 유저를 찾고 있으면 밑에를 실행해라
            // 유저를 찾고 유저가 있으면 정보 수정하는 함
              .map(user -> {
                  user.setUsername(userDetails.getUsername());
                  user.setEmail(userDetails.getEmail());
                  return userRepository.save(user);
              });
        }
        ```
        

## page와 Slice

- 데이터가 많을 때 페이징처리와 정렬을 통해 필요한 부분만 조회할 수 있다
- `Pageable`: 페이징 처리를 쉽게 지원해주는 인터페이스

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
}
```

### **PageRequest 생성**

아래와 같이 정적 팩토리 메소드를 사용하여 **`PageRequest`** 를 생성할 수 있다.

```java
PageRequest page = PageRequest.of(0, 10);
```

첫번째 파라미터는 페이지 순서이고, 두번째 파라미터는 단일 페이지의 크기를 의미

### **조회 결과 정렬**

아래와 같이 **`Sort`** 클래스 혹은 **`Sort`** 의 내부 enum 클래스인 **`Direction`** 을 사용하여 정렬을 설정할수도 있다. 정렬 방향을 지정하는 방법은 아래와 같이 다양하다.

```java
PageRequest.of(0, 10, Sort.by("price").descending());
PageRequest.of(0, 10, Sort.by(Direction.DESC, "price"));
PageRequest.of(0, 10, Sort.by(Order.desc("price")));
PageRequest.of(0, 10, Direction.DESC, "price");
```

**`PageRequest.of()`** 의 세번째 인자로 **`Sort`** 혹은 **`Direction`** 을 전달하면 된다. 전달된 **`"price"`** 는 정렬 기준이 되는 컬럼 이름이다. 위 코드는 모두 내림차순으로 정렬된 결과를 받아올 때 사용한다.

### Slice

**`Slice`** 는 별도로 **count 쿼리를 실행하지 않는다**. 따라서 전체 페이지의 개수와 전체 엔티티의 개수를 알 수 없지만, **불필요한 count 쿼리로 인한 성능 낭비는 발생하지 않는다**.

최근 모바일 UI에서 많이 사용되는 **무한 스크롤**을 구현할 때에는 전체 페이지 개수가 굳이 필요하지 않다. 다음 페이지의 유무만 판단하고, 있다면 다음 페이지를 불러오기만 하면 되기 때문이다. 이런 기능을 구현할 때 **`Slice`** 를 사용하면 적합하다.

### Page

**`Page`** 는 **`Slice`** 와 다르게 **count 쿼리를 실행**하여, 전체 데이터 개수와 전체 페이지 개수를 계산할 수 있다. 따라서 우리가 흔히 볼 수 있는 **게시판의 페이지네이션 UI** 등을 구현할 때 적합하다.

### Default

- JpaRepository를 사용하여 메서드를 생성할 때, 이름이 너무 길어져서 사용하기에 가독성이 좋지 않은 경우가 있다. 이 경우, 인터페이스 내에 `defaultMethod를` 통해 짧은 이름을 지정해 값을 return 하게끔 method를 생성할 수 있다.
- Java 8 부터 인터페이스에 `defaultMethod` 라는 기능이 생겼는데, 기존의 인터페이스 개념과 달리 인터페이스 내부에 로직이 포함된 메서드를 작성할 수 있다.

```java
default List<Tour> getByLocationAndDate(String tourLocation, LocalDate startDate, LocalDate endDate) {
    return findAllByTourLocationAndStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByIdDesc(tourLocation, startDate, endDate);
}
```

### ManyToOne, OneToMany

- 두개의 엔티티에서 양방향으로 설정해 줘야한다.
- 엔티티의 입장에서 생각해야한다

```yaml

```

### Fetch Join

- 사용 이유
1. **N+1 문제 해결**: 연관 엔티티를 조회할 때 발생하는 추가 쿼리를 방지합니다.
2. **성능 최적화**: 필요한 데이터를 한 번의 쿼리로 가져와 성능을 향상시킵니다.
- 일반 조인과의 차이
    - 일반 조인: 연관된 엔티티를 함께 선택하지만, 실제 데이터는 즉시 로딩하지 않습니다.
    - Fetch 조인: 연관된 엔티티의 데이터를 즉시 함께 로딩합니다.

## @Vaild

- @notNull : null만 허용 하지 않음, “” “ “은 허용
- @notBlank : null과 “”을 허용하지 않음, “ “은 허용
- @notEmpty : 모두 허용하지 않