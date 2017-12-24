"use strict";

A();

function C() {
	console.log("OOPS!");
}

function E(f) {
	console.log("E");
	f();
	var f = F;
}

function A() {
	console.log("A");
	B();
};

function G() {
	console.log("G");
    (function() {
		console.log("H");
		I();
	})();
}

function D() {
	console.log("D");
	E(F);
}

function I() {
	console.log("I");
	J();
}

function B() {
	console.log("B");
	C();
};

function F() {
	console.log("F");
	G();
};

function J() {
    (function() {
		console.log("J");
	})();

    var rest = "KLMNOPQRSTUVWXYZ".split("");
    for (var i=0; i<rest.length; i++) {
        (function(i){
            // define the current function
            window[rest[i]] = function() {
                console.log(rest[i]);
                if (i < (rest.length-1)) {
                    window[rest[i+1]]()
                }
            };
        })(i);
    }
    window[rest[0]]()
};

function C() {
	console.log("C");
	D();
};

