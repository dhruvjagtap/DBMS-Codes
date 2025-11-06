<!DOCTYPE html>
<html>
<head>
    <title>Student Form</title>
</head>
<body>

<h2>Add New Student</h2>
<form action="insert.php" method="post">
    Firstname: <input type="text" name="firstname"><br><br>
    Lastname: <input type="text" name="lastname"><br><br>
    Age: <input type="number" name="age"><br><br>
    <input type="submit" value="Add Student">
</form>

<hr>

<h2>Student Records</h2>
<?php
include 'dbconnect.php';

$result = mysqli_query($conn, "SELECT * FROM students");

echo "<table border='1' cellpadding='5'>
<tr><th>ID</th><th>Firstname</th><th>Lastname</th><th>Age</th><th>Actions</th></tr>";

while($row = mysqli_fetch_assoc($result)) {
    echo "<tr>
            <td>{$row['id']}</td>
            <td>{$row['firstname']}</td>
            <td>{$row['lastname']}</td>
            <td>{$row['age']}</td>
            <td>
                <a href='edit.php?id={$row['id']}'>Edit</a> | 
                <a href='delete.php?id={$row['id']}'>Delete</a>
            </td>
          </tr>";
}
echo "</table>";

mysqli_close($conn);
?>
</body>
</html>
