import {Component} from "react";
import getData from "./utils";
import Product from "./Product";

interface User {
    id: number
    favoriteID: number
    name: string
    email: string
    password: string
}

interface UserState {
    users: User[]
}

interface UserProps {}

class User extends Component<UserProps,UserState> {

    constructor(props: UserProps) {
        super(props);
        this.state = {
            users: [],
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/User"

        const data: User[] = await getData(url, "GET")
        console.log(data)
        this.setState({users: data})
    }

    render() {
        return (
            <div className="users">
                {this.state.users.map((user, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{user.name}</h4>
                                {user.email}
                                {user.password}
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default User;