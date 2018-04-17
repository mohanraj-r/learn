import React from 'react';
import TimeDisplay from "./TimeDisplay";

class App extends React.Component {
    state = {
      // time: new Date(),
      time: "Date",
    };

    componentDidMount() {
        setInterval(() => {
                this.setState({
                    time: new Date(),
                })
            }, 1000)
    }

    render() {
        return (
            <div className={"demo"}>
                Hello World!
                <input/>
                <TimeDisplay time={this.state.time}/>
            </div>
            )
    }
}

export default App;
