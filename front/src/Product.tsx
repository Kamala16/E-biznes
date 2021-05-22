import {Component} from "react";
import getData from "./utils";

interface Product {
    id: number
    categoryId: number
    rateId: number
    promotionId: number
    price: number
}

interface Category {
    id: number
    productId: number
    name: String
}

interface ProductState {
    products: Product[]
    category: Category[]
}

interface ProductProps {}

class Product extends Component<ProductProps, ProductState> {

    constructor(props: ProductProps) {
        super(props);
        this.state = {
            products: [],
            category: []
        };
    }

    async componentDidMount() {
        const url = "http://localhost:9000/api/Product"

        const data: Product[] = await getData(url, "GET")
        console.log(data)
        this.setState({products: data})
    }

    render() {
        return (
            <div className="products">
                {this.state.products.map((product, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{product.id}</h4>
                                {product.categoryId}
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Product;