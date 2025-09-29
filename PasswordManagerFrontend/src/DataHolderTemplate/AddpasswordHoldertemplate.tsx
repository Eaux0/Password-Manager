import React, { useState } from "react";

interface AddpasswordHoldertemplateProps {
  buttonStyle?: (color: "gray" | "blue" | "red") => React.CSSProperties;
  setAddPasswordModalShow?: (show: boolean) => void;
}

const AddpasswordHoldertemplate = ({
  buttonStyle,
  setAddPasswordModalShow,
}: AddpasswordHoldertemplateProps) => {
  const [showPassword, setShowPassword] = useState(false);
  const userGroups = ["a", "b", "c", "d"];

  const [passwordName, setPasswordName] = useState("");
  const [passwordDescription, setPasswordDescription] = useState("");

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [selectedGroup, setSelectedGroup] = useState("");

  const savePassword = () => {
    if (passwordName.trim() === "") {
      alert("Password name cannot be empty");
      return;
    }
    if (passwordName === "Password Identifier") {
      alert("Enter a valid password name");
      return;
    }
    if (passwordDescription === "Password Description") {
      alert("Enter a valid password description");
      return;
    }
    if (username.trim() === "") {
      alert("Username cannot be empty");
      return;
    }
    if (password.trim() === "") {
      alert("Password cannot be empty");
      return;
    }
    if (selectedGroup.trim() === "") {
      alert("Select a password group");
      return;
    }
    console.log("Password saved!");
    console.log({ username, password, selectedGroup });
    setAddPasswordModalShow?.(false);
  };

  return (
    <div>
      <h2
        style={{ marginBottom: "8px" }}
        contentEditable
        suppressContentEditableWarning
        onInput={(e) => setPasswordName(e.currentTarget.textContent || "")}
      >
        {"Password Identifier"}
      </h2>
      <p
        style={{ marginBottom: "20px", color: "#555" }}
        contentEditable
        suppressContentEditableWarning
        onInput={(e) =>
          setPasswordDescription(e.currentTarget.textContent || "")
        }
      >
        {"Password Description"}
      </p>

      {/* Username */}
      <div style={{ position: "relative", marginBottom: "16px" }}>
        <label
          style={{ display: "block", fontWeight: 500, marginBottom: "4px" }}
        >
          Username
        </label>
        <input
          type="text"
          //   value={username}
          readOnly={false}
          onChange={(e) => setUsername?.(e.target.value)}
          style={{
            width: "100%",
            padding: "10px 36px 10px 10px",
            fontSize: "14px",
            border: "1px solid #ccc",
            borderRadius: "5px",
          }}
        />
      </div>

      {/* Password */}
      <div style={{ position: "relative", marginBottom: "16px" }}>
        <label
          style={{ display: "block", fontWeight: 500, marginBottom: "4px" }}
        >
          Password
        </label>
        <input
          type={showPassword ? "text" : "password"}
          //   value={password}
          readOnly={false}
          onChange={(e) => setPassword?.(e.target.value)}
          style={{
            width: "100%",
            padding: "10px 70px 10px 10px",
            fontSize: "14px",
            border: "1px solid #ccc",
            borderRadius: "5px",
          }}
        />
        <span
          title="Toggle Password Visibility"
          onClick={() => setShowPassword?.(!showPassword)}
          style={{
            position: "absolute",
            right: "10px",
            top: "36px",
            cursor: "pointer",
            fontSize: "16px",
          }}
        >
          {showPassword ? "🙈" : "👁️"}
        </span>
      </div>

      <div style={{ position: "relative", marginBottom: "16px" }}>
        <label
          style={{ display: "block", fontWeight: 500, marginBottom: "4px" }}
        >
          Password Group
        </label>
        <select
          //   value={username}
          onChange={(e) => setSelectedGroup?.(e.target.value)}
          style={{
            width: "100%",
            padding: "10px 36px 10px 10px",
            fontSize: "14px",
            border: "1px solid #ccc",
            borderRadius: "5px",
          }}
        >
          <option value="" disabled selected>
            Select a group
          </option>
          {userGroups.map((group) => (
            <option key={group} value={group}>
              {group}
            </option>
          ))}
        </select>
      </div>

      {/* Action Buttons */}
      <div
        style={{
          marginTop: "24px",
          display: "flex",
          gap: "10px",
          flexWrap: "wrap",
        }}
      >
        <button
          onClick={() => setAddPasswordModalShow?.(false)}
          style={buttonStyle?.("gray")}
        >
          Close
        </button>
        <button onClick={() => savePassword()} style={buttonStyle?.("blue")}>
          Save
        </button>
      </div>
    </div>
  );
};

export default AddpasswordHoldertemplate;
