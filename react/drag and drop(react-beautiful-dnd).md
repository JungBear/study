# drag and drop(react-beautiful-dnd)

- drag and drop을 쉽게 해주는 react 라이브러리

```bash
npm install react-beautiful-dnd
```

## React Beautiful DnD 종합 가이드

## 1. 개요

React Beautiful DnD는 React 애플리케이션에서 드래그 앤 드롭 인터페이스를 쉽게 만들 수 있게 해주는 라이브러리입니다. 자연스러운 움직임, 접근성, 그리고 강력한 API를 제공합니다.

## 2. 주요 개념

### DragDropContext

- 전체 드래그 앤 드롭 기능을 관리하는 최상위 컴포넌트입니다.
- 드래그 앤 드롭 이벤트를 처리하는 콜백 함수를 받습니다.

### Droppable

- 아이템을 드롭할 수 있는 영역을 정의합니다.
- 고유한 `droppableId`가 필요합니다.

### Draggable

- 드래그할 수 있는 개별 아이템을 나타냅니다.
- 고유한 `draggableId`와 `index`가 필요합니다.

## 3. 주요 Props 및 콜백

### DragDropContext

- `onDragEnd`: 드래그가 끝났을 때 호출되는 필수 콜백 함수입니다.
- `onDragStart`, `onDragUpdate`: 선택적 콜백 함수들입니다.

### Droppable

- `droppableId`: 해당 드롭 영역의 고유 식별자입니다.
- `type`: 드롭 영역의 타입을 지정합니다. 같은 타입의 Droppable 간에만 드래그가 가능합니다.

### Draggable

- `draggableId`: 드래그 가능한 아이템의 고유 식별자입니다.
- `index`: 현재 Droppable 내에서의 아이템 순서를 나타냅니다.

## 4. 기본 사용 예시

```jsx
jsx
Copy
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';

function App() {
  const onDragEnd = (result) => {
// 드래그 앤 드롭 결과 처리 로직
  };

  return (
    <DragDropContext onDragEnd={onDragEnd}>
      <Droppable droppableId="list">
        {(provided) => (
          <ul {...provided.droppableProps} ref={provided.innerRef}>
            {items.map((item, index) => (
              <Draggable key={item.id} draggableId={item.id} index={index}>
                {(provided) => (
                  <li
                    ref={provided.innerRef}
                    {...provided.draggableProps}
                    {...provided.dragHandleProps}
                  >
                    {item.content}
                  </li>
                )}
              </Draggable>
            ))}
            {provided.placeholder}
          </ul>
        )}
      </Droppable>
    </DragDropContext>
  );
}

```

## 5. 고급 기능

### 멀티 드래그

여러 아이템을 동시에 드래그할 수 있습니다.

### 드래그 핸들

아이템의 특정 부분만 드래그 가능하게 만들 수 있습니다.

### 조건부 드래그

특정 조건에 따라 드래그를 활성화/비활성화할 수 있습니다.

### 가상 리스트

대량의 데이터를 효율적으로 렌더링하기 위해 가상 리스트와 함께 사용할 수 있습니다.

## 6. 성능 최적화

- `shouldComponentUpdate`를 사용하여 불필요한 리렌더링을 방지합니다.
- `React.memo`를 활용하여 컴포넌트를 최적화합니다.

## 7. 접근성

- 키보드 네비게이션을 지원합니다.
- 스크린 리더와 호환됩니다.

## 8. 스타일링

- CSS-in-JS 라이브러리와 함께 사용할 수 있습니다.
- 드래그 중인 아이템의 스타일을 커스터마이즈할 수 있습니다.

## 9. 주의사항

- 깊은 중첩 구조에서는 성능 이슈가 발생할 수 있습니다.
- 복잡한 레이아웃에서는 추가적인 최적화가 필요할 수 있습니다.

React Beautiful DnD는 강력하고 유연한 드래그 앤 드롭 솔루션을 제공하며, 다양한 사용 사례에 적용할 수 있습니다.