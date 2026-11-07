import { View, Text, Button, Switch, StyleSheet } from "react-native";

export default function TodoItem({ todo, dispatch }) {
  return (
    <View style={styles.container}>
      <Switch
        value={todo.done}
        onValueChange={() =>
          dispatch({
            type: "todoToggled",
            id: todo.id,
          })
        }
      />

      <Text style={styles.text}>
        {todo.content}
      </Text>

      <Button
        title="X"
        onPress={() =>
          dispatch({
            type: "todoDeleted",
            id: todo.id,
          })
        }
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    alignItems: "center",
    gap: 10,
    marginBottom: 10,
  },
  text: {
    flex: 1,
    fontSize: 18,
  },
});