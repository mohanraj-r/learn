import React, { Component } from 'react';

class Profile extends Component {
    render() {
        let hobbies = this.props.hobbies.map((hobby, index) =>
            <li key={index}>
                {hobby}
            </li>
        );

        return (
            <div>
                <h3>{this.props.name}</h3>
                <p>{this.props.name} is {this.props.age} years old and {this.props.bio}</p>
                <h3>Hobbies</h3>
                <ul>
                    {hobbies}
                </ul>
            </div>
        );
    }
}

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            profiles : [
                {
                    name: "Sue",
                    age: 30,
                    bio: "enjoys outdoor activities",
                    hobbies: ["swimming", "biking"]
                },
                {
                    name: "Bill",
                    age: 40,
                    bio: "enjoys reading books",
                    hobbies: ["gardening", "games"]
                }
            ]
        };
    }

    render() {
        let profiles = this.state.profiles.map((profile, index) =>
                <Profile
                    key={index}
                    name={profile.name}
                    age={profile.age}
                    bio={profile.bio}
                    hobbies={profile.hobbies}
                />
        );

        return (
          <div className="App">
            <header className="App-header">
              <h1 className="App-title">Profiles</h1>
            </header>
              <div>
                  {profiles}
              </div>
          </div>
        );
  }
}

export default App;
