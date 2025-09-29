import React, { useState } from "react";

interface AddGroupHolderTemplateProps {
  buttonStyle?: (color: "gray" | "blue" | "red") => React.CSSProperties;
  setAddGroupModalShow?: (show: boolean) => void;
}

const AddGroupHolderTemplate: React.FC<AddGroupHolderTemplateProps> = ({
  buttonStyle,
  setAddGroupModalShow,
}) => {
  const [groupName, setGroupName] = useState("");
  const [groupDescription, setGroupDescription] = useState("");

  const saveGroup = () => {
    if (groupName.trim() === "") {
      alert("Group name cannot be empty");
      return;
    }
    if (groupName === "Group Identifier") {
      alert("Enter a valid group name");
      return;
    }
    if (groupDescription === "Group Description") {
      alert("Enter a valid group description");
      return;
    }
    console.log("Group saved!", { groupName, groupDescription });
    setAddGroupModalShow?.(false);
  };

  return (
    <div>
      <h2
        style={{ marginBottom: "8px" }}
        contentEditable
        suppressContentEditableWarning
        onInput={(e) => setGroupName(e.currentTarget.textContent || "")}
      >
        {"Group Identifier"}
      </h2>
      <p
        style={{ marginBottom: "20px", color: "#555" }}
        contentEditable
        suppressContentEditableWarning
        onInput={(e) => setGroupDescription(e.currentTarget.textContent || "")}
      >
        {"Group Description"}
      </p>
      <div
        style={{
          marginTop: "24px",
          display: "flex",
          gap: "10px",
          flexWrap: "wrap",
        }}
      >
        <button
          onClick={() => setAddGroupModalShow?.(false)}
          style={buttonStyle?.("gray")}
        >
          Close
        </button>
        <button onClick={() => saveGroup()} style={buttonStyle?.("blue")}>
          Save
        </button>
      </div>
    </div>
  );
};

export default AddGroupHolderTemplate;
