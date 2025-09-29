interface GridHolderTemplateProps {
  index: number;
  title: string;
  description: string;
  setSelectedGrid: (index: number | null) => void;
}

const GridHolderTemplate = ({
  index,
  title,
  description,
  setSelectedGrid,
}: GridHolderTemplateProps) => {
  return (
    <div
      style={{
        border: "1px solid gray",
        margin: "10px",
        padding: "10px",
      }}
      onClick={() => setSelectedGrid(index)}
    >
      <h3>{title}</h3>
      <p>{description}</p>
    </div>
  );
};

export default GridHolderTemplate;
