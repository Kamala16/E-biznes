import {Component} from "react";
import getData from "./utils";
import Product from "./Product";

interface Rate {
    id: number
    userId: number
    productId: number
    value: number
}

interface RateState {
    rates: Rate[]
}

interface RateProps {}

class Rate extends Component<RateProps,RateState> {

    constructor(props: RateProps) {
        super(props);
        this.state = {
            rates: [],
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Rate"

        const data: Rate[] = await getData(url, "GET")
        console.log(data)
        this.setState({rates: data})
    }

    render() {
        return (
            <div className="rates">
                {this.state.rates.map((rate, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{rate.productId}</h4>
                                {rate.value}
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Rate;