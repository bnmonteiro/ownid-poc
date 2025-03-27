import React, { useEffect, useState } from "react";
import axios from "axios";

function Account() {
    const [userData, setUserData] = useState(null);
    const token = localStorage.getItem("token");

    useEffect(() => {
        async function fetchUser() {
            try {
                const response = await axios.get("http://localhost:8080/api/users/me", {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setUserData(response.data);
            } catch (error) {
                console.error("Failed to fetch user data", error);
            }
        }
        fetchUser();
    }, [token]);

    return (
        <div>
            <h2>Account Page</h2>
            {userData ? <pre>{JSON.stringify(userData, null, 2)}</pre> : <p>Loading...</p>}
            <button onClick={() => { localStorage.removeItem("token"); window.location.href = "/login"; }}>Logout</button>
        </div>
    );
}

export default Account;
