import type { SignUpViewProps } from "../DataProcessing/Props";

const SignUpView = ({
  handleSubmitOnSignUp,
  username,
  setUsername,
  password,
  setPassword,
}: SignUpViewProps) => {
  return (
    <div className="logIn-container">
      <div className="form-container">
        <h1>Sign Up</h1>
        <form onSubmit={handleSubmitOnSignUp}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            required
            onChange={(e) => setUsername(e.target.value)}
          />
          <input type="text" placeholder="Full Name" required />
          <input
            type="password"
            value={password}
            placeholder="Password"
            required
            onChange={(e) => setPassword(e.target.value)}
          />
          <input type="password" placeholder="Confirm Password" required />
          <button className="form-button" type="submit">
            Sign Up
          </button>
        </form>
        <button
          className="secondary form-button"
          onClick={() => window.location.reload()}
        >
          Back to Login
        </button>
      </div>
    </div>
  );
};

export default SignUpView;
