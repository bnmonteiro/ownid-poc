import React, { useRef, useState, useEffect } from "react";
import axios from "axios";
import { OwnID } from "@ownid/react";

function Login() {
  const emailField = useRef(null);
  const [showOwnIDContainer, setShowOwnIDContainer] = useState(false);

  async function onLogin(token) {
    // sessionStorage.setItem("token", token);
    document.cookie = `token=${token}; path=/; SameSite=Lax`;
    window.location.href = "/account";
  }

  async function handleCustomOwnIDLogin() {
    const email = emailField.current.value;
    if (!email) {
      console.log("Please enter an email first!");
      return;
    }

    setShowOwnIDContainer(true); // Show OwnID UI
  }

  useEffect(() => {
    if (showOwnIDContainer) {
      console.log("Starting OwnID authentication in container...");
      window.ownid("start", {
        container: ".ownid-container", // Your custom container
        providers: {
          session: {
            create: async (data) => {
              try {
                // Set user session using data.token
                console.log("Login successful", data);
                onLogin(data.token);
                return { status: "logged-in" };
              } catch (error) {
                console.log("3");
                return { status: "fail", reason: "Failed to create session" };
              }
            },
          },
          events: {
            onFinish: async () => {
              // Redirect to homepage after successful login
              console.log("finished");
              return { action: "redirect", url: "/homepage" };
            },
          },
        },
      });
    }
  }, [showOwnIDContainer]);

  return (
    <div className="login-container">
      {!showOwnIDContainer && (
        <div>
          <form className="login-form" onSubmit={(e) => e.preventDefault()}>
            <h2 className="login-title">Login</h2>

            <input
              ref={emailField}
              type="email"
              name="email"
              placeholder="Email"
              required
              className="login-input"
            />

            {/* Custom OwnID Button */}
            <button
              id="custom-ownid-button"
              onClick={handleCustomOwnIDLogin}
              className="ownid-button"
            >
              LOG IN WITH TOUCH ID
            </button>

            <button type="submit" onClick={onLogin} className="password-button">
              LOG IN WITH PASSWORD
            </button>
          </form>
        </div>
      )}

      {/* Conditionally render OwnID container */}
      {showOwnIDContainer && <div className="ownid-container"></div>}
    </div>
  );
}

export default Login;
