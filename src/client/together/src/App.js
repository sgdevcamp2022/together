import logo from "./logo.svg";
import "./App.css";
import { Switch, Route } from "react-router-dom";
import MainPage from "./main";
import VideoCallPage from "./videoCall";

function App() {
  return (
    <div>
      <Switch>
        <Route exact={true} path="/">
          <MainPage />
        </Route>
        <Route exact={true} path="/videoCall">
          <VideoCallPage />
        </Route>
      </Switch>
    </div>
  );
}

export default App;
