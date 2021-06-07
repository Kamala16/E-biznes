import React, {Component} from 'react';
import getData from "./utils";

type Category = {
    productId: number
    name: String
}

type Product = {
    categoryId: number
    rateId: number
    promotionId: number
    price: number
}

type CategoryFormState = {
    category: Category;
    products: Product[];
}

interface CategoryFormProps {}

class CategoryForm extends Component<CategoryFormProps, CategoryFormState> {

    constructor(props: CategoryFormProps) {
        super(props);
        this.state = {
            category: {
                productId: 0,
                name: ""
            },
            products: []
        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addCategory/', "POST", {
            id: 0,
            productId: this.state.category.productId,
            name: this.state.category.name,
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>Product</label>
                <input
                    type="text"
                    name="category[product]"
                    value={this.state.category.productId}
                />
                <label>Category name</label>
                <input
                    type="text"
                    name="category[name]"
                />
                <button>Add category</button>
            </form>
        );
    }

}


export default CategoryForm;
