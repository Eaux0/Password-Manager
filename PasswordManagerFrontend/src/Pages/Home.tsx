import { useState } from "react";
import { Navbar, Container, Form, FormControl, Button } from "react-bootstrap";
import { Toggle } from "rsuite";
import "rsuite/dist/rsuite.min.css";
import "bootstrap/dist/css/bootstrap.min.css";
import GridView from "../PageTemplates/GridView";
import LineView from "../PageTemplates/LineView";
import ModalViewTemplate from "../PageTemplates/ModalViewTemplate";

interface HomeProps {
  token: string | null;
  setToken: (token: string | null) => void;
  setLoggedInStatus: (status: boolean) => void;
}

const Home = ({ token, setToken, setLoggedInStatus }: HomeProps) => {
  const handleLogout = () => {
    setToken(null);
    setLoggedInStatus(false);
  };

  const [templateType, setTemplateType] = useState("Grid");
  const [addPasswordModalShow, setAddPasswordModalShow] = useState(false);
  const [addGroupModalShow, setAddGroupModalShow] = useState(false);
  console.log(token);
  return (
    <>
      <Navbar
        bg="dark"
        variant="dark"
        expand={false}
        className="mb-3"
        fixed="top"
      >
        <Container fluid>
          <Form className="d-flex mt-3">
            <FormControl
              type="search"
              placeholder="Search"
              className="me-2"
              style={{ width: "50rem" }}
              aria-label="Search"
            />
          </Form>
          <div>
            <Button variant="outline-success">Search</Button>
          </div>
          <div>
            <Toggle
              size="lg"
              checkedChildren="Grid"
              unCheckedChildren="List"
              onChange={(checked) => setTemplateType(checked ? "Grid" : "List")}
              defaultChecked
            />
          </div>
          <div>
            <Button onClick={() => setAddPasswordModalShow(true)}>
              Add password
            </Button>
          </div>
          <div>
            <Button onClick={() => setAddGroupModalShow(true)}>
              Add Group
            </Button>
          </div>
          <div>
            <Button variant="outline-danger" onClick={handleLogout}>
              Log Out
            </Button>
          </div>
        </Container>
      </Navbar>
      {/* <div className="main-content">Home - Token: {token}</div> */}
      {templateType === "Grid" ? (
        <div className="main-content">
          <GridView setAddPasswordModalShow={setAddPasswordModalShow} />
        </div>
      ) : (
        <div className="main-content">
          <LineView
            index={null}
            gridTitle={null}
            gridDescription={null}
            setAddPasswordModalShow={setAddPasswordModalShow}
          />
        </div>
      )}
      {addPasswordModalShow && (
        <ModalViewTemplate
          index={null}
          modalType="addPassword"
          setAddPasswordModalShow={setAddPasswordModalShow}
        />
      )}
      {addGroupModalShow && (
        <ModalViewTemplate
          index={null}
          modalType="addGroup"
          setAddGroupModalShow={setAddGroupModalShow}
        />
      )}
    </>
  );
};

export default Home;
