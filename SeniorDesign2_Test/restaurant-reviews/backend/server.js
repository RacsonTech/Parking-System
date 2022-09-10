import express from "express";
import cors from "cors";
import restaurants from "./api/restaurants.route.js";

const app = express();

app.use(cors());
app.use(express.json()); // to accept json files in a request

// Route for our main page
app.use("/api/v1/restaurants", restaurants);

// Handles non existing routes
app.use("*", (req, res) => res.status(404).json({ error: "not found" }));

// export this file as a module
export default app;
