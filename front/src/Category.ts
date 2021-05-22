import {Component} from "react";

class Category extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            categories: [],
        };
    }

    componentDidMount() {
        var url = "http://localhost:9000/category"

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
            let categories = data.map(() => {
                return ( "KATEGORIA"
                    // <div key={prod.id}>
                    //     <div className="title">{prod.id}</div>
                    // </div>
                )
            })
            this.setState({categories: categories})
        })
    }

    render() {
        return ("KATEGORIA 2"
            // <div className="products">
            //     {this.state.products}
            // </div>
        )
    }
}

export default Category;