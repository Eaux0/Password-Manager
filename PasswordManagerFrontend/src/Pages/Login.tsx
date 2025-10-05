import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import LoginView from "../PageTemplates/LoginView";
import SignUpView from "../PageTemplates/SignUpView";
import generateAesKey from "../DataProcessing/RestApis.ts";

interface LoginProps {
  setToken: (token: string) => void;
  setSessionId: (sessionId: number) => void;
}

const Login = ({ setToken, setSessionId }: LoginProps) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [signUpMode, setSignUpMode] = useState(false);
  const navigate = useNavigate();

  const handleSubmitOnSignIn = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log("Username:", username);
    console.log("Password:", password);

    try {
      const response = await axios.post("https://localhost:8081/api/login", {
        username: "yourUsername",
        password: "yourPassword",
      });

      console.log("Response:", response.data);
      setSessionId(response.data.sessionId);
      return generateAesKey();
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Axios error:", error.response?.data || error.message);
      } else {
        console.error("Unexpected error:", error);
      }
    }
    const tempToken = "1234567890abcdef";
    setToken(tempToken);
    navigate("/");
  };

  const handleSubmitOnSignUp = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log("Username:", username);
    console.log("Password:", password);

    try {
      const response = await axios.post("https://localhost:8081/api/login", {
        username: "yourUsername",
        password: "yourPassword",
      });

      console.log("Response:", response.data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Axios error:", error.response?.data || error.message);
      } else {
        console.error("Unexpected error:", error);
      }
    }
    const tempToken = "1234567890abcdef";
    setToken(tempToken);
    navigate("/");
  };

  return (
    <>
      {!signUpMode && (
        <LoginView
          username={username}
          setUsername={setUsername}
          password={password}
          setPassword={setPassword}
          handleSubmitOnSignIn={handleSubmitOnSignIn}
          setSignUpMode={setSignUpMode}
        />
      )}
      {signUpMode && (
        <SignUpView
          handleSubmitOnSignUp={handleSubmitOnSignUp}
          username={username}
          setUsername={setUsername}
          password={password}
          setPassword={setPassword}
        />
      )}
    </>
  );
};

export default Login;
