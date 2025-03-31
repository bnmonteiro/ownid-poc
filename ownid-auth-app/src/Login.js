import React, { useRef, useState, useEffect } from "react";
import axios from "axios";
import { OwnID } from "@ownid/react";

function Login() {
    const emailField = useRef(null);
    const [showOwnIDContainer, setShowOwnIDContainer] = useState(false);

    async function onLogin() {
        const loginId = emailField.current.value;

        try {
            const response = await axios.post("http://localhost:8080/getSessionByLoginId", { loginId });
            alert("Login successful");
            localStorage.setItem("token", response.data.token);
            window.location.href = "/account"; // Redirect to account page
        } catch (error) {
            console.error("Login failed", error);
            alert("Login failed");
        }
    }

    async function handleCustomOwnIDLogin() {
        const email = emailField.current.value;
        if (!email) {
            alert("Please enter an email first!");
            return;
        }

        setShowOwnIDContainer(true); // Show OwnID UI

    }

    useEffect(() => {
        if (showOwnIDContainer) {
            console.log("Starting OwnID authentication in container...");
            window.ownid?.("start", {
                container: ".ownid-container",
                onSuccess: function(response) {
                    // Navigate to a new page or perform any other action
                    alert("success");
                    onLogin();
                },
            });
        }

    }, [showOwnIDContainer]);

    return (
        <div className="login-container">
            <form className="login-form" onSubmit={(e) => e.preventDefault()}>
                <h2 className="login-title">Login</h2>

                <input ref={emailField} type="email" name="email" placeholder="Email" required className="login-input" />

                {/* Custom OwnID Button */}
                <button id="custom-ownid-button" onClick={handleCustomOwnIDLogin} className="ownid-button">
                    LOG IN WITH TOUCH ID
                </button>

                <button type="submit" onClick={onLogin} className="password-button">
                    LOG IN WITH PASSWORD
                </button>

             
            </form>
            {/* Conditionally render OwnID container */}
            {showOwnIDContainer && <div className="ownid-container"></div>}
        </div>
    );
}

export default Login;

