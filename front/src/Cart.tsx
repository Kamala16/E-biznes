import {Component} from "react";
import getData from "./utils";

interface Cart {
    id: number
    userId: number
    productId: number
    discountId: number
    price: number
}

interface CartState {
    carts: Cart[]
}

interface CartProps {}

class Cart extends Component<CartProps, CartState> {

    constructor(props: CartProps) {
        super(props);
        this.state = {
            carts: [],
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Cart"

        const data: Cart[] = await getData(url, "GET")
        console.log(data)
        this.setState({carts: data})
    }

    render() {
        return (
            <div className="carts">
                {this.state.carts.map((cart, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{cart.id}</h4>
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Cart;