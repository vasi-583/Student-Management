const STATS_URL = "/api/dashboard/stats";

const token = localStorage.getItem("token");
if (!token) {
    window.location.href = "login.html";
}

const authHeaders = {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
};

const logoutBtn = document.getElementById("logoutBtn");
const welcomeUser = document.getElementById("welcomeUser");

async function loadStats() {
    const res = await fetch(STATS_URL, { headers: authHeaders });

    if (res.status === 401 || res.status === 403) {
        localStorage.clear();
        window.location.href = "login.html";
        return;
    }

    const stats = await res.json();

    document.getElementById("statStudents").textContent = stats.totalStudents;
    document.getElementById("statCourses").textContent = stats.totalCourses;
    document.getElementById("statGrade").textContent = stats.totalGradeRecords > 0
        ? stats.avgGradePercentage + "%"
        : "No data";
    document.getElementById("statAttendance").textContent = stats.totalAttendanceRecords > 0
        ? stats.attendanceRate + "%"
        : "No data";
}

welcomeUser.textContent = localStorage.getItem("username") || "";

if (logoutBtn) {
    logoutBtn.addEventListener("click", () => {
        localStorage.clear();
        window.location.href = "login.html";
    });
}

loadStats();