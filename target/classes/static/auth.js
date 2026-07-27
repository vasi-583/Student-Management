const API_BASE = "http://localhost:8080/api/auth";

const loginForm = document.getElementById("loginForm");
const signupForm = document.getElementById("signupForm");

if (loginForm) {
    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const errorMsg = document.getElementById("errorMsg");

        try {
            const res = await fetch(`${API_BASE}/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });
            const data = await res.json();

            if (data.token) {
                localStorage.setItem("token", data.token);
                localStorage.setItem("role", data.role);
                localStorage.setItem("username", username);
                window.location.href = "index.html";
            } else {
                errorMsg.textContent = data.error || "Login failed";
            }
        } catch (err) {
            errorMsg.textContent = "Server error. Try again.";
        }
    });
}

if (signupForm) {
    signupForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const errorMsg = document.getElementById("errorMsg");
        const successMsg = document.getElementById("successMsg");

        try {
            const res = await fetch(`${API_BASE}/signup`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });
            const data = await res.json();

            if (data.message) {
                successMsg.textContent = data.message + " Redirecting to login...";
                errorMsg.textContent = "";
                setTimeout(() => window.location.href = "login.html", 1500);
            } else {
                errorMsg.textContent = data.error || "Signup failed";
            }
        } catch (err) {
            errorMsg.textContent = "Server error. Try again.";
        }
    });
}