import React, {Component} from "react";

class AddProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formInput : {
                name: "Name",
                age: "Age",
                bio: "Bio",
                hobby: "Hobby"
            }
        };
    }

    handleInputChange = (e) => {
        let formInput = Object.assign({}, this.state.formInput)
        formInput[e.target.id] = e.target.value;
        this.setState({formInput});
    }

    addProfile = (e) => {
        this.props.addProfile({
            name: this.state.formInput.name,
            age: this.state.formInput.age,
            bio: this.state.formInput.bio,
            hobbies: [this.state.formInput.hobby]
        })
    }

    render() {
        return (
            <div>
                <input id="name"  onChange={this.handleInputChange} value={this.state.formInput.name}/>
                <input id="age"   onChange={this.handleInputChange} value={this.state.formInput.age}/>
                <input id="bio"   onChange={this.handleInputChange} value={this.state.formInput.bio}/>
                <input id="hobby" onChange={this.handleInputChange} value={this.state.formInput.hobby}/>
                <button onClick={this.addProfile}>Add Profile</button>
            </div>
        );
    }
}

export default AddProfile;