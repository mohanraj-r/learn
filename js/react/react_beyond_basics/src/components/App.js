import React from 'react';
import TimeDisplay from "./TimeDisplay";
import store from "./store/index";

class App extends React.Component {
    componentDidMount() {
        store.on("change", ()=>{
            this.setState(store.state);
        })
    }

    render() {
        return (
            <div className={"demo"}>
                Hello World!
                <input/>
                <TimeDisplay time={store.state.time}/>
            </div>
            )
    }
}

export default App;
