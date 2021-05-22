import {Component} from "react";
import getData from "./utils";

interface Category {
    id: number
    productId: number
    name: string
}

interface CategoryState {
    categories: Category[]
}

interface CategoryProps {
}

class Category extends Component<CategoryProps, CategoryState> {

    constructor(props: CategoryProps) {
        super(props);
        this.state = {
            categories: [],
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Category"

        const data: Category[] = await getData(url, "GET")
        console.log(data)
        this.setState({categories: data})
    }

    render() {
        return (
            <div className="categories">
                {this.state.categories.map((category, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{category.id}</h4>
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Category;