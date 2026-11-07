import { NavigationContainer } from "@react-navigation/native";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

import HomeScreen from "./screens/HomeScreen";
import TodosScreen from "./screens/TodosScreen";
import AboutScreen from "./screens/AboutScreen";
import SignInScreen from "./screens/SignInScreen";
import SignUpScreen from "./screens/SignUpScreen";


const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator>
        <Tab.Screen name="Home" component={HomeScreen} />
        <Tab.Screen name="Todos" component={TodosScreen} />
        <Tab.Screen name="About" component={AboutScreen} />
        <Tab.Screen name="SignIn" component={SignInScreen} />
        <Tab.Screen name="Signup" component={SignUpScreen} />

      </Tab.Navigator>
    </NavigationContainer>
  );
}