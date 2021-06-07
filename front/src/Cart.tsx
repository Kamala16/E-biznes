import {Component} from "react";
import getData from "./utils";

interface Cart {
    id: number
    userId: number
    productId: number
    discountId: number
    price: number
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

interface Discount {
    id: number
    userId: number
    value: number
}

interface CartState {
    carts: Cart[]
    users: User[]
    products: Product[]
    discounts: Discount[]
}

interface CartProps {}

class Cart extends Component<CartProps, CartState> {

    constructor(props: CartProps) {
        super(props);
        this.state = {
            carts: [],
            users: [],
            products: [],
            discounts: []
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Cart"

        const data: Cart[] = await getData(url, "GET")
        console.log(data)
        this.setState({carts: data})
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

    getDiscount(discountId: number): number {
        let disc = this.state.discounts.find(d => {
            return d.id === discountId
        })
        if (disc) {
            return disc.value
        } else {
            return 0
        }
    }

    render() {
        return (
            <div className="carts">
                {this.state.carts.map((cart, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{cart.id}</h4>
                                <p>User: {this.getUserName(cart.userId)}</p>
                                <p>Discount: {this.getDiscount(cart.discountId)}</p>
                                <p>PRICE: {cart.price}</p>
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Cart;