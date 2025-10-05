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
  sessionId,
  setSessionId,
}: {
  token: string | null;
  setToken: React.Dispatch<React.SetStateAction<string | null>>;
  isLoggedIn: boolean;
  setLoggedInStatus: React.Dispatch<React.SetStateAction<boolean>>;
  sessionId: number | null;
  setSessionId: React.Dispatch<React.SetStateAction<number | null>>;
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
            sessionId={sessionId}
          />
        }
      />
      <Route
        path="/login"
        element={<Login setToken={setToken} setSessionId={setSessionId} />}
      />
    </Routes>
  );
};

const App = () => {
  const [token, setToken] = useState<string | null>(null);
  const [isLoggedIn, setLoggedInStatus] = useState(false);
  const [sessionId, setSessionId] = useState<number | null>(null);
  return (
    <Router>
      <AppRoutes
        token={token}
        setToken={setToken}
        isLoggedIn={isLoggedIn}
        setLoggedInStatus={setLoggedInStatus}
        sessionId={sessionId}
        setSessionId={setSessionId}
      />
    </Router>
  );
};

export default App;
