import { useState } from "react";
import ModalViewTemplate from "../PageTemplates/ModalViewTemplate";

interface ListHolderTemplateProps {
  index: number;
  title: string;
  description: string;
}

const ListHolderTemplate = ({
  index,
  title,
  description,
}: ListHolderTemplateProps) => {
  const [selectedPassword, setSelectedPassword] = useState<number | null>(null);
  const [editMode, setEditMode] = useState(true);
  const [showPassword, setShowPassword] = useState(false);
  const [username, setUsername] = useState("myUsername");
  const [password, setPassword] = useState("mySecret123");
  const [copiedField, setCopiedField] = useState<
    "username" | "password" | null
  >(null);

  const copyToClipboard = async (
    value: string,
    field: "username" | "password"
  ) => {
    try {
      await navigator.clipboard.writeText(value);
      setCopiedField(field);
      setTimeout(() => setCopiedField(null), 1000);
    } catch (err) {
      console.error("Failed to copy:", err);
    }
  };

  const handleInput = (
    e: React.FormEvent<HTMLHeadingElement | HTMLParagraphElement>,
    type: "title" | "description"
  ) => {
    const newText = e.currentTarget.textContent || "";
    console.log("New text:", type, newText);
  };

  return (
    <>
      <div
        key={index}
        style={{
          border: "1px solid #ccc",
          borderRadius: "5px",
          padding: "2px",
          margin: "8px",
          cursor: "pointer",
        }}
        onClick={() => setSelectedPassword(index)}
      >
        <h4>{title}</h4>
        <p>{description}</p>
      </div>

      {selectedPassword !== null && (
        <ModalViewTemplate
          index={index}
          modalType="showPassword"
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
        />
      )}
    </>
  );
};

export default ListHolderTemplate;
