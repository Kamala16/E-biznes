import {Component} from "react";
import getData from "./utils";
import Product from "./Product";

interface Favorite {
    id: number
    userId: number
    productId: number
}

interface FavoriteState {
    favorites: Favorite[]
}

interface FavoriteProps {}

class Favorite extends Component <FavoriteProps, FavoriteState> {

    constructor(props: FavoriteProps) {
        super(props);
        this.state = {
            favorites: [],
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Favorite"

        const data: Favorite[] = await getData(url, "GET")
        console.log(data)
        this.setState({favorites: data})
    }

    render() {
        return (
            <div className="favorites">
                {this.state.favorites.map((favorite, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{favorite.id}</h4>
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Favorite;