import {Component} from "react";

class Product extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            products: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/product"

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
            let products = data.map(() => {
                return ( "PRODUKT"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({products: products})
        })
    }

    render() {
        return ("PRODUKT 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default Product;