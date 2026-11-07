import {useState} from 'react';
import {StyleSheet, TextInput, Button} from 'react-native';
import {SafeAreaView, SafeAreaProvider} from 'react-native-safe-area-context';

const TextInputExample = () => {
  const [username, onChangeUsername] = useState('');
  const [password, onChangePassword] = useState('');

  const verify = () => {
    console.log(username);
  }

  return (
    <SafeAreaProvider>
      <SafeAreaView>
        <TextInput
          style={styles.input}
          onChangeText={onChangeUsername}
          placeholder="Please enter your username"
          value={username}
        />

        <TextInput
          style={styles.input}
          onChangeText={onChangePassword}
          value={password}
          placeholder="Please enter your password"
          secureTextEntry={true}
        />
        
        <Button
          style={styles.input}
          title="Sign In"
          onPress={verify}
        />
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

const styles = StyleSheet.create({
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
  },
});

export default TextInputExample;