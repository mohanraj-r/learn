import React, {Component} from 'react';
import Profile from './Profile';
import AddProfile from "./AddProfile";

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

        this.addProfile = this.addProfile.bind(this);
    }

    addProfile(e) {
        let staticUser = {
            name: "Joe",
            age: 90,
            bio: "Moving it along",
            hobbies: ["living", "holding on"]
        }

        this.setState({
            profiles: this.state.profiles.concat(staticUser)
        });
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
                  <AddProfile addProfile={this.addProfile}/>
              </div>
          </div>
        );
  }
}

export default App;
