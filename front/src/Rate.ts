import {Component} from "react";

class Rate extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            rates: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/rate"

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
            let rates = data.map(() => {
                return ( "OCENA"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({rates: rates})
        })
    }

    render() {
        return ("OCENA 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default Rate;