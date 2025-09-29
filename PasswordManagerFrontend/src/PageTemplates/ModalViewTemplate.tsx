import React from "react";
import PasswordHolderTemplate from "../DataHolderTemplate/PasswordHolderTemplate";
import AddpasswordHoldertemplate from "../DataHolderTemplate/AddpasswordHoldertemplate";
import AddGroupHolderTemplate from "../DataHolderTemplate/AddGroupHolderTemplate";

interface ModalViewTemplateProps {
  index: number | null;
  modalType: string;
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
  setAddPasswordModalShow?: (show: boolean) => void;
  setAddGroupModalShow?: (show: boolean) => void;
}

const ModalViewTemplate = ({
  index,
  modalType,
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
  setAddPasswordModalShow,
  setAddGroupModalShow,
}: ModalViewTemplateProps) => {
  const buttonStyle = (color: "gray" | "blue" | "red") => {
    const colors = {
      gray: "#6c757d",
      blue: "#007bff",
      red: "#dc3545",
    };

    return {
      padding: "8px 14px",
      backgroundColor: colors[color],
      border: "none",
      borderRadius: "4px",
      cursor: "pointer",
      fontSize: "14px",
    };
  };
  return (
    <>
      <div
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          width: "100vw",
          height: "100vh",
          backgroundColor: "rgba(0,0,0,0.5)",
          backdropFilter: "blur(5px)",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          zIndex: 1000,
        }}
        onClick={() => setSelectedPassword?.(null)}
      >
        <div
          onClick={(e) => e.stopPropagation()}
          style={{
            borderRadius: "10px",
            padding: "24px",
            minWidth: "400px",
            boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
          }}
        >
          {modalType === "showPassword" && (
            <PasswordHolderTemplate
              index={index}
              title={title}
              description={description}
              username={username}
              password={password}
              editMode={editMode}
              showPassword={showPassword}
              setSelectedPassword={setSelectedPassword}
              setEditMode={setEditMode}
              setShowPassword={setShowPassword}
              setUsername={setUsername}
              setPassword={setPassword}
              copyToClipboard={copyToClipboard}
              copiedField={copiedField}
              handleInput={handleInput}
              buttonStyle={buttonStyle}
            />
          )}
          {modalType === "addPassword" && (
            <AddpasswordHoldertemplate
              setUsername={setUsername}
              setPassword={setPassword}
              handleInput={handleInput}
              buttonStyle={buttonStyle}
              setAddPasswordModalShow={setAddPasswordModalShow}
            />
          )}
          {modalType === "addGroup" && (
            <AddGroupHolderTemplate
              buttonStyle={buttonStyle}
              setAddGroupModalShow={setAddGroupModalShow}
            />
          )}
        </div>
      </div>
    </>
  );
};

export default ModalViewTemplate;
