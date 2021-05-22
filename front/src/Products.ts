import {Component} from "react";

class Product extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.state = {
            products: [],
        };
    }

    async getProductRequest(url: RequestInfo) {
        let result = null;
        result = fetch(url, {
            mode: 'cors',
            headers: {
                'Accept' : 'application/json',
                'Content-type': 'application/json',
                'Access-Control-Allow-Origin': 'http://localhost:3000',
            },
            method: 'GET',
        }).then(response => { return response.json}).then(responseData => {
            return responseData;
        })
        return result;
    }

    async getProduct() {
        const url = "http://localhost:9000/"
        let response = await this.getProductRequest(url);
        let products: { id: any; categoryId: any; rateId: any; promotionId: any; price: any; }[] = [];
        // @ts-ignore
        response.map((prod: { id: any; categoryId: any; rateId: any; promotionId: any; price: any; }) => {
            let product = {
                id: prod.id,
                categoryId: prod.categoryId,
                rateId: prod.rateId,
                promotionId: prod.promotionId,
                price: prod.price
            };
            products.push(product)
        })
        this.setState({products: products})
    }

    componentDidMount() {
        this.getProduct();
    }

    render() {
        return "PRODUKT"
    }
}

export default Product;