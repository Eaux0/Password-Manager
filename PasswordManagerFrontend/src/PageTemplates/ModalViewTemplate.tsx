import PasswordHolderTemplate from "../DataHolderTemplate/PasswordHolderTemplate";
import AddpasswordHoldertemplate from "../DataHolderTemplate/AddpasswordHoldertemplate";
import AddGroupHolderTemplate from "../DataHolderTemplate/AddGroupHolderTemplate";
import type { ModalViewTemplateProps } from "../DataProcessing/Props";

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
  sessionId,
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
              sessionId={sessionId}
            />
          )}
          {modalType === "addPassword" && (
            <AddpasswordHoldertemplate
              buttonStyle={buttonStyle}
              setAddPasswordModalShow={setAddPasswordModalShow}
              sessionId={sessionId}
            />
          )}
          {modalType === "addGroup" && (
            <AddGroupHolderTemplate
              buttonStyle={buttonStyle}
              setAddGroupModalShow={setAddGroupModalShow}
              sessionId={sessionId}
            />
          )}
        </div>
      </div>
    </>
  );
};

export default ModalViewTemplate;
