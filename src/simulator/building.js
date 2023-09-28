// This web page allows to simulate
// 1- users that open/close doors
// 2- the readers of credentials of the doors, requesting actions as if we were a user, and at a certain date and time
//   so a request is : where (door reader), when (time and date), who (user credential), what (action requested)
//   the reader receives an answer from the server that may change the state of the door and this is shown by painting
//   the reader with certain colors
// 3- what a user could ask remotely, from an app installed in his/her mobile, again a request but now addressed
//   to a door but also to all the doors giving access to a certain area
//
// For 1 click on the door, 2 on the reader that is the small colored square besides it, for 3 on the text labels or
// the door readers
//
// We can see the result of our request in
// - the reader, that changes its color
// - the Answer text box that shows the response of the server to a request

var formUser = document.getElementById("formUser");
var formAction = document.getElementById("formAction");
var timeControl = document.getElementById("appt_time");
var dateControl = document.getElementById("appt_date");
var readerControl = document.getElementById("reader");
var areaControl = document.getElementById("area");

// address of the web server, what it listens to
var baseUrl = "http://localhost:8080";

// initialize date control
var now = new Date();
document.getElementById('appt_date').valueAsDate = now;

var img = new Image();
img.src = 'images/blueprint_no_doors.png';
var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");
const factor = 1000.0/1267.0;
// canvas width / image width, must be the same ratio for height
const squareSize = 10.0*factor;


// A bounding box is a rectangle defined by upper left and lower right corners. We use it to detect
// if the user has clicked on a door, the reader of a door or a label in the canvas showing the plan
// of the building
class BoundingBox {
    constructor(x0, y0, x1, y1) {
        console.log("x0=" + x0 + ", y0=" + y0 + ", x1=" + x1 + ", y1=" + y1);
        if ( (x0>=x1) || (y0>=y1) ) {
            throw new Error("Wrong bounding box, non-positive width or height");
        }
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
    }

    inside(x,y) {
        return ( (x >= this.x0) && (x <= this.x1)
                 && (y >= this.y0) && (y <= this.y1) );
    }

    width() {
        return (this.x1 - this.x0);
    }

    height() {
        return (this.y1 - this.y0);
    }

}

//
// Doors
//

class Door {
    constructor(pointReader, pointDoor, id) {
        // point is the upper left corner coordinates of the credentials reader in the original image
        // (the small square)
        this.xr = pointReader[0]*factor;
        this.yr = pointReader[1]*factor;
        // xd, yd is the upper left coordinate of the crop containing the image of the open or closed door
        // that we will draw
        this.x0 = pointDoor[0]*factor;
        this.y0 = pointDoor[1]*factor;
        this.id = id;
        // a unique identifier
        /*
        this.isClosed = true; // has to be the same that in the constructor of Door in the server
        this.isLocked = false;
        */
        this.isClosed = null;
        this.state = null;
        // the state of the door (locked, unlocked etc) to be shown through the reader color

        // the size of small squares representing readers of credentials, 10 in the original image
        this.colorsOfStates = {"unlocked":"green",
                                "locked":"orange",
                                "unlocked_shortly":"lime",
                                "propped": "red"};
        this.imageClosed = new Image();
        this.imageClosed.src = "images/" + id + "_closed.png";
        this.imageOpen = new Image();
        this.imageOpen.src = "images/" + id + "_open.png";
        this.bbox = null; // initialized in draw()
    }

    setState(state) {
        this.state = state;
	}

    insideBBox(x,y) {
        return this.bbox.inside(x,y);
    }

    // fill the small square representing the reader with the color of its *server* state,
    // that is, unlocked, locked etc
    paintReader() {
        ctx.fillStyle = this.colorsOfStates[this.state];
        ctx.fillRect (this.xr, this.yr, squareSize, squareSize);
    }

    // draws the door according to its javascript state, either open or closed
    draw() {
        var ima;
        if (this.isClosed) {
            ima = this.imageClosed;
        } else {
            ima = this.imageOpen;
        }
        if (this.bbox==null) {
            var x1 = this.x0 + ima.width*factor;
            var y1 = this.y0 + ima.height*factor;
            this.bbox = new BoundingBox(this.x0, this.y0, x1, y1);
            // we do it here because in the constructor the images have no yet been loaded
        }
        ctx.drawImage(ima, this.bbox.x0, this.bbox.y0, this.bbox.width(), this.bbox.height());
    }

}


// To contain all the doors, draw them all at the beginning and search by id. The id is obtained by clicking on
// the reader.
class DirectoryDoors {
    constructor() {
        // same ids than in Java server, coordinates in pixels in the original image x=column y=row
        this.doors = [new Door([ 257, 337], [ 142, 291], "D1"),
                      new Door([ 295, 163], [ 262, 180], "D2"),
                      new Door([ 720, 338], [ 610, 282], "D3"),
                      new Door([ 729, 162], [ 698, 172], "D4"),
                      new Door([ 634, 159], [ 602, 175], "D5"),
                      new Door([ 709, 130], [ 646,  99], "D6"),
                      new Door([1161, 163], [1129, 170], "D7"),
                      new Door([1079, 159], [1048, 172], "D8"),
                      new Door([1150,  80], [1156, 101], "D9"),
                      ];
	}

	findById(id) {
	    var i;
	    for (i=0 ; i<this.doors.length; i++) {
	        if (this.doors[i].id == id) {
	            return this.doors[i];
	        }
	    }
	    throw new Error("door with id "+id+" doesn't exist");
	}

}

var dirDoors = new DirectoryDoors();

//
// Areas
//

// An area is each one of the texts appearing in the blueprint, and the bounding box
// encloses this text. Some areas (stairs) may have the label repeated in the blueprint
// so we keep a list of bboxes.
class Area {
    constructor(id, x0, y0, x1, y1) {
        this.id = id;
        this.bboxes = [new BoundingBox(x0*factor,y0*factor,x1*factor,y1*factor)];
    }

    addBBox(x0,y0,x1,y1) {
        this.bboxes = this.bboxes.concat(new BoundingBox(x0*factor,y0*factor,x1*factor,y1*factor));
    }

    insideBBox(x,y) {
        var i;
        for (i=0; i<this.bboxes.length; i++) {
            if (this.bboxes[i].inside(x,y)) {
                return true;
            }
        }
        return false;
    }
}


// To contain all the areas, like DirDoors with doors before
class DirectoryAreas {
    constructor() {
        // like with doors, area ids are the same as in the server
        // No spaces in names! Javascript converts them to %20 and then in Java
        // %20 != " " so we don't find areas, in general string comparisons will fail
        this.areas = [
            new Area("building", 3,347,91,380),
            new Area("exterior", 617,352,706,379),
            new Area("basement", 138,9,250,38),
            new Area("parking", 86,175,174,205),
            new Area("ground_floor", 559,9,696,38),
            new Area("room1", 491,175,574,197),
            new Area("room2", 678,72,763,99),
            new Area("hall", 706,259,755,286),
            new Area("floor1", 1018,9,1097,38),
            new Area("corridor", 1093,200,1126,291),
            new Area("room3", 922,171,1004,198),
            new Area("IT", 1190,86,1226,115)
        ];
        var stairs = new Area("stairs", 306,179,370,203);
        stairs.addBBox(741,179,806,203);
        stairs.addBBox(1172,178,1241,205);
        this.areas = this.areas.concat(stairs);
    }

	findById(id) {
	    var i;
	    for (i=0 ; i<this.areas.length; i++) {
	        if (this.areas[i].id == id) {
	            return this.areas[i];
	        }
	    }
	    throw new Error("area with id "+id+" doesn't exist");
	}
}

var dirAreas = new DirectoryAreas();

//
// Requests
//

async function sendRefresh() {
    const theUrl = baseUrl+"/refresh";
    request.value = theUrl;
    answer.value = "";
    httpGetAsync(theUrl, processAnswerRefreshRequest);
}

// this is called every time we click on the Submit button => send a request from
// the reader or an area
async function sendRequest() {
	// two forms (user and action) and one button is a problem, this is a workaround
	var person_value = "";
	var i;
	for (i = 0; i < formUser.length; i++) {
        if (formUser[i].checked) {
    	    person_value = formUser[i].value; // credential
			break;
        }
	}
	var action_value = "";
	for (i = 0; i < formAction.length; i++) {
        if (formAction[i].checked) {
    	    action_value = formAction[i].value;
			break;
        }
	}

    // if all the necessary fields have been filled in, make and send a request
	if ( (person_value != "")
	     && (action_value != "")
	     && (timeControl.value != "")
	     && (dateControl.value != "")
	     &&  ((readerControl.value != "")
	          || (areaControl.value != "")) ) {
    	console.log(person_value, action_value, dateControl.value, timeControl.value, readerControl.value,
    	            areaControl.value);
    	// collect values from the form fields
    	var cred = "credential=" + person_value;
    	var act = "action=" + action_value;
    	var dt = "datetime=" + dateControl.value + "T" + timeControl.value;
    	var doorId = readerControl.value;
    	var id = "doorId=" + doorId;
    	var areaId = areaControl.value;
    	var ia = "areaId=" + areaId;
   	    var theUrl;

    	var request_type;
    	if (doorId!="") { // request coming from a reader
    	    console.assert(areaId=="");
    	    request_type = "reader";
    	    theUrl = baseUrl+"/reader?" + cred + "&" + act + "&" + dt + "&" + id;
     	} else if (areaId!="") { // request from an area
     	    console.assert(doorId=="");
     	    request_type = "area";
     	    theUrl = baseUrl+"/area?" + cred + "&" + act + "&" + dt + "&" + ia;
     	}
    	request.value = theUrl;
    	answer.value = "";
		console.log("theUrl " + theUrl);

        if (request_type=="reader") {
            if (action_value=="unlock_shortly") {
                httpGetAsync(theUrl, processAnswerReaderRequest);
            } else { // some action different of unlock_shortly
                httpGetAsync(theUrl, processAnswerReaderRequest);
            }
        } else if (request_type == "area") {
            if (action_value != "unlock_shortly") {
                httpGetAsync(theUrl, processAnswerAreaRequest);
            } else { // This action does not make sense for an area because a user can enter only one door at a time.
                console.log("action open on an area does not make sense for us");
            }
        } else { // invalid request
            throw new Error("Wrong request type " + request_type);
        }
    } else {
        // some field in the form is missing
	    console.log("person " + person_value + ", action " + action_value + ", date "
	                + dateControl.value + ", time " + timeControl.value + ", door " + readerControl.value
	                + ", area " + areaControl.value);
		console.log("some input field has not been input");
	}
}

// from https://stackoverflow.com/questions/247483/http-get-request-in-javascript, also
// https://www.freecodecamp.org/news/here-is-the-most-popular-ways-to-make-an-http-request-in-javascript-954ce8c95aaa/

function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();

    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            console.log("calling callback with " + xmlHttp.responseText);
            callback(xmlHttp.responseText);
        } else {
            console.log("state=" + xmlHttp.readyState + ", status=" + xmlHttp.status)
        }
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous
    xmlHttp.send(null);
}


function processAnswerRefreshRequest(responseText) {
    answer.value = responseText;
    json = JSON.parse(responseText);
	var responsesDoors = json.doors;
	var i;
	for (i=0; i<responsesDoors.length; i++) {
	    var d = dirDoors.findById(responsesDoors[i].id);
	    d.isClosed = responsesDoors[i].closed;
	    d.draw();
	    d.setState(responsesDoors[i].state);
	    d.paintReader();
    }
}


// what we do with the response from the server, when it arrives
function processAnswerReaderRequest(responseText) {
    console.log("processAnswerReaderRequest " + responseText)
    answer.value = responseText;
	json = JSON.parse(responseText);
	if (json.authorized) {
	    var door = dirDoors.findById(json.doorId);
	    door.isClosed = json.closed;
        door.draw();
	    door.setState(json.state);
	    door.paintReader();
    }
}


function processAnswerAreaRequest(responseText) {
    console.log("processAnswerAreaRequest " + responseText)
	json = JSON.parse(responseText);
	// this contains an array of door responses, extract and process each one
	var requestsDoors = json.requestsDoors
	var numDoors = requestsDoors.length;
	var i;
	for (i=0; i<numDoors; i++) {
	    processAnswerReaderRequest(JSON.stringify(requestsDoors[i]));
	    // back to string because this is what processAnswerReaderRequest expects
	}
    answer.value = responseText;
}


function processAnswerDoorRequest(responseText) {
    console.log("processAnswerDoorRequest " + responseText)
    answer.value = responseText;
    json = JSON.parse(responseText);
    var door = dirDoors.findById(json.doorId);
    door.isClosed = json.closed;
    door.draw();
}


myCanvas.addEventListener('mousedown', e => {
	mouseX = parseInt(e.clientX) - canvas.offsetLeft;
    mouseY = parseInt(e.clientY) - canvas.offsetTop;
	document.getElementById("mouseX").value = mouseX
	document.getElementById("mouseY").value = mouseY

	var halfSize = Math.ceil(squareSize/2.);
	var j;
	// have we clicked on a reader ?
    for (j=0 ; j< dirDoors.doors.length; j++) {
        var d = dirDoors.doors[j];

		var difxr = Math.abs(mouseX - (d.xr + halfSize));
		var difyr = Math.abs(mouseY - (d.yr + halfSize));
		// console.log("d.xr " + d.xr + " d.yr " + d.yr + " difxr " + difxr + " difyr " + difyr);
		if ((difxr <= squareSize) && (difyr <= squareSize)) {
            readerControl.value = d.id;
            areaControl.value = ""; // it's one or the other
            document.getElementById("radiobutton_open").disabled = false;
            document.getElementById("radiobutton_close").disabled = false;
            document.getElementById("radiobutton_unlock_shortly").disabled = false;
            return;
        }
    }

    // have we clicked on an area label ?
    var area;
    for (j=0; j<dirAreas.areas.length; j++) {
        area = dirAreas.areas[j];
        if (area.insideBBox(mouseX, mouseY)) {
            readerControl.value = "";
            areaControl.value = area.id;
            document.getElementById("radiobutton_open").disabled = true;
            document.getElementById("radiobutton_close").disabled = true;
            document.getElementById("radiobutton_unlock_shortly").disabled = true;
            if (document.getElementById('radiobutton_unlock_shortly').checked) {
                // if it was checked we uncheck it so that when pressing Submit the request is not sent
                document.getElementById('radiobutton_unlock_shortly').checked = false;
            }
            return;
        }
    }
    // we have clicked elsewhere
    readerControl.value = ""
    areaControl.value = ""
});


window.onload = function() {
	ctx.drawImage(img, 0, 0, img.width, img.height, 0, 0, canvas.width, canvas.height);
	// this rescales the rows and cols of image *independently* to the width
	// and height of the canvas => it's necessary that canvas size has the same
	// xy ratio
    sendRefresh();
    // this is to get the initial state of each door and if closed or open, according to the constructor
    // of Door in the server

}
