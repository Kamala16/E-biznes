import {Component} from "react";

class Favorite extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            favorites: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/favorite"

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
            let favorites = data.map(() => {
                return ( "ULUBIONE"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({favorites: favorites})
        })
    }

    render() {
        return ("ULUBIONE 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default Favorite;