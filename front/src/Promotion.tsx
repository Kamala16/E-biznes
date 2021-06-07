import {Component} from "react";
import getData from "./utils";

interface Promotion {
    id: number
    productId: number
    value: number
}

interface Product {
    id: number
    categoryId: number
    rateId: number
    promotionId: number
    price: number
}

interface PromotionState {
    promotions: Promotion[]
    products: Product[]
}

interface PromotionProps {}

class Promotion extends Component<PromotionProps,PromotionState> {

    constructor(props: PromotionProps) {
        super(props);
        this.state = {
            promotions: [],
            products: []
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Promotion"

        const data: Promotion[] = await getData(url, "GET")
        console.log(data)
        this.setState({promotions: data})
    }

    render() {
        return (
            <div className="promotions">
                {this.state.promotions.map((promotion, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{promotion.id}</h4>
                                <p>{promotion.productId}</p>
                                <p>Value: {promotion.value}</p>
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Promotion;