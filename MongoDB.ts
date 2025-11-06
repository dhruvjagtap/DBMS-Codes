// Problem Statement 1 (CRUD)
// Create collection Student with fields as Roll_No, Name, Class, Marks, Address, Enrolled_Courses.
// (Hint: One student can enrol in multiple courses. Use Array to store the names of courses enrolled)
// Insert 10 documents in the collection Student. Write the queries for following.

// 1. List the names of students who have enrolled in the course “DBMS”, “TOC”.
var db = Object();
db.students.find(
    { Enrolled_Courses: { $in: ["DBMS", "TOC"] } },
    { Name: 1, _id: 0 }
);

// 2. List the Roll numbers and class of students who have marks more than 50 or class as TE.
db.students.find(
    {
        $or: [
            { Marks: { $gt: 50 } },
            { Class: "TE" }
        ]
    },
    { Roll_No: 1, Class: 1, _id: 0 }
);

// 3. Update the entire record of roll_no A10.
db.students.updateOne(
    { Roll_No: "A10" },
    {
        $set: {
            Roll_No: "A10",
            Name: "Aditya",
            Class: "SE",
            Marks: 76,
            Address: "Pune",
            Enrolled_Courses: ["DBMS", "AI"]
        }
    }
);


// 4. Display the names of students having 3rd and 4th highest marks.
db.students.find({}, { Name: 1, Marks: 1, _id: 0 })
    .sort({ Marks: -1 })
    .skip(2)
    .limit(2);

// 5. Delete the records of students having marks less than 20.
db.students.deleteMany({ Marks: { $lt: 20 } });

// 6. Delete only first record from the collection.
db.students.deleteOne({});


// ---------------------------------
// Problem statement 4:
// Create the Collection Student_Data( Student _ID, Student _Name, Department, Marks )and solve the
// following:

// 1. Display all Students based on their departments along with an average Marks of a particular
// department.
db.Student_Data.aggregate([
    {
        $group: {
            _id: "$Department",
            Students: { $push: "$Student_Name" },
            Average_Marks: { $avg: "$Marks" }
        }
    }
]);


// 2. Displays the number of Students associated along with a particular department.
db.Student_Data.aggregate([
    {
        $group: {
            _id: "$Department",
            Total_Students: { $sum: 1 }
        }
    }
]);

// 3. Display list of Students with the highest Marks in each Department in descending order
// of Marks.
db.Student_Data.aggregate([
    {
        $sort: { Department: 1, Marks: -1 }
    },
    {
        $group: {
            _id: "$Department",
            Topper: { $first: "$Student_Name" },
            Highest_Marks: { $first: "$Marks" }
        }
    },
    {
        $sort: { Highest_Marks: -1 }
    }
]);


// 4. Create an index on field Student_ID.
db.Student_Data.createIndex({ Student_ID: 1 });

// 5. Create an index on fields “Student_Name‘ and “Department”.
db.Student_Data.createIndex({ Student_Name: 1, Department: 1 });

// 6. Drop an index on field Student_ID.
db.Student_Data.dropIndex({ Student_ID: 1 });

// 7. Drop an index on fields “Student_Name‘ and “Department”.
db.Student_Data.dropIndex({ Student_Name: 1, Department: 1 });

// ---------------------------------
// Problem Statement 7 (Map Reduce)
// Create Book Collection with (Title, Author_name, Borrowed_status) as fields. Write Map Reduce Functions for
// following requirements.
// 1. Display Author wise list of books.
/*
var map = function () {
  emit(this.Author_name, this.Title);
};

var reduce = function (key, values) {
  return values;
};

db.Book.mapReduce(
  map,
  reduce,
  { out: "author_wise_books" }
);



// 2. Display Author wise list of books having Borrowed status as “True”.
var map = function () {
  if (this.Borrowed_status === true) {
    emit(this.Author_name, this.Title);
  }
};

var reduce = function (key, values) {
  return values;
};

db.Book.mapReduce(
  map,
  reduce,
  { out: "author_wise_books_borrowed" }
);


// 3. Display Author wise list of books having price greater than 300.
var map = function () {
  if (this.price > 300) {
    emit(this.Author_name, this.Title);
  }
};

var reduce = function (key, values) {
  return values;
};

db.Book.mapReduce(
  map,
  reduce,
  { out: "author_wise_books_gt300" }
);
*/

// ------------------------------------------------

// Problem Statement 10 (CRUD)

// Create a collection Social_Media having fields as User_Id, User_Name, No_of_Posts, No_of_Friends,

// Friends_List, Interests. (Hint: Friends_List and Interests can be of array type)

// Insert 20 documents in the collection Social_Media. Write queries for following.

// 1. List all the users from collection Social_Media in formatted manner.
db.Social_Media.find().pretty();

// 2. Find all users having number of posts greater than 100.
db.Social_Media.find({ No_of_Posts: { $gt: 100 } }, { User_Name: 1, _id: 0 });

// 3. List the user names and their respective Friens_List
db.Social_Media.find({}, { User_Name: 1, Friends_List: 1, _id: 0 });

// 4. Display the user ids and Friends list of users who have more than 5 friends.
db.Social_Media.find(
    { No_of_Friends: { $gt: 5 } },
    { User_Id: 1, Friends_List: 1, _id: 0 }
);

// 5. Display all users with no of posts in descending order.
db.Social_Media.find({}, { User_Id: 1, User_Name: 1, No_of_Posts: 1, _id: 0 })
    .sort({ No_of_Posts: -1 });

// ---------------------------------------------------

// Problem Statement 13 (Map Reduce)

// Create collection for Student{roll_no, name, class, dept, aggregate_marks}. Write Map Reduce Functions for
// following requirements.

// 1. Finding the total marks of students of “TE” class department-wise.
/*
var mapFunction = function () {
  if (this.class === "TE") {
    emit(this.dept, this.aggregate_marks);
  }
};

var reduceFunction = function (key, values) {
  return Array.sum(values);  // built-in function to sum array values
};

db.Student.mapReduce(
  mapFunction,
  reduceFunction,
  { out: "te_total_marks" }
);



// 2. Finding the highest marks of students of “SE” class department-wise.
var mapFunction = function () {
  if (this.class === "SE") {
    emit(this.dept, this.aggregate_marks);
  }
};

var reduceFunction = function (key, values) {
  return Math.max.apply(null, values);  // returns highest mark in each dept
};

db.Student.mapReduce(
  mapFunction,
  reduceFunction,
  { out: "se_highest_marks" }
);

var reduceFunction = function (key, values) {
  return 
}

// 3. Find Average marks of students of “BE” class department-wise.
var mapFunction = function () {
  if (this.class === "BE") {
    emit(this.dept, this.aggregate_marks);
  }
};

var reduceFunction = function (key, values) {
  var total = Array.sum(values);
  return total / values.length;
};

db.Student.mapReduce(
  mapFunction,
  reduceFunction,
  { out: "be_average_marks" }
);

*/

// ------------------------------------------

// Problem Statement 19 (Aggregation & Indexing)

// Create the Collection Movies_Data( Movie_ID, Movie_Name, Director, Genre, BoxOfficeCollection) and
// solve the following:

// 1. Display a list stating how many Movies are directed by each “Director”.
db.Movies_Data.aggregate([
    {
        $group: {
            _id: "$Director",
            Total_Movies: { $sum: 1 },
            Movie_List: { $push: "$Movie_Name" }
        }
    }
]);

// 2. Display list of Movies with the highest BoxOfficeCollection in each Genre.
db.Movies_Data.aggregate([
    {
        $group: {
            _id: "$Genre",
            Highest_Collection: { $max: "$BoxOfficeCollection" },
            Top_Movie: { $first: "$Movie_Name" }
        }
    }
]);

// 3. Display list of Movies with the highest BoxOfficeCollection in each Genre in ascending order
// of BoxOfficeCollection.
db.Movies_Data.aggregate([
    { $sort: { Genre: 1, BoxOfficeCollection: -1 } },
    {
        $group: {
            _id: "$Genre",
            Top_Movie: { $first: "$Movie_Name" },
            Highest_Collection: { $first: "$BoxOfficeCollection" }
        }
    },
    { $sort: { Highest_Collection: 1 } } // ascending order
]);

// 4. Create an index on field Movie_ID.
db.Movies_Data.createIndex({ Movie_ID: 1 });

// 5. Create an index on fields ” Movie_Name” and ” Director”.
db.Movies_Data.createIndex({ Movie_Name: 1, Director: 1 });

// 6. Drop an index on field Movie_ID.
db.Movies_Data.dropIndex({ Movie_ID: 1 });

// 7. Drop an index on fields ” Movie_Name” and ” Director”
db.Movies_Data.dropIndex({ Movie_Name: 1, Director: 1 });
