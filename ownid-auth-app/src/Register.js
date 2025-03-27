import React, { useRef, useState } from "react";
import { OwnID } from "@ownid/react";
import axios from "axios";

function Register() {
    const emailField = useRef(null);
    const passwordField = useRef(null);
    const [ownIDData, setOwnIDData] = useState(null);

    async function onSubmit(event) {
        event.preventDefault();
        const userData = {
            loginId: emailField.current.value,
            password: passwordField.current.value,
            ownIdData: ownIDData,
        };

        try {
            const response = await axios.post("https://3527-2804-7f0-bd40-48c9-8ce9-8fa-3e5a-5696.ngrok-free.app/api/users", userData);
            alert("Registration successful!");
            window.location.href = "/account";
        } catch (error) {
            console.error("Registration error:", error);
            alert("Registration failed");
        }
    }

    return (
        <form onSubmit={onSubmit}>
            <h2>Register</h2>
            <input ref={emailField} type="email" name="email" placeholder="Email" required />
            <input ref={passwordField} type="password" name="password" placeholder="Password" />
            <button type="submit">Register</button>
            <OwnID
                type="register"
                loginIdField={emailField}
                passwordField={passwordField}
                onError={(error) => console.error(error)}
                onRegister={(event) => setOwnIDData(event.data)}
            />
        </form>
    );
}

export default Register;
