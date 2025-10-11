import React, { useState, useEffect } from "react";
import axios from "axios";
import ListHolderTemplate from "../DataHolderTemplate/ListHolderTemplate";
import { Button } from "react-bootstrap";
import type { LineViewProps, ListItem } from "../DataProcessing/Props";
import useSessionStore from "../DataProcessing/sessionStore";

const LineView = ({
  index,
  gridTitle,
  gridDescription,
  setSelectedGrid,
}: LineViewProps) => {
  const [passwords, setPasswords] = useState<ListItem[]>([]);
  const { sessionId } = useSessionStore();

  useEffect(() => {
    let url = "";
    if (index != null)
      url =
        "https://localhost:8080/api/" + sessionId + "/" + index + "/passwords";
    else url = "https://localhost:8080/api/" + sessionId + "/passwords";

    const fetchPasswords = async () => {
      try {
        const response = await axios.get<ListItem[]>(url);
        setPasswords(response.data);
      } catch (error) {
        console.error("Failed to fetch groups", error);
      }
    };

    fetchPasswords();
  }, []);

  const listPasswords = (index: number | null) => {
    console.log("Listing passwords for index:", index);
    return passwords.map((password, i) => (
      <ListHolderTemplate
        index={i}
        title={password.title}
        description={password.description}
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
