const API_URL = "http://localhost:8080/api/courses";

const token = localStorage.getItem("token");
if (!token) {
    window.location.href = "login.html";
}

const authHeaders = {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
};

const form = document.getElementById("courseForm");
const tableBody = document.querySelector("#courseTable tbody");
const submitBtn = document.getElementById("submitBtn");
const logoutBtn = document.getElementById("logoutBtn");

async function loadCourses() {
    const res = await fetch(API_URL, { headers: authHeaders });

    if (res.status === 401 || res.status === 403) {
        localStorage.clear();
        window.location.href = "login.html";
        return;
    }

    const courses = await res.json();
    renderTable(courses);
}

function renderTable(courses) {
    const role = localStorage.getItem("role");
    tableBody.innerHTML = "";
    courses.forEach(c => {
        const deleteBtn = role === "ADMIN"
            ? `<button class="delete" onclick="deleteCourse(${c.id})">Delete</button>`
            : "";
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${c.id}</td>
            <td>${c.name}</td>
            <td>${c.description || ""}</td>
            <td>
                <button class="edit" onclick="editCourse(${c.id}, '${c.name}', '${c.description || ""}')">Edit</button>
                ${deleteBtn}
            </td>
        `;
        tableBody.appendChild(row);
    });
}

form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = document.getElementById("courseId").value;
    const course = {
        name: document.getElementById("courseName").value,
        description: document.getElementById("courseDescription").value
    };

    if (id) {
        await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: authHeaders,
            body: JSON.stringify(course)
        });
    } else {
        await fetch(API_URL, {
            method: "POST",
            headers: authHeaders,
            body: JSON.stringify(course)
        });
    }

    form.reset();
    document.getElementById("courseId").value = "";
    submitBtn.textContent = "Add Course";
    loadCourses();
});

function editCourse(id, name, description) {
    document.getElementById("courseId").value = id;
    document.getElementById("courseName").value = name;
    document.getElementById("courseDescription").value = description;
    submitBtn.textContent = "Update Course";
}

async function deleteCourse(id) {
    if (confirm("Delete this course? Students linked to it will lose their course reference.")) {
        await fetch(`${API_URL}/${id}`, { method: "DELETE", headers: authHeaders });
        loadCourses();
    }
}

if (logoutBtn) {
    logoutBtn.addEventListener("click", () => {
        localStorage.clear();
        window.location.href = "login.html";
    });
}


loadCourses();