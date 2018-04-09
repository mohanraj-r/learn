import React, { Component } from 'react';
import './App.css';

class App extends Component {
    state = {
      clicks : 0,
    };

    countClicks = () => {
        this.setState({
            clicks: this.state.clicks+1
        });
    };

  render() {
    return (
        <div>
            <InputForm2/>
            <ButtonClass label={"Input"} onClickFunc={this.countClicks}/>
            <ButtonClass label={"Input2"} onClickFunc={this.countClicks}/>
            <br/>
            Total num of clicks: {this.state.clicks}
        </div>
    );
  }
}

function Button({label}) {
    return (
        <button className={"my-button"} style={{color:'green'}}>
            {label}
        </button>
    )
}

class ButtonClass extends Component {
    // Class field syntax
        state = {
            numberOfClicks:0,
        };

        countClick = () => {
            this.props.countClicks();
            this.setState({
                numberOfClicks: this.state.numberOfClicks+1,
            })
        };
        // Constructor syntax
    // constructor(props) {
    //     super(props);
    //
    //     this.state = {
    //         numberOfClicks:0,
    //     };
    //
    //     this.countClick = () => {
    //         this.setState({
    //             numberOfClicks: this.state.numberOfClicks+1,
    //         })
    //     }
    // }

    render() {
        return (
            <button className={"my-button"} style={{color:'green'}} onClick={this.countClick}>
                {this.props.label}
                {this.state.numberOfClicks}
            </button>
        )
    }
}

const InputForm = React.createElement(
  "form",
    {action: "https://google.com/search", target:"_blank"},
    React.createElement("input", {name:"q"}),
    <Button label={"Search"}/>,
);

function InputForm2() {
    return (
        <form action={"https://google.com/search"} target={"_blank"}>
            <input name={"q"} className={"input"}/>
            <Button label={"Search"}/>
        </form>
    )
}

export default App;
