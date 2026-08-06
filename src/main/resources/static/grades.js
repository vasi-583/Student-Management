const GRADES_URL = "/api/grades";
const STUDENTS_URL = "/api/students";

const token = localStorage.getItem("token");
if (!token) {
    window.location.href = "login.html";
}

const authHeaders = {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
};

const form = document.getElementById("gradeForm");
const tableBody = document.querySelector("#gradeTable tbody");
const submitBtn = document.getElementById("submitBtn");
const studentSelect = document.getElementById("studentSelect");
const logoutBtn = document.getElementById("logoutBtn");

async function loadStudents() {
    const res = await fetch(STUDENTS_URL, { headers: authHeaders });
    if (res.status === 401 || res.status === 403) {
        localStorage.clear();
        window.location.href = "login.html";
        return;
    }
    const students = await res.json();

    studentSelect.innerHTML = '<option value="">Select Student</option>';
    students.forEach(s => {
        const opt = document.createElement("option");
        opt.value = s.id;
        opt.textContent = s.name;
        studentSelect.appendChild(opt);
    });
}

async function loadGrades() {
    const res = await fetch(GRADES_URL, { headers: authHeaders });
    if (res.status === 401 || res.status === 403) {
        localStorage.clear();
        window.location.href = "login.html";
        return;
    }
    const grades = await res.json();
    renderTable(grades);
}

function renderTable(grades) {
    const role = localStorage.getItem("role");
    tableBody.innerHTML = "";
    grades.forEach(g => {
        const percentage = ((g.marks / g.maxMarks) * 100).toFixed(1);
        const deleteBtn = role === "ADMIN"
            ? `<button class="delete" onclick="deleteGrade(${g.id})">Delete</button>`
            : "";
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${g.id}</td>
            <td>${g.student.name}</td>
            <td>${g.subject}</td>
            <td>${g.marks} / ${g.maxMarks}</td>
            <td>${percentage}%</td>
            <td>
                <button class="edit" onclick='editGrade(${g.id}, ${g.student.id}, "${g.subject}", ${g.marks}, ${g.maxMarks})'>Edit</button>
                ${deleteBtn}
            </td>
        `;
        tableBody.appendChild(row);
    });
}

form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = document.getElementById("gradeId").value;
    const grade = {
        studentId: parseInt(studentSelect.value),
        subject: document.getElementById("subject").value,
        marks: parseFloat(document.getElementById("marks").value),
        maxMarks: parseFloat(document.getElementById("maxMarks").value)
    };

    if (id) {
        await fetch(`${GRADES_URL}/${id}`, {
            method: "PUT",
            headers: authHeaders,
            body: JSON.stringify(grade)
        });
    } else {
        await fetch(GRADES_URL, {
            method: "POST",
            headers: authHeaders,
            body: JSON.stringify(grade)
        });
    }

    form.reset();
    document.getElementById("gradeId").value = "";
    document.getElementById("maxMarks").value = 100;
    submitBtn.textContent = "Add Grade";
    loadGrades();
});

function editGrade(id, studentId, subject, marks, maxMarks) {
    document.getElementById("gradeId").value = id;
    studentSelect.value = studentId;
    document.getElementById("subject").value = subject;
    document.getElementById("marks").value = marks;
    document.getElementById("maxMarks").value = maxMarks;
    submitBtn.textContent = "Update Grade";
}

async function deleteGrade(id) {
    if (confirm("Delete this grade record?")) {
        await fetch(`${GRADES_URL}/${id}`, { method: "DELETE", headers: authHeaders });
        loadGrades();
    }
}

if (logoutBtn) {
    logoutBtn.addEventListener("click", () => {
        localStorage.clear();
        window.location.href = "login.html";
    });
}

loadStudents().then(loadGrades);