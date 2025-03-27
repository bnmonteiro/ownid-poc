import React, { useRef } from "react";
import { OwnID } from "@ownid/react";
import axios from "axios";

function Login() {
    const emailField = useRef(null);
    const passwordField = useRef(null);

    async function onLogin(event) {
        const loginId = emailField.current.value;
        
        alert("console.log(loginId)");
        try {
            const response = await axios.post("http://localhost:8080/getSessionByLoginId", { loginId });
            console.log('Su',response.data)
            localStorage.setItem("token", response.data.token);
            window.location.href = "/account";
        } catch (error) {
            console.error("Login failed", error);
            alert("Login failed");
        }
    }

    return (
        <form>
            <h2>Login</h2>
            <input ref={emailField} type="email" name="email" placeholder="Email" required />
            <input ref={passwordField} type="password" name="password" placeholder="Password"  />
            <button type="submit">Log In</button>
            <OwnID
                type="login"
                loginIdField={emailField}
                passwordField={passwordField}
                onError={(error) => console.error(error)}
                onLogin={onLogin}
            />
        </form>
    );
}

export default Login;
