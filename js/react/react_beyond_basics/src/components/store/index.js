const EventEmitter = require("events");

const store = new EventEmitter();

store.state = {
    time: new Date()
};

setInterval(() => {
    store.state.time = new Date();
    store.emit("change")
}, 1000);

export default store;
