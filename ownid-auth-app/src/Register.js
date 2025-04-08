import React, { useRef } from "react";

import { OwnID, WidgetType } from "@ownid/react";
import axios from "axios";

function Register() {
  const emailField = useRef(null);
  const passwordField = useRef(null);

  function onRegister(ownIdData) {
    // store the ownIdDataObject in your app storage.
    // global variable is just an example. It's not recommended using it in that way
    console.log("onRegister:", ownIdData);
    window.ownIdDataObject = ownIdData;
  }

  function onSubmit(formData) {
    console.log("onSubmit:", formData);
    if (window.ownIdDataObject) {
      formData.ownIdData = window.ownIdDataObject;
    }

    // this is just an example - Call your existing registration logic in the backend
    return register(formData);
  }

  async function register(formData) {
    // Call your backend API to register the user with formData and ownIdData
    console.log("Registering user with data:", formData);

    const userData = {
      loginId: window.ownIdDataObject.loginId,
      password: formData.password,
      ownIdData: window.ownIdDataObject.data,
    };
    console.log("userData:", userData);

    try {
      await axios.post("http://localhost:8080/api/users", userData);
      alert("Registration successful!");
      window.location.href = "/login";
    } catch (error) {
      console.error("Registration error:", error);
      alert("Registration failed");
    }
  }

  return (
    <form className="register-form" onSubmit={(e) => e.preventDefault()}>
      <input
        ref={emailField}
        type="email"
        name="email"
        placeholder="Email"
        className="form-input"
        required
      />
      <input
        type="text"
        name="first-name"
        placeholder="Name"
        className="form-input"
        required
      />
      <input
        ref={passwordField}
        type="password"
        name="password"
        placeholder="Password"
        className="form-input"
      />
      {/* <input
    ref={confirmPasswordField}
    type="password"
    name="confirm-password"
    placeholder="Confirm Password"
    className="form-input"
  /> */}
      <button type="submit" onClick={onSubmit} className="form-button">
        Register
      </button>

      <OwnID
        type={WidgetType.Register}
        loginIdField={emailField}
        passwordField={passwordField}
        passwordFieldBinding={true}
        // confirmPasswordContainer={confirmPasswordField}
        onError={(error) => console.error(error)}
        onRegister={onRegister}
      />
    </form>
  );
}

export default Register;
