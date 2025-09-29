import { useState } from "react";
import { useNavigate } from "react-router-dom";
import LoginView from "../PageTemplates/LoginView";
import SignUpView from "../PageTemplates/SignUpView";

interface LoginProps {
  setToken: (token: string) => void;
}

const Login = ({ setToken }: LoginProps) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [signUpMode, setSignUpMode] = useState(false);
  const navigate = useNavigate();

  const handleSubmitOnSignIn = (e: React.FormEvent) => {
    e.preventDefault();

    const tempToken = "1234567890abcdef";

    setToken(tempToken);
    navigate("/");
  };

  const handleSubmitOnSignUp = (e: React.FormEvent) => {
    e.preventDefault();

    const tempToken = "fedcba0987654321";

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
      {signUpMode && <SignUpView handleSubmitOnSignUp={handleSubmitOnSignUp} />}
    </>
  );
};

export default Login;
