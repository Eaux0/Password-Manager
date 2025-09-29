import { useEffect, useState } from "react";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import Login from "./Pages/Login";
import Home from "./Pages/Home";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  useNavigate,
  useLocation,
} from "react-router-dom";

const AppRoutes = ({
  token,
  setToken,
  setLoggedInStatus,
}: {
  token: string | null;
  setToken: React.Dispatch<React.SetStateAction<string | null>>;
  isLoggedIn: boolean;
  setLoggedInStatus: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    if (!token && location.pathname !== "/login") {
      navigate("/login");
    } else if (token && location.pathname === "/login") {
      navigate("/");
    }
  }, [location, navigate, token]);

  return (
    <Routes>
      <Route
        path="/"
        element={
          <Home
            token={token}
            setToken={setToken}
            setLoggedInStatus={setLoggedInStatus}
          />
        }
      />
      <Route path="/login" element={<Login setToken={setToken} />} />
    </Routes>
  );
};

const App = () => {
  const [token, setToken] = useState<string | null>(null);
  const [isLoggedIn, setLoggedInStatus] = useState(false);
  return (
    <Router>
      <AppRoutes
        token={token}
        setToken={setToken}
        isLoggedIn={isLoggedIn}
        setLoggedInStatus={setLoggedInStatus}
      />
    </Router>
  );
};

export default App;
