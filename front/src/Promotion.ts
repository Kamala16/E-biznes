import {Component} from "react";

class Promotion extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            promotions: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/promotion"

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
            let promotions = data.map(() => {
                return ( "PROMOCJA"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({promotions: promotions})
        })
    }

    render() {
        return ("PROMOCJA 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default Promotion;