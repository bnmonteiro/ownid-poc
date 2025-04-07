import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { OwnIDInit } from "@ownid/react";
import Register from "./Register";
import Login from "./Login";
import Account from "./Account";
import IframeContent from "./IframeContent";

function App() {
  return (
    <Router>
      <OwnIDInit config={{ appId: "lbsvvkuvx8pdq1" }} />
      <Routes>
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/account" element={<Account />} />
        <Route path="/iframe" element={<IframeContent />} />
      </Routes>
    </Router>
  );
}

export default App;
