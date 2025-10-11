import type { PasswordHolderTemplateProps } from "../DataProcessing/Props";
import axios from "axios";
import useSessionStore from "../DataProcessing/sessionStore";

const PasswordHolderTemplate = ({
  index,
  title,
  description,
  username,
  password,
  editMode,
  showPassword,
  setSelectedPassword,
  setEditMode,
  setShowPassword,
  setUsername,
  setPassword,
  copyToClipboard,
  copiedField,
  handleInput,
  buttonStyle,
}: PasswordHolderTemplateProps) => {
  const { sessionId } = useSessionStore();

  const deletePassword = async (index: number | null) => {
    try {
      const response = await axios.post(
        "https://localhost:8080/api/" + sessionId + "/passwords/" + index
      );

      console.log("Response:", response.data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Axios error:", error.response?.data || error.message);
      } else {
        console.error("Unexpected error:", error);
      }
    }

    console.log("Password deleted at index:", index);
    setSelectedPassword?.(null);
  };

  return (
    <div>
      <h2
        style={{ marginBottom: "8px" }}
        contentEditable
        suppressContentEditableWarning
        onInput={(e) => handleInput?.(e, "title")}
      >
        {title}
      </h2>
      <p
        style={{ marginBottom: "20px", color: "#555" }}
        contentEditable
        suppressContentEditableWarning
        onInput={(e) => handleInput?.(e, "description")}
      >
        {description}
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
          value={username}
          readOnly={editMode}
          onChange={(e) => setUsername?.(e.target.value)}
          style={{
            width: "100%",
            padding: "10px 36px 10px 10px",
            fontSize: "14px",
            border: "1px solid #ccc",
            borderRadius: "5px",
          }}
        />
        <span
          title="Copy Username"
          onClick={() => copyToClipboard?.(username ?? "", "username")}
          style={{
            position: "absolute",
            right: "10px",
            top: "36px",
            cursor: "pointer",
            fontSize: "16px",
          }}
        >
          📋
        </span>
        {copiedField === "username" && (
          <span style={{ color: "green", fontSize: "12px", marginLeft: "8px" }}>
            Copied!
          </span>
        )}
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
          value={password}
          readOnly={editMode}
          onChange={(e) => setPassword?.(e.target.value)}
          style={{
            width: "100%",
            padding: "10px 70px 10px 10px",
            fontSize: "14px",
            border: "1px solid #ccc",
            borderRadius: "5px",
          }}
        />
        {/* Toggle show/hide password */}
        <span
          title="Toggle Password Visibility"
          onClick={() => setShowPassword?.(!showPassword)}
          style={{
            position: "absolute",
            right: "40px",
            top: "36px",
            cursor: "pointer",
            fontSize: "16px",
          }}
        >
          {showPassword ? "🙈" : "👁️"}
        </span>

        {/* Copy password */}
        <span
          title="Copy Password"
          onClick={() => copyToClipboard?.(password ?? "", "password")}
          style={{
            position: "absolute",
            right: "10px",
            top: "36px",
            cursor: "pointer",
            fontSize: "16px",
          }}
        >
          📋
        </span>
        {copiedField === "password" && (
          <span style={{ color: "green", fontSize: "12px", marginLeft: "8px" }}>
            Copied!
          </span>
        )}
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
          onClick={() => setSelectedPassword?.(null)}
          style={buttonStyle?.("gray")}
        >
          Close
        </button>
        <button
          onClick={() => setEditMode?.(!editMode)}
          style={buttonStyle?.("blue")}
        >
          {editMode ? "Edit" : "Save"}
        </button>
        <button
          style={buttonStyle?.("red")}
          onClick={() => deletePassword(index)}
        >
          Delete
        </button>
      </div>
    </div>
  );
};

export default PasswordHolderTemplate;
