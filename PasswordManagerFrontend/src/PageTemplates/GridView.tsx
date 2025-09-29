import { useState } from "react";
import GridHolderTemplate from "../DataHolderTemplate/GridHolderTemplate";
import LineView from "./LineView";

interface GridViewProps {
  setAddPasswordModalShow: (show: boolean) => void;
}

const GridView = ({ setAddPasswordModalShow }: GridViewProps) => {
  const generateRandomString = (length: number): string => {
    const chars =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    return Array.from(
      { length },
      () => chars[Math.floor(Math.random() * chars.length)]
    ).join("");
  };

  const grids: string[] = [];
  const gridDescriptions: string[] = [];

  for (let i = 0; i < 10; i++) {
    grids.push(generateRandomString(10));
    gridDescriptions.push(generateRandomString(20));
  }

  const [selectedGrid, setSelectedGrid] = useState<number | null>(null);

  return (
    <>
      {selectedGrid == null &&
        grids.map((grid, i) => (
          <GridHolderTemplate
            index={i}
            title={grid}
            description={gridDescriptions[i]}
            setSelectedGrid={setSelectedGrid}
          />
        ))}
      {selectedGrid != null && (
        <LineView
          index={selectedGrid}
          gridTitle={grids[selectedGrid]}
          gridDescription={gridDescriptions[selectedGrid]}
          setSelectedGrid={setSelectedGrid}
          setAddPasswordModalShow={setAddPasswordModalShow}
        />
      )}
    </>
  );
};

export default GridView;
