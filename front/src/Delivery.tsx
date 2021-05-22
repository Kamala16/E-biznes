import {Component} from "react";
import getData from "./utils";
import Product from "./Product";

interface Delivery {
    id: number
    name: String
}

interface DeliveryState {
    delivers: Delivery[]
}

interface DeliveryProps {}

class Delivery extends Component<DeliveryProps, DeliveryState> {

    constructor(props: DeliveryProps) {
        super(props);
        this.state = {
            delivers: [],
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Delivery"

        const data: Delivery[] = await getData(url, "GET")
        console.log(data)
        this.setState({delivers: data})
    }

    render() {
        return (
            <div className="delivers">
                {this.state.delivers.map((delivery, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{delivery.name}</h4>
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Delivery;