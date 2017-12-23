var express = require("express");
var bodyParser = require("body-parser");
var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true}));


app.post("/messages", function(req, res){
    if(!req.body.phoneNumber || typeof req.body.phoneNumber != "string" || !req.body.message || typeof req.body.message != "string"){
        res.status(400).send("400 Bad Request");
    }

    console.log(req.body.phoneNumber);
    console.log(req.body.message);

    res.status(200).end();
});

var server = app.listen(3000, function(){
    console.log("Listening on port 3000");
})