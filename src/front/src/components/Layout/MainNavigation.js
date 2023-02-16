import { useContext } from "react";
import { Link } from "react-router-dom";

import AuthContext from "../../store/auth-context";
import "./MainNavigation.css";
import ServerList from "./ServerList";

const MainNavigation = () => {
  const authCtx = useContext(AuthContext);

  return (
    <ul>
      <ServerList />
    </ul>
  );
};

export default MainNavigation;
