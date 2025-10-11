import { useState, useEffect } from "react";
import axios from "axios";
import GridHolderTemplate from "../DataHolderTemplate/GridHolderTemplate";
import LineView from "./LineView";
import type { GroupResponse } from "../DataProcessing/RestApis.ts";
import type { GridViewProps } from "../DataProcessing/Props.ts";
import useSessionStore from "../DataProcessing/sessionStore.ts";

const GridView = ({ setAddPasswordModalShow }: GridViewProps) => {
  const [selectedGrid, setSelectedGrid] = useState<number | null>(null);
  const [grids, setGrids] = useState<GroupResponse[]>([]);
  const { sessionId } = useSessionStore();

  useEffect(() => {
    const fetchGrids = async () => {
      try {
        const response = await axios.get<GroupResponse[]>(
          "https://localhost:8080/api/" + sessionId + "/groups"
        );
        setGrids(response.data);
      } catch (error) {
        console.error("Failed to fetch groups", error);
      }
    };

    fetchGrids();
  }, []);

  return (
    <>
      {selectedGrid == null &&
        grids.map((grid, i) => (
          <GridHolderTemplate
            index={i}
            title={grid.groupName}
            description={grid.groupDescription}
            setSelectedGrid={setSelectedGrid}
          />
        ))}
      {selectedGrid != null && (
        <LineView
          index={selectedGrid}
          gridTitle={grids[selectedGrid].groupName}
          gridDescription={grids[selectedGrid].groupDescription}
          setSelectedGrid={setSelectedGrid}
          setAddPasswordModalShow={setAddPasswordModalShow}
        />
      )}
    </>
  );
};

export default GridView;
