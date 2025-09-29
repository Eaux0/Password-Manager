import React from "react";

interface PasswordHolderTemplateProps {
  index: number | null;
  title?: string;
  description?: string;
  username?: string;
  password?: string;
  editMode?: boolean;
  showPassword?: boolean;
  setSelectedPassword?: (index: number | null) => void;
  setEditMode?: (editMode: boolean) => void;
  setShowPassword?: (show: boolean) => void;
  setUsername?: (username: string) => void;
  setPassword?: (password: string) => void;
  copyToClipboard?: (value: string, field: "username" | "password") => void;
  copiedField?: "username" | "password" | null;
  handleInput?: (
    e: React.FormEvent<HTMLHeadingElement | HTMLParagraphElement>,
    type: "title" | "description"
  ) => void;
  buttonStyle?: (color: "gray" | "blue" | "red") => React.CSSProperties;
}

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
  const deletePassword = (index: number | null) => {
    // Logic to delete the password
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
