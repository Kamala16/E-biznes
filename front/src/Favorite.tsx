import {Component} from "react";
import getData from "./utils";

interface Favorite {
    id: number
    userId: number
    productId: number
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

interface FavoriteState {
    favorites: Favorite[]
    users: User[]
    products: Product[]
}

interface FavoriteProps {}

class Favorite extends Component <FavoriteProps, FavoriteState> {

    constructor(props: FavoriteProps) {
        super(props);
        this.state = {
            favorites: [],
            users: [],
            products: []
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Favorite"

        const data: Favorite[] = await getData(url, "GET")
        console.log(data)
        this.setState({favorites: data})
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
            <div className="favorites">
                {this.state.favorites.map((favorite, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{favorite.id}</h4>
                                <p>User: {this.getUserName(favorite.userId)}</p>
                                <p>Product: {favorite.productId}</p>
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Favorite;