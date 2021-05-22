import {Component} from "react";
import getData from "./utils";
import Product from "./Product";

interface Promotion {
    id: number
    productId: number
    value: number
}

interface PromotionState {
    promotions: Promotion[]
}

interface PromotionProps {}

class Promotion extends Component<PromotionProps,PromotionState> {

    constructor(props: PromotionProps) {
        super(props);
        this.state = {
            promotions: [],
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
                                {promotion.productId}
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Promotion;