import React from 'react';
import ReactDOM from 'react-dom';

const reactContainer = document.getElementById("react");

const render = () => {
    ReactDOM.render(
        <div className={"demo"}>
            Hello World!
            <input/>
            <p>{new Date().toString()}</p>
        </div>,
        reactContainer
    );
}

setInterval(render, 1000);
