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

interface Rate {
    id: number
    userId: number
    productId: number
    value: number
}

interface Promotion{
    id: number
    productId: number
    value: number
}

interface ProductState {
    products: Product[]
    category: Category[]
    rate: Rate[]
    promotion: Promotion[]
}

interface ProductProps {}

class Product extends Component<ProductProps, ProductState> {

    constructor(props: ProductProps) {
        super(props);
        this.state = {
            products: [],
            category: [],
            rate: [],
            promotion: []
        };
    }

    async componentDidMount() {
        const url = "http://localhost:9000/api/Product"

        const data: Product[] = await getData(url, "GET")
        console.log(data)
        this.setState({products: data})
    }

    getCategory(categoryId: number): String {
        let category = this.state.category.find(c => {
            return c.id === categoryId
        })
        if (category) {
            return category.name
        } else {
            return "No category"
        }
    }

    getRate(rateId: number): number {
        let rate = this.state.rate.find(r => {
            return r.id === rateId
        })
        if(rate) {
            return rate.value
        } else {
            return 0
        }
    }

    getPromotion(promotionId: number): number {
        let promo = this.state.promotion.find(p => {
            return p.id === promotionId
        })
        if(promo) {
            return promo.value
        } else {
            return 0
        }
    }

    render() {
        return (
            <div className="products">
                {this.state.products.map((product, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{product.id}</h4>
                                <p>Price: {product.price}</p>
                                <p>Category: {this.getCategory(product.categoryId)}</p>
                                <p>Rate: {this.getRate(product.rateId)}</p>
                                <p>Promotion: {this.getPromotion(product.promotionId)}</p>
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Product;