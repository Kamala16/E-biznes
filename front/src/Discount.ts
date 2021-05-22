import {Component} from "react";

class Discount extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            discounts: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/discount"

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
            let discounts = data.map(() => {
                return ( "PRODUKT"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({discounts: discounts})
        })
    }

    render() {
        return ("ZNIŻKA 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default Discount;