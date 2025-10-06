import type { LoginViewProps } from "../DataProcessing/Props";

const LoginView = ({
  username,
  setUsername,
  password,
  setPassword,
  handleSubmitOnSignIn,
  setSignUpMode,
}: LoginViewProps) => {
  return (
    <div className="logIn-container">
      <div className="form-container">
        <h1>Login</h1>
        <form onSubmit={handleSubmitOnSignIn}>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Username"
            required
          />
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Password"
            required
          />
          <button className="form-button" type="submit">
            Login
          </button>
        </form>
        <button
          className="secondary form-button"
          onClick={() => setSignUpMode(true)}
        >
          Don't have an account? Sign Up
        </button>
      </div>
    </div>
  );
};

export default LoginView;
