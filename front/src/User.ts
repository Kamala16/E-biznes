import {Component} from "react";

class User extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            users: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/user"

        fetch(url, {
            mode: 'cors',
            headers: {
                'Accept' : 'application/json',
                'Content-type': 'application/json',
                'Access-Control-Allow-Origin': 'http://localhost:3000',
            },
            method: 'GET',
        }).then(results => {
            return results.json();
        }).then(data => {
            let users = data.map(() => {
                return ( "UŻYTKOWNIK"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({users: users})
        })
    }

    render() {
        return ("UŻYTKOWNIK 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default User;