import React from "react";
import ListHolderTemplate from "../DataHolderTemplate/ListHolderTemplate";
import { Button } from "react-bootstrap";

interface LineViewProps {
  index: number | null;
  gridTitle: string | null;
  gridDescription: string | null;
  setSelectedGrid?: (index: number | null) => void;
  setAddPasswordModalShow: (show: boolean) => void;
}

const LineView = ({
  index,
  gridTitle,
  gridDescription,
  setSelectedGrid,
}: LineViewProps) => {
  const generateRandomString = (length: number): string => {
    const chars =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    return Array.from(
      { length },
      () => chars[Math.floor(Math.random() * chars.length)]
    ).join("");
  };

  const passwords: string[] = [];
  const passwordDescriptions: string[] = [];

  for (let i = 0; i < 10; i++) {
    passwords.push(generateRandomString(10));
    passwordDescriptions.push(generateRandomString(20));
  }

  const listPasswords = (index: number | null) => {
    console.log("Listing passwords for index:", index);
    return passwords.map((password, i) => (
      <ListHolderTemplate
        index={i}
        title={password}
        description={passwordDescriptions[i]}
      />
    ));
  };

  const handleInput = (
    e: React.FormEvent<HTMLHeadingElement | HTMLParagraphElement>,
    type: "title" | "description"
  ) => {
    const newText = e.currentTarget.textContent || "";
    console.log("New text:", type, newText);
  };

  const topBar = () => {
    return (
      <div style={{ display: "flex", alignItems: "left", gap: "10px" }}>
        <div>
          <h3
            contentEditable
            suppressContentEditableWarning
            onInput={(e) => handleInput(e, "title")}
          >
            {gridTitle}
          </h3>
          <p
            contentEditable
            suppressContentEditableWarning
            onInput={(e) => handleInput(e, "description")}
          >
            {gridDescription}
          </p>
        </div>
        <div style={{ marginLeft: "auto", display: "flex", gap: "10px" }}>
          <Button onClick={() => setSelectedGrid && setSelectedGrid(null)}>
            Back
          </Button>
        </div>
      </div>
    );
  };

  return (
    <>
      {index != null && (
        <>
          {topBar()}
          <hr></hr>
          {listPasswords(index)}
        </>
      )}
      {index == null && <>{listPasswords(null)}</>}
    </>
  );
};

export default LineView;
