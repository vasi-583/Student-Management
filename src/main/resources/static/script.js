const API_URL = "http://localhost:8080/api/students";

const token = localStorage.getItem("token");
if (!token) {
    window.location.href = "login.html";
}

const authHeaders = {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
};

const form = document.getElementById("studentForm");
const tableBody = document.querySelector("#studentTable tbody");
const submitBtn = document.getElementById("submitBtn");
const searchInput = document.getElementById("searchInput");
const logoutBtn = document.getElementById("logoutBtn");

let allStudents = [];

async function loadStudents() {
    const res = await fetch(API_URL, { headers: authHeaders });

    if (res.status === 401 || res.status === 403) {
        localStorage.clear();
        window.location.href = "login.html";
        return;
    }

    allStudents = await res.json();
    renderTable(allStudents);
}

function renderTable(students) {
    tableBody.innerHTML = "";
    students.forEach(s => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${s.id}</td>
            <td>${s.name}</td>
            <td>${s.course}</td>
            <td>${s.email}</td>
            <td>
                <button class="edit" onclick="editStudent(${s.id}, '${s.name}', '${s.course}', '${s.email}')">Edit</button>
                <button class="delete" onclick="deleteStudent(${s.id})">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

if (searchInput) {
    searchInput.addEventListener("input", () => {
        const query = searchInput.value.toLowerCase();
        const filtered = allStudents.filter(s =>
            s.name.toLowerCase().includes(query) ||
            s.course.toLowerCase().includes(query)
        );
        renderTable(filtered);
    });
}

form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = document.getElementById("studentId").value;
    const student = {
        name: document.getElementById("name").value,
        course: document.getElementById("course").value,
        email: document.getElementById("email").value
    };

    if (id) {
        await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: authHeaders,
            body: JSON.stringify(student)
        });
    } else {
        await fetch(API_URL, {
            method: "POST",
            headers: authHeaders,
            body: JSON.stringify(student)
        });
    }

    form.reset();
    document.getElementById("studentId").value = "";
    submitBtn.textContent = "Add Student";
    loadStudents();
});

function editStudent(id, name, course, email) {
    document.getElementById("studentId").value = id;
    document.getElementById("name").value = name;
    document.getElementById("course").value = course;
    document.getElementById("email").value = email;
    submitBtn.textContent = "Update Student";
}

async function deleteStudent(id) {
    if (confirm("Delete this student?")) {
        await fetch(`${API_URL}/${id}`, { method: "DELETE", headers: authHeaders });
        loadStudents();
    }
}

if (logoutBtn) {
    logoutBtn.addEventListener("click", () => {
        localStorage.clear();
        window.location.href = "login.html";
    });
}

loadStudents();