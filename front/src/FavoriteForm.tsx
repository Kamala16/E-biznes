import React, {Component} from 'react';
import getData from "./utils";

type Favorite ={
    userId: number
    productId: number
}

type Product = {
    categoryId: number
    rateId: number
    promotionId: number
    price: number
}

type User = {
    favoriteId: number
    name: String
    email: String
    password: String
}

type FavoriteFormState = {
    favorite: Favorite;
    products: Product[]
    users: User[];
}

interface FavoriteFormProps {}

class FavoriteForm extends Component<FavoriteFormProps, FavoriteFormState> {

    constructor(props: FavoriteFormProps) {
        super(props);
        this.state = {
            favorite: {
                userId: 0,
                productId: 0,
            },
            products: [],
            users: [],
        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addFavorite/', "POST", {
            id: 0,
            userId: this.state.favorite.userId,
            productId: this.state.favorite.productId,
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>User</label>
                <input
                    type="text"
                    name="favorite[userId]"
                    value={this.state.favorite.userId}
                />
                <label>Product</label>
                <input
                    type="text"
                    name="favorite[productId]"
                    value={this.state.favorite.productId}
                />
                <button>Add favorite</button>
            </form>
        );
    }

}


export default FavoriteForm;
