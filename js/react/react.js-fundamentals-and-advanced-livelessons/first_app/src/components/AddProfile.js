import React, {Component} from "react";

class AddProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
            <button onClick={this.props.addProfile}>Add Profile</button>
        );
    }
}

export default AddProfile;