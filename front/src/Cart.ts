import {Component} from "react";

class Cart extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            carts: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/cart"

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
            let carts = data.map(() => {
                return ( "KOSZYK"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({carts: carts})
        })
    }

    render() {
        return ("KOSZYK 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default Cart;