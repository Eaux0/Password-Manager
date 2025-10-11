import React, { useState } from "react";
import type { AddGroupHolderTemplateProps } from "../DataProcessing/Props";
import axios from "axios";
import useSessionStore from "../DataProcessing/sessionStore";

const AddGroupHolderTemplate: React.FC<AddGroupHolderTemplateProps> = ({
  buttonStyle,
  setAddGroupModalShow,
}) => {
  const [groupName, setGroupName] = useState("");
  const [groupDescription, setGroupDescription] = useState("");
  const { sessionId } = useSessionStore();

  const saveGroup = async () => {
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

    try {
      const response = await axios.post(
        "https://localhost:8080/api/" + sessionId + "/groups",
        {
          userId: -1,
          groupId: -1,
          groupName: groupName,
          groupDescription: groupDescription,
        }
      );

      console.log("Response:", response.data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Axios error:", error.response?.data || error.message);
      } else {
        console.error("Unexpected error:", error);
      }
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
