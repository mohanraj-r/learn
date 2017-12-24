class HelloWorld extends React.Component {
    render() {
        // return React.createElement('h1', this.props, 'Hello World!')
        return (
            <h1> Hello World! </h1>
        )
    }
}


ReactDOM.render(
    // React.createElement(HelloWorld, {
    //     id: 'hw',
    //     title: 'si',
    //     fooBar: 'hi'
    // }),
    <HelloWorld/>,
    document.getElementById('content')
)