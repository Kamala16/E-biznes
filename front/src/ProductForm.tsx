import React, {Component} from 'react';
import getData from "./utils";

type Product = {
    categoryId: number
    rateId: number
    promotionId: number
    price: number
}

type Category = {
    id: number
    productId: number
    name: String
}

type Rate = {
    id: number
    userId: number
    productId: number
    value: number
}

type Promotion = {
    id: number
    productId: number
    value: number
}

type ProductFormState = {
    product: Product;
    categories: Category[];
    rates: Rate[];
    promotions: Promotion[];
}

interface ProductFormProps {}

class ProductForm extends Component<ProductFormProps, ProductFormState> {

    constructor(props: ProductFormProps) {
        super(props);
        this.state = {
            categories: [],
            product: {
                categoryId: 0,
                rateId: 0,
                promotionId: 0,
                price: 0
            },
            rates: [],
            promotions: []
        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addProduct/', "POST", {
            id: 0,
            categoryId: this.state.product.categoryId,
            rateId: this.state.product.rateId,
            promotionId: this.state.product.promotionId,
            price: this.state.product.price
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>Category</label>
                <input
                    type="text"
                    name="product[category]"
                    value={this.state.product.categoryId}
                />
                <label>Rate</label>
                <input
                    type="text"
                    name="product[rate]"
                    value={this.state.product.rateId}
                />
                <label>Promotion</label>
                <input
                    type="text"
                    name="product[promotion]"
                    value={this.state.product.promotionId}
                />
                <label>Category</label>
                <input
                    type="text"
                    name="product[price]"
                    value={this.state.product.price}
                />
                <button>Add product</button>
            </form>
    );
    }

}


export default ProductForm;
