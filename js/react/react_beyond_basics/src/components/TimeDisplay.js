import React from 'react';

class TimeDisplay extends React.Component {
    render() {
        return (
                <p>{this.props.time.toString()}</p>
        )
    }
}

export default TimeDisplay;
