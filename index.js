require("dotenv").config();
require("./utils.js");
const session = require("express-session");
const express = require("express");
const Joi = require("joi");
const saltRounds = 12;
const bcrypt = require("bcrypt");
const MongoStore = require("connect-mongo");

//Secrets
const mongodb_host = process.env.MONGODB_HOST;
const mongodb_user = process.env.MONGODB_USER;
const mongodb_password = process.env.MONGODB_PASSWORD;
const mongodb_database = process.env.MONGODB_DATABASE;
const mongodb_session_secret = process.env.MONGODB_SESSION_SECRET;
const node_session_secret = process.env.NODE_SESSION_SECRET;

const app = express();
const port = process.env.PORT || 3001;

const expire = 1 * 60 * 60 * 1000;

var users = [];

var { database } = include("databaseConnection");

const userCollection = database.db(mongodb_database).collection("users");

app.set('view engine', 'ejs');
app.use(express.urlencoded({ extended: false }));

//Access mongodb
var mongoStore = MongoStore.create({
  mongoUrl: `mongodb+srv://${mongodb_user}:${mongodb_password}@${mongodb_host}/sessions`,
  crypto: {
    secret: mongodb_session_secret,
  },
});

//setup session storage and secret
app.use(
  session({
    secret: node_session_secret,
    store: mongoStore,
    saveUninitialized: false,
    resave: true,
  })
);

function isValidSession(req) {
  if (req.session.authenticated) {
    return true;
  }
  return false;
}

function sessionValidation(req, res, next) {
  if (isValidSession(req)) {
    next();
  }
  else {
    res.redirect('/login');
  }
}

function isAdmin(req) {
  if (req.session.user_type == 'admin') {
    return true;
  }
  return false;
}

function adminAuthorization(req, res, next) {
  if (!isAdmin(req)) {
    res.status(403);
    res.render('403');
    return;
  }
  else {
    next();
  }
}

app.get("/", (req, res) => {
  if (!req.session.authenticated) {
    res.render("index");
  } else {
    var name = req.session.name;
    res.render("members-logout", {user : name});
  }
});

app.get("/nosql-injection", async (req, res) => {
  var name = req.query.user;
  if (!name) {
    res.send(
      `<h3>No user provided - try /nosql-injection?user=name</h3> <h3>or /nosql-injection?user[$ne]=name</h3>`
    );
    return;
  }
  const schema = Joi.string().max(20).required();
  const validationResult = schema.validate(name);

  if (validationResult.error != null) {
    console.log(validationResult.error);
    res.send(`<h3>Oops! not sure what happened there, try again!</h3>`);
    return;
  }

  const result = await userCollection
    .find({ name: name })
    .project({ name: 1, password: 1, _id: 1 })
    .toArray();

  console.log(result);

  res.send(`<h1>Hello, ${name}!</h1>`);
});

app.get("/login", (req, res) => {
  var error = req.query.error;
 if (error) {
    res.render("login-error");
 }
  res.render("login");
});

app.get("/signup", (req, res) => {
 res.render("signup");
});

app.get("/signup-error", (req, res) => {
  var missing = req.query.missing;
  var exists = req.query.exists;
  if (exists){
    res.render("email-exists")
  }
  if (missing) {
  res.render("missing-signup");
}
});

app.post("/submitUser", async (req, res) => {
  var name = req.body.name;
  var password = req.body.password;
  var email = req.body.email;

  const schema = Joi.object({
    name: Joi.string().alphanum().max(20).required(),
    password: Joi.string().max(20).required(),
    email: Joi.string().required(),
  });

  const validationResult = schema.validate({ name, password, email });

  const existingUser = await userCollection
    .find({ email: email })
    .project({ email: email })
    .toArray();

  if (existingUser.length != 0) {
    console.log("user already exists");
    res.redirect("/signup-error?exists=1");
    return;
  }

  if (name == '' || email == '' || password == '') {
    res.redirect("/signup-error?missing=1");
    return;
  }
  
  var hashedPassword = await bcrypt.hash(password, saltRounds);
  await userCollection.insertOne({
    name: name,
    password: hashedPassword,
    email: email,
    user_type: "user"
  });
  console.log("signup successful");
  req.session.authenticated = true;
  req.session.name = name;
  res.redirect("/");
});

app.post("/loggingin", async (req, res) => {
  var email = req.body.email;
  var password = req.body.password;

  const schema = Joi.string().max(20).required();
  const validationResult = schema.validate(email);
  if (validationResult.error != null) {
    console.log(validationResult.error);
    res.redirect("/login");
    return;
  }
  const result = await userCollection
    .find({ email: email })
    .project({ name: 1, email: 1, password: 1, _id: 1, user_type: 1})
    .toArray();

  if (result.length != 1) {
    res.redirect("/login?error=1");
    return;
  }
  if (await bcrypt.compare(password, result[0].password)) {
    console.log("Great success!");
    req.session.authenticated = true;
    req.session.email = email;
    req.session.user_type = result[0].user_type;
    req.session.name = result[0].name;
    req.session.cookie.maxAge = expire;

    res.redirect("/loggedin");
    return;
  } else {
    res.redirect("/login?error=1");
    return;
  }
});

app.get("/loggedin", (req, res) => {
  if (!req.session.authenticated) {
    res.redirect("/login");
  } else {
    res.redirect("/members");
  }
});

app.get("/logout", (req, res) => {
  req.session.destroy((e) => {
    if (e) {
      console.log(e);
    } else {
      res.redirect("/");
    }
  });
});

const imageUrl = ["miketyson.gif", "michaelscott.jpg", "hellothere.gif"];


app.get("/members", (req, res) => {
  if (!req.session.authenticated) {
    res.redirect("/");
    return;
  }
var name = req.session.name;
  res.render("members", {name: name, images: imageUrl});
});

app.get("/admin", adminAuthorization, sessionValidation, async (req, res) => {
  if (req.session.authenticated && req.session.user_type == "admin") {
    const result = await userCollection.find()
    .project({name: 1, email: 1, user_type: 1})
    .toArray();
    res.render("admin", {users: result});
    return;
  } else if (req.session.authenticated) {
    res.render("403");
    return;
  } else {
    res.redirect("/");
  }
});

app.post("/promote", async (req, res) => {
  const email = req.body.email;
  await userCollection.updateOne({email: email}, {$set: {user_type: "admin"}});
  res.redirect("/admin");
});

app.post("/demote", async (req, res) => {
  const email = req.body.email;
  await userCollection.updateOne({email: email}, {$set: {user_type: "user"}});
  res.redirect("/admin");
})


app.use(express.static(__dirname + "/public"));

app.get("*", (req, res) => {
  res.status(404);
  res.render("404");
});

app.listen(port, function () {
  console.log("Listening on port " + port + "!");
});
