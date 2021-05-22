import {Component} from "react";

class Delivery extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            delivers: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/delivery"

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
            let delivers = data.map(() => {
                return ( "DOSTAWA"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({delivers: delivers})
        })
    }

    render() {
        return ("DOSTAWA 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default Delivery;