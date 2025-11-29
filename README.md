# 📋 TaskFlow - Project & Issue Tracking System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

> **Platform kolaborasi dan manajemen proyek terintegrasi berbasis desktop untuk tim pengembang dan kreatif.**

---

## 📖 Tentang Proyek

**TaskFlow** adalah aplikasi manajemen proyek berbasis desktop (Java Swing) yang dirancang untuk membantu tim mengorganisir, melacak, dan mengelola proyek serta issue/ticket secara efektif.

Aplikasi ini hadir sebagai solusi bagi tim software development, tim kreatif, atau organisasi yang membutuhkan alat manajemen workflow yang stabil tanpa ketergantungan pada browser (web-based), dengan visualisasi progress yang jelas dan pelacakan yang detail.

## 🚀 Fitur Utama

* **Project Planning Central:** Membuat, mengedit, dan memantau timeline proyek dalam satu dashboard.
* **Issue & Bug Tracking:** Sistem tiket yang komprehensif untuk pelaporan bug atau request fitur.
* **Task Management:** Assign tugas ke anggota tim, set prioritas, dan deadline.
* **Time Tracking:** Log jam kerja untuk setiap task (Work Logs).
* **Collaboration:** Sistem komentar dan update status real-time pada setiap tiket.
* **Reporting:** Generate laporan performa tim dan progress proyek.

## 🛠️ Teknologi yang Digunakan

Aplikasi ini dibangun dengan arsitektur **MVC (Model-View-Controller)** menggunakan stack berikut:

* **Bahasa Pemrograman:** Java (JDK 17+)
* **GUI Framework:** Java Swing (FlatLaf untuk UI Modern)
* **Application Framework:** Spring Framework (Dependency Injection & Transaction Management)
* **ORM:** Hibernate
* **Database:** MySQL / PostgreSQL
* **Build Tool:** Maven / Gradle

## 👥 Hak Akses Pengguna (User Roles)

Sistem ini memiliki 4 tingkatan akses (Role-Based Access Control):

### 1. 🛡️ Admin
* Manajemen User & Team (CRUD).
* Kontrol penuh atas semua proyek.
* Akses ke laporan global & analitik sistem.
* Konfigurasi sistem umum.

### 2. 👔 Project Manager
* Membuat & inisialisasi proyek baru.
* Assign task ke anggota tim (Team Members).
* Monitoring progress dan approval.
* Generate laporan proyek spesifik.

### 3. 💻 Team Member / Developer
* Melihat task yang ditugaskan (Assigned Tasks).
* Update status task (To Do -> In Progress -> Done).
* Log jam kerja (Work hours).
* Kolaborasi via komentar.

### 4. 📝 Reporter (Optional/Client)
* Membuat issue baru atau bug report.
* Melacak status tiket yang dibuat.
* Akses *View-Only* terbatas.

## 📸 Tangkapan Layar (Screenshots)

| Dashboard | Task Board |
|:---:|:---:|
| ![Dashboard Placeholder](https://via.placeholder.com/400x200?text=Dashboard+UI) | ![Task Board Placeholder](https://via.placeholder.com/400x200?text=Task+Details) |

## ⚙️ Instalasi & Cara Menjalankan

Ikuti langkah berikut untuk menjalankan project di lokal Anda:

### Prasyarat
* Java Development Kit (JDK) versi 17 atau terbaru.
* MySQL atau PostgreSQL terinstall.
* Maven.

### Langkah-langkah

1.  **Clone Repository**
    ```bash
    git clone [https://github.com/username/TaskFlow.git](https://github.com/username/TaskFlow.git)
    cd TaskFlow
    ```

2.  **Konfigurasi Database**
    Buat database baru di MySQL/PostgreSQL (misal: `taskflow_db`).
    Buka file `src/main/resources/application.properties` dan sesuaikan kredensial:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/taskflow_db
    spring.datasource.username=root
    spring.datasource.password=password_anda
    spring.jpa.hibernate.ddl-auto=update
    ```

3.  **Build Project**
    ```bash
    mvn clean install
    ```

4.  **Jalankan Aplikasi**
    ```bash
    mvn spring-boot:run
    # Atau jalankan file Main.java melalui IDE (IntelliJ/Eclipse/Netbeans)
    ```

## 🤝 Kontribusi

Kontribusi sangat diterima! Jika Anda ingin menambahkan fitur atau memperbaiki bug:

1.  Fork repository ini.
2.  Buat branch fitur baru (`git checkout -b fitur-keren`).
3.  Commit perubahan Anda (`git commit -m 'Menambahkan fitur keren'`).
4.  Push ke branch (`git push origin fitur-keren`).
5.  Buat Pull Request.

## 📄 Lisensi

Didistribusikan di bawah lisensi MIT. Lihat `LICENSE` untuk informasi lebih lanjut.

---
Dikembangkan oleh Nopall
# orbit-app
