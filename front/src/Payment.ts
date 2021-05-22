import {Component} from "react";

class Payment extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            payments: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/payment"

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
            let payments = data.map(() => {
                return ( "PŁATNOŚĆ"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({payments: payments})
        })
    }

    render() {
        return ("PŁATNOŚĆ 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default Payment;