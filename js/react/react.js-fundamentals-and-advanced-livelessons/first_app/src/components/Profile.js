import React, {Component} from "react";

class Profile extends Component {
    render() {
        let hobbies = this.props.hobbies.map((hobby, index) =>
            <li key={index}>
                {hobby}
            </li>
        );

        return (
            <div>
                <h2>{this.props.name}</h2>
                <p>{this.props.name} is {this.props.age} years old and {this.props.bio}</p>
                <h4>Hobbies</h4>
                <ul>
                    {hobbies}
                </ul>
                <hr/>
            </div>
        );
    }
}

export default Profile;