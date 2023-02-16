import { useContext, useState } from "react";

import "./App.css";
import AuthPage from "./pages/AuthPage";
import AuthContext from "./store/auth-context";
import Sidebar from "./components/Sidebar/Sidebar";
import Chat from "./components/Chat/Chat";

function App() {
  const authCtx = useContext(AuthContext);
  return (
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
  );
}

export default App;
