import React from "react";

interface SignUpViewProps {
  handleSubmitOnSignUp: (e: React.FormEvent) => void;
}

const SignUpView = ({ handleSubmitOnSignUp }: SignUpViewProps) => {
  return (
    <div className="logIn-container">
      <div className="form-container">
        <h1>Sign Up</h1>
        <form onSubmit={handleSubmitOnSignUp}>
          <input type="text" placeholder="Username" required />
          <input type="text" placeholder="Full Name" required />
          <input type="password" placeholder="Password" required />
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
