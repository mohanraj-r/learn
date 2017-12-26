import React, {Component} from "react";

class AddProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "Name",
            age: "Age",
            bio: "Bio",
            hobby: "Hobby"
        };
    }

    handleName = (e) => {
        this.setState({
            name: e.target.value
        })
    };

    handleAge = (e) => {
        this.setState({
            age: e.target.value
        })
    };

    handleBio = (e) => {
        this.setState({
            bio: e.target.value
        })
    };

    handleHobby = (e) => {
        this.setState({
            hobby: e.target.value
        })
    };

    addProfile = (e) => {
        this.props.addProfile({
            name: this.state.name,
            age: this.state.age,
            bio: this.state.bio,
            hobbies: [this.state.hobby]
        })
    }

    render() {
        return (
            <div>
                <input onChange={this.handleName} value={this.state.name}/>
                <input onChange={this.handleAge} value={this.state.age}/>
                <input onChange={this.handleBio} value={this.state.bio}/>
                <input onChange={this.handleHobby} value={this.state.hobby}/>
                <button onClick={this.addProfile}>Add Profile</button>
            </div>
        );
    }
}

export default AddProfile;