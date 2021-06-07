import {Component} from "react";
import getData from "./utils";

interface Rate {
    id: number
    userId: number
    productId: number
    value: number
}

interface User {
    id: number
    favoriteId: number
    name: String
    email: String
    password: String
}

interface Product {
    id: number
    categoryId: number
    rateId: number
    promotionId: number
    price: number
}

interface RateState {
    rates: Rate[]
    users: User[]
    products: Product[]
}

interface RateProps {}

class Rate extends Component<RateProps,RateState> {

    constructor(props: RateProps) {
        super(props);
        this.state = {
            rates: [],
            users: [],
            products: []
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Rate"

        const data: Rate[] = await getData(url, "GET")
        console.log(data)
        this.setState({rates: data})
    }

    getUserName(userId: number): String {
        let user = this.state.users.find(u => {
            return u.id === userId
        })
        if (user) {
            return user.name
        } else {
            return "No user"
        }
    }

    render() {
        return (
            <div className="rates">
                {this.state.rates.map((rate, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{rate.productId}</h4>
                                <p>{rate.value}</p>
                                <p>User: {this.getUserName(rate.userId)}</p>
                                <p>Product: {rate.productId}</p>
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Rate;