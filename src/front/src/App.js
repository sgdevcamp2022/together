import { useContext, useState } from "react";
import { Switch, Route } from "react-router-dom";

import "./App.css";
import AuthPage from "./pages/AuthPage";
import AuthContext from "./store/auth-context";
import Sidebar from "./components/Sidebar/Sidebar";
import Chat from "./components/Chat/Chat";
import ReactModal from "react-modal";
import VideoCallPage from "./components/VideoCall/VideoCall";

ReactModal.setAppElement("#root");

function App() {
  const authCtx = useContext(AuthContext);
  return (
    /*
    <div className="app">
      {authCtx.isLoggedIn ? (
        <>
          <Sidebar />
          <Chat />
        </>
      ) : (
        <AuthPage />
      )}
      
    </div>
        */

    <div>
      <>
        <Switch>
          <Route exact={true} path="/">
            <Sidebar />
            <Chat />
          </Route>
          <Route exact={true} path="/videoCall">
            <VideoCallPage />
          </Route>
        </Switch>
      </>
    </div>
  );
}

export default App;
