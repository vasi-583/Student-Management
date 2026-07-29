const ATTENDANCE_URL = "http://localhost:8080/api/attendance";
const STUDENTS_URL = "http://localhost:8080/api/students";

const token = localStorage.getItem("token");
if (!token) {
    window.location.href = "login.html";
}

const authHeaders = {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
};

const form = document.getElementById("attendanceForm");
const tableBody = document.querySelector("#attendanceTable tbody");
const studentSelect = document.getElementById("studentSelect");
const logoutBtn = document.getElementById("logoutBtn");
const filterBtn = document.getElementById("filterBtn");
const clearFilterBtn = document.getElementById("clearFilterBtn");
const filterDate = document.getElementById("filterDate");

let allStudents = [];

async function loadStudents() {
    const res = await fetch(STUDENTS_URL, { headers: authHeaders });
    if (res.status === 401 || res.status === 403) {
        localStorage.clear();
        window.location.href = "login.html";
        return;
    }
    allStudents = await res.json();

    studentSelect.innerHTML = '<option value="">Select Student</option>';
    allStudents.forEach(s => {
        const opt = document.createElement("option");
        opt.value = s.id;
        opt.textContent = s.name;
        studentSelect.appendChild(opt);
    });
}

async function loadAttendance() {
    const res = await fetch(ATTENDANCE_URL, { headers: authHeaders });
    if (res.status === 401 || res.status === 403) {
        localStorage.clear();
        window.location.href = "login.html";
        return;
    }
    const records = await res.json();
    renderTable(records);
}

function renderTable(records) {
    const role = localStorage.getItem("role");
    tableBody.innerHTML = "";
    records.forEach(r => {
        const deleteBtn = role === "ADMIN"
            ? `<button class="delete" onclick="deleteAttendance(${r.id})">Delete</button>`
            : "";
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${r.id}</td>
            <td>${r.student.name}</td>
            <td>${r.date}</td>
            <td>${r.status}</td>
            <td>${deleteBtn}</td>
        `;
        tableBody.appendChild(row);
    });
}

form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const record = {
        studentId: parseInt(studentSelect.value),
        date: document.getElementById("attendanceDate").value,
        status: document.getElementById("statusSelect").value
    };

    await fetch(ATTENDANCE_URL, {
        method: "POST",
        headers: authHeaders,
        body: JSON.stringify(record)
    });

    form.reset();
    loadAttendance();
});

async function deleteAttendance(id) {
    if (confirm("Delete this attendance record?")) {
        await fetch(`${ATTENDANCE_URL}/${id}`, { method: "DELETE", headers: authHeaders });
        loadAttendance();
    }
}

filterBtn.addEventListener("click", async () => {
    if (!filterDate.value) return;
    const res = await fetch(`${ATTENDANCE_URL}/date/${filterDate.value}`, { headers: authHeaders });
    const records = await res.json();
    renderTable(records);
});

clearFilterBtn.addEventListener("click", () => {
    filterDate.value = "";
    loadAttendance();
});

if (logoutBtn) {
    logoutBtn.addEventListener("click", () => {
        localStorage.clear();
        window.location.href = "login.html";
    });
}

loadStudents().then(loadAttendance);