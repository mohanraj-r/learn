import React from 'react';
import ReactDOM from 'react-dom';

import App from './components/App';


const reactContainer = document.getElementById("react");

const render = () => {
    ReactDOM.render(
       <App/>,
        reactContainer
    );
};

setInterval(render, 1000);
