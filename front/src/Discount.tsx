import {Component} from "react";
import getData from "./utils";

interface Discount {
    id: number
    value: number
}

interface DiscountState {
    discounts: Discount[]
}

interface DiscountProps {}

class Discount extends Component<DiscountProps, DiscountState> {

    constructor(props: DiscountProps) {
        super(props);
        this.state = {
            discounts: [],
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Discount"

        const data: Discount[] = await getData(url, "GET")
        console.log(data)
        this.setState({discounts: data})
    }

    render() {
        return (
            <div className="discounts">
                {this.state.discounts.map((discount, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{discount.id}</h4>
                                {discount.value}
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Discount;