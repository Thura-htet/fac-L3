export const initialTodos = [
  { id: 1, content: "Acheter du pain", done: false },
  { id: 2, content: "Comprendre React", done: true },
  { id: 3, content: "Faire du fonctionnel", done: false },
];

export function todosReducer(model, action) {
  switch (action.type) {
    case "todoToggled":
      return model.map(todo =>
        todo.id === action.id
          ? { ...todo, done: !todo.done }
          : todo
      );

    case "todoDeleted":
      return model.filter(todo =>
        todo.id !== action.id
      );

    case "allCompleted":
      return model.map(todo => ({
        ...todo,
        done: true,a
      }));

    default:
      return model;
  }
}